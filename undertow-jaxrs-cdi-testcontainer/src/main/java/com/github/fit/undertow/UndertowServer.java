package com.github.fit.undertow;

import javax.servlet.ServletException;
import javax.ws.rs.core.Application;

import io.undertow.Undertow;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.cdi.CdiInjectorFactory;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;

@Slf4j
public class UndertowServer {
    private UndertowJaxrsServer server = new UndertowJaxrsServer();
    private int serverPort;

    public UndertowServer(int serverPort) {
        this.serverPort = serverPort;
    }

    public void stopContainer(){
        server.stop();
    }

    public void startContainer(Application application) throws ServletException {
        Undertow.Builder serverBuilder = Undertow.builder().addHttpListener(getServerPort(), "0.0.0.0");
        server.start(serverBuilder);
        ResteasyDeployment deployment = new ResteasyDeployment();
        deployment.setInjectorFactoryClass(CdiInjectorFactory.class.getName());

        deployment.setApplication(application);
        final DeploymentInfo deploymentInfo = server.undertowDeployment(deployment)
                .setClassLoader(ClassLoader.getSystemClassLoader())
                .setContextPath("/")
                .setDeploymentName(application.getClass().getSimpleName())
                .addListeners(Servlets.listener(org.jboss.weld.environment.servlet.Listener.class));
        server.deploy(deploymentInfo);
        log.info("Undertow started at port {}", getServerPort());
    }

    public int getServerPort() {
        return serverPort;
    }
}