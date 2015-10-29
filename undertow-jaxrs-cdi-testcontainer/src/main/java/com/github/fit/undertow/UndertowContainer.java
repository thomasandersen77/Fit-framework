package com.github.fit.undertow;

import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.servlet.api.ListenerInfo;
import io.undertow.servlet.api.ServletInfo;
import org.jboss.resteasy.plugins.server.servlet.HttpServlet30Dispatcher;
import org.jboss.resteasy.spi.ResteasyDeployment;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

import javax.servlet.ServletException;
import javax.ws.rs.core.Application;


public class UndertowContainer {

    private WeldContainer weldContainer;
    private Undertow server;
    private Application application;
    private int port;
    private Weld weld = new Weld();

    public UndertowContainer(Application application) {
        this.port = HttpUtils.allocatePort();
        this.application = application;
    }

    public int getPort() {
        return port;
    }

    public void start() {
        if(server != null && weldContainer !=null && weldContainer.isRunning()) {
            System.err.println(" >>> Server already running...");
            System.out.println("\t- weldContainer = " + weldContainer);
            System.out.println("\t- server = " + server);
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
            // ListenerInfo weldListener = Servlets.listener()



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
                System.out.println("Server started in [{}] ms." + (System.currentTimeMillis() - start));
            } catch (ServletException e) {
                e.printStackTrace();
                stop();
            }

        } catch (Exception e) {
            e.printStackTrace();
            stop();
        }

    }

    public void stop() {
        if(server != null) server.stop();
        if(weldContainer != null) weldContainer.shutdown();
        System.out.println("Server stopped...");
    }
}
