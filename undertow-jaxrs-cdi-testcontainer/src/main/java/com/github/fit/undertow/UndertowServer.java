package com.github.fit.undertow;

import javax.servlet.ServletException;
import javax.ws.rs.core.Application;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.UndertowOptions;
import io.undertow.server.handlers.PathHandler;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
//import org.glassfish.jersey.servlet.ServletContainer;
import org.jboss.weld.environment.servlet.Listener;

public class UndertowServer {
    private static Undertow server;

    public static void stopContainer(){
        server.stop();
    }

    public static void startContainer(int port, Application application) throws ServletException {
        DeploymentInfo servletBuilder = Servlets.deployment();

        servletBuilder
                .setClassLoader(UndertowServer.class.getClassLoader())
                .setContextPath("/")
                .setDeploymentName(application.getClass().getName()+ ".war")
                .addListeners(Servlets.listener(Listener.class));
                /*.addServlets(Servlets.servlet("jerseyServlet", ServletContainer.class)
                        .setLoadOnStartup(1)
                        .addInitParam("javax.ws.rs.Application", application.getClass().getName())
                        .addMapping("/*"));*/

        DeploymentManager manager = Servlets.defaultContainer().addDeployment(servletBuilder);
        manager.deploy();
        PathHandler path = Handlers.path(Handlers.redirect("/"))
                .addPrefixPath("/", manager.start());

        server =
                Undertow
                        .builder()
                        .addHttpListener(port, "localhost")
                        .setHandler(path)
                        //.setServerOption(UndertowOptions.ENABLE_HTTP2)
                        .build();

        server.start();
    }
}