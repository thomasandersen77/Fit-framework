package org.github.fit.undertow;

import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.servlet.api.ListenerInfo;
import io.undertow.servlet.api.ServletInfo;
import lombok.extern.slf4j.Slf4j;
import org.github.fit.ConfigurableTestContainer;
import org.github.fit.core.CDIInjectorFactory;
import org.github.fit.core.ProviderExtension;
import org.github.fit.core.ResourceExtension;
import org.github.fit.undertow.CDIRequestListener;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.ext.cdi1x.internal.CdiComponentProvider;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.jboss.resteasy.cdi.CdiInjectorFactory;
import org.jboss.resteasy.cdi.ResteasyCdiExtension;
import org.jboss.resteasy.plugins.server.servlet.HttpServlet30Dispatcher;
import org.jboss.resteasy.spi.ResteasyDeployment;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

import javax.servlet.ServletException;
import javax.ws.rs.core.Application;
import java.net.URI;

/**
 * Created by thomas on 15.07.15.
 */
@Slf4j
public class UndertowWeldCdiTestContainer extends ConfigurableTestContainer {

    private WeldContainer weldContainer;
    private Weld weld;
    private Undertow server;
    private String baseUri;

    public UndertowWeldCdiTestContainer(Class packageToScan, Application jaxRsApplication, String baseUri, int port) {

        // System.setProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager");

        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();

        weld = new Weld();
        try {
            weld.addPackage(true, packageToScan);
            weld.extensions(new ResteasyCdiExtension(), new ProviderExtension(), new ResourceExtension());
            weld.setClassLoader(systemClassLoader);
            //weldContainer = weld.initialize();


            ResteasyDeployment resteasyDeployment = new ResteasyDeployment();
            resteasyDeployment.setApplication(jaxRsApplication);
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
                    .addServlet(resteasyServlet).setDeploymentName("TestContainerUndertowCdi")
                    .setClassLoader(systemClassLoader);

            DeploymentManager deploymentManager = Servlets.defaultContainer().addDeployment(deploymentInfo);
            deploymentManager.deploy();


            try {
                server = Undertow.builder()
                            .addHttpListener(port, getBaseUri())
                            .setDirectBuffers(true)
                            .setWorkerThreads(3)
                            .setHandler(deploymentManager.start())
                            .build();
                //server.start();
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
    public void start() {
        long start = System.currentTimeMillis();
        weldContainer = weld.initialize();
        server.start();
        long startupTime = (System.currentTimeMillis() - start);
        log.info("Server oppe. Oppstarten tok [" + startupTime + "] ms.");
    }

    public void stop() {
        if(server != null) server.stop();
        if(weldContainer != null) weldContainer.shutdown();
        log.info("******************* Stoppet test server *****************************");
    }

    public String  getBaseUri() {
        return baseUri;
    }


    @Override
    protected void configure() {

    }
}
