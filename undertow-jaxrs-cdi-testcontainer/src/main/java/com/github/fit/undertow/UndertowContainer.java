package com.github.fit.undertow;

import com.github.fit.common.*;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.servlet.api.ListenerInfo;
import io.undertow.servlet.api.ServletInfo;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.cdi.ResteasyCdiExtension;
import org.jboss.resteasy.plugins.server.servlet.HttpServlet30Dispatcher;
import org.jboss.resteasy.spi.ResteasyDeployment;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

import javax.servlet.ServletException;
import javax.ws.rs.core.Application;


@Slf4j
public class UndertowContainer implements TestContainer {

    private WeldContainer weldContainer;
    private Undertow server;
    private Application application;
    private Package scanPackage;
    private int port;
    private Weld weld = new Weld();

    public UndertowContainer(ServerConfig config) {
        this.port = config.getPort();
        this.application = config.getApplication();
        this.scanPackage = Package.getPackage(config.getScanPackage());
    }

    @Override
    public void start() {
        try {
            long start = System.currentTimeMillis();
            ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
            weld.addPackage(true, scanPackage.getClass());
            weld.extensions(new ResteasyCdiExtension(), new ProviderExtension(), new ResourceExtension());
            weld.setClassLoader(systemClassLoader);
            weldContainer = weld.initialize();

            ResteasyDeployment resteasyDeployment = new ResteasyDeployment();
            resteasyDeployment.setApplication(application);
            resteasyDeployment.setInjectorFactoryClass(CDIInjectorFactory.class.getName());

            ListenerInfo listener = Servlets.listener(CDIRequestListener.class);

            ServletInfo resteasyServlet = Servlets.servlet("ResteasyServlet", HttpServlet30Dispatcher.class)
                    .setAsyncSupported(true)
                    .setLoadOnStartup(1)
                    .addMapping("/*");

            DeploymentInfo deploymentInfo = new DeploymentInfo()
                    .addListener(listener)
                    .setContextPath("/")
                    .addServletContextAttribute(ResteasyDeployment.class.getName(), resteasyDeployment)
                    .addServlet(resteasyServlet).setDeploymentName("UndertowContainer")
                    .setClassLoader(systemClassLoader);

            DeploymentManager deploymentManager = Servlets.defaultContainer().addDeployment(deploymentInfo);
            deploymentManager.deploy();


            try {
                HttpHandler httpHandler = deploymentManager.start();
                server = Undertow.builder()
                        .addHttpListener(port, "0.0.0.0")
                        .setHandler(httpHandler)
                        .build();
                server.start();
                log.info("Server started in [{}] ms.", (System.currentTimeMillis() - start));
            } catch (ServletException e) {
                e.printStackTrace();
                stop();
            }

        } catch (Exception e) {
            e.printStackTrace();
            stop();
        }

    }

    @Override
    public void stop() {
        if(server != null) server.stop();
        if(weldContainer != null) weldContainer.shutdown();
        log.info("Server stopped...");
    }
}
