package org.github.fit.undertow;

import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.servlet.api.ListenerInfo;
import io.undertow.servlet.api.ServletInfo;
import lombok.extern.slf4j.Slf4j;
import org.github.fit.ServerConfig;
import org.github.fit.TestContainer;
import org.github.fit.core.CDIInjectorFactory;
import org.github.fit.core.ProviderExtension;
import org.github.fit.core.ResourceExtension;
import org.jboss.resteasy.cdi.ResteasyCdiExtension;
import org.jboss.resteasy.plugins.server.servlet.HttpServlet30Dispatcher;
import org.jboss.resteasy.spi.ResteasyDeployment;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import sun.nio.ch.Net;

import javax.servlet.ServletException;
import javax.ws.rs.core.Application;
import java.net.InetSocketAddress;

/**
 * Created by thomas on 15.07.15.
 */
@Slf4j
public class UndertowTestContainer implements TestContainer {

    private WeldContainer weldContainer;

    private Undertow server;
    private Application application;
    private Package scanPackage;
    private String baseUri;
    private int port;
    private String bindAddress;
    private Weld weld = new Weld();

    public UndertowTestContainer(ServerConfig config) {
        this.bindAddress = config.getAddress() == null ? "0.0.0.0" : config.getAddress();
        this.port = config.getPort() > 0 ? config.getPort() : 9998;
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
                    .addServlet(resteasyServlet).setDeploymentName("UndertowTestContainer")
                    .setClassLoader(systemClassLoader);

            DeploymentManager deploymentManager = Servlets.defaultContainer().addDeployment(deploymentInfo);
            deploymentManager.deploy();


            try {
                HttpHandler httpHandler = deploymentManager.start();
                server = Undertow.builder()
                        .addHttpListener(port, bindAddress)
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

    @Override
    public String getAddress() {
        return Net.checkAddress(new InetSocketAddress(bindAddress, port)).getHostName();
    }

    /**
     * retrive it just experiment some more
     * @return
     */
    @Override
    public Weld getWeld() {
        return weld;
    }
}
