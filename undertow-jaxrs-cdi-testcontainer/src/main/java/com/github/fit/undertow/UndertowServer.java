package com.github.fit.undertow;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.servlet.api.ListenerInfo;
import io.undertow.servlet.api.ServletInfo;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.plugins.server.servlet.HttpServlet30Dispatcher;
import org.jboss.resteasy.spi.ResteasyDeployment;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

import javax.servlet.ServletException;
import javax.ws.rs.core.Application;


@Slf4j
public class UndertowServer {

    private WeldContainer weldContainer;
    private Undertow server;
    private Application application;
    private int port;
    private Weld weld = new Weld();
    private String bindAddress;
    private String contextPath = "";

    public UndertowServer(Application application) {
        this.port = HttpUtils.allocatePort();
        this.application = application;
    }

    public int getPort() {
        return port;
    }

    public void start() {
        if(this.server != null && weldContainer !=null && weldContainer.isRunning()) {
            log.warn(" >>> Server already running...");
            log.info("\t- weldContainer = " + weldContainer);
            log.info("\t- server = " + this.server);
            return;
        }

        try {
            long start = System.currentTimeMillis();
            ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
            weldContainer = weld.initialize();

            ResteasyDeployment resteasyDeployment = new ResteasyDeployment();
            resteasyDeployment.setApplication(application);
            resteasyDeployment.setInjectorFactoryClass(CDIInjectorFactory.class.getName());

            ListenerInfo listener = Servlets.listener(CDIRequestListener.class);

            // todo: this should be injectable
            String deploymentName = "UndertowServer";

            ServletInfo resteasyServlet = Servlets.servlet("ResteasyServlet", HttpServlet30Dispatcher.class)
                    .setAsyncSupported(true)
                    .setLoadOnStartup(1)
                    .addMapping("/*");

            DeploymentInfo deploymentInfo = new DeploymentInfo()
                    .addListener(listener)
                    .setContextPath( this.getContextPath() )
                    .addServletContextAttribute(ResteasyDeployment.class.getName(), resteasyDeployment)
                    .addServlet(resteasyServlet).setDeploymentName(deploymentName)
                    .setClassLoader(systemClassLoader);

            DeploymentManager deploymentManager = Servlets.defaultContainer().addDeployment(deploymentInfo);
            deploymentManager.deploy();
            //Handlers.path(Handlers.redirect( this.getContextPath() ));

            try {
                log.info("Trying to deploy JAX RS application to undertow");

                HttpHandler httpHandler = deploymentManager.start();
                this.server = Undertow
                            .builder()
                                .addHttpListener(getPort(), getBindAddress())
                                .setHandler(httpHandler)
                                .build();
                this.server.start();
                log.info("Server started in [{}] ms." + (System.currentTimeMillis() - start));
            } catch (ServletException e) {
                log.error("Server could not start. Caught {}", e);
                e.printStackTrace();
                this.stop();
            }

        } catch (Exception e) {
            log.error("Server could not start. Caught {}", e);
            e.printStackTrace();
            this.stop();
        }

    }

    public void stop() {
        if(this.server != null)
            this.server.stop();

        if(weldContainer != null)
            weldContainer.shutdown();

        log.info("Server stopped...");
    }

    public void setBindAddress(String bindAddress) {
        this.bindAddress = bindAddress;
    }

    public String getBindAddress() {
        return bindAddress;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }
}
