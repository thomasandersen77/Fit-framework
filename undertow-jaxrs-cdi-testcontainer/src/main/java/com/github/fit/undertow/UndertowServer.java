package com.github.fit.undertow;

import javax.servlet.ServletException;
import javax.ws.rs.core.Application;

import io.undertow.Undertow;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;

@Slf4j
public class UndertowServer {

    private final UndertowJaxrsServer server = new UndertowJaxrsServer();

    private String contextPath;
    private int port;
    private String bindAddress;
    private Class<? extends Application> application;


    public UndertowServer(){

    }

    public UndertowServer(int port, String bindAddress, Class<? extends Application> applictionClass) {
        this(port, bindAddress, applictionClass, "/");
    }

    public UndertowServer(int port, Class<? extends Application> applictionClass) {
        this(port, "0.0.0.0", applictionClass, "/");
    }

    public UndertowServer(Class<? extends Application> applictionClass) {
        this(8080, "0.0.0.0", applictionClass, "/");
    }

    public UndertowServer(Class<? extends Application> applictionClass, String contextPath) {
        this(8080, "0.0.0.0", applictionClass, contextPath);
    }

    public UndertowServer(int port, String bindAddress, Class<? extends Application> application, String contextPath) {
        Undertow.Builder serverBuilder = Undertow.builder().addHttpListener(port, bindAddress);
        server.start(serverBuilder);
        this.application = application;
        this.bindAddress = bindAddress;
        this.port = port;
        this.contextPath = contextPath;
    }

    public void start(){
        DeploymentInfo di = this.deployApplication("/", application)
                .setClassLoader(UndertowServer.class.getClassLoader())
                .setContextPath(this.contextPath)
                .setDeploymentName("default")
                .addListeners(
                        Servlets.listener(org.jboss.weld.environment.servlet.Listener.class)
                );
        server.deploy(di);
    }

    public DeploymentInfo deployApplication(String appPath, Class<? extends Application> applicationClass) {
        ResteasyDeployment deployment = new ResteasyDeployment();
        deployment.setInjectorFactoryClass("org.jboss.resteasy.cdi.CdiInjectorFactory");
        deployment.setApplicationClass(applicationClass.getName());
        return server.undertowDeployment(deployment, appPath);
    }

    public void stop() {
        if(server!=null)
            server.stop();
    }

    public Class<? extends Application> getApplication() {
        return application;
    }

    public void setApplication(Class<? extends Application> application) {
        this.application = application;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getBindAddress() {
        return bindAddress;
    }

    public void setBindAddress(String bindAddress) {
        this.bindAddress = bindAddress;
    }
}
