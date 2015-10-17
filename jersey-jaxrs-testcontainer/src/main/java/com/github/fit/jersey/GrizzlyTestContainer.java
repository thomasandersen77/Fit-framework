package com.github.fit.jersey;


import com.github.fit.common.ProviderExtension;
import com.github.fit.common.ResourceExtension;
import com.github.fit.common.ServerConfig;
import com.github.fit.common.TestContainer;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.jboss.weld.environment.se.Weld;

import javax.ws.rs.core.Application;
import java.net.URI;

@Slf4j
public class GrizzlyTestContainer implements TestContainer {
    private int port;
    private Weld weld;
    private HttpServer server;
    private Class<?> scanPackage;
    private Application application;

    public GrizzlyTestContainer(ServerConfig config) {
        String scanPackage = config.getScanPackage();
        log.info("Scan {}", scanPackage);
        Package aPackage = Package.getPackage(scanPackage);
        this.scanPackage = aPackage.getClass();
        this.application = config.getApplication();
        this.port = config.getPort() > 0 ? config.getPort() : 9998;
    }

    @Override
    public void start() {
        try {
            long start = System.currentTimeMillis();
            weld = new Weld();
            weld.addPackage(true, scanPackage);
            weld.extensions(new ProviderExtension() , new ResourceExtension());
            weld.initialize();
            server = GrizzlyHttpServerFactory.createHttpServer(new URI("localhost:"+port),
                    new ResourceConfig(application.getClasses()));

            weld.addInterceptor(CDIRequestInterceptor.class);
            server.start();
            long startupTime = (System.currentTimeMillis() - start);
            log.info("Server started in [{}] ms.", startupTime);

        } catch (Exception e) {
            e.printStackTrace();
            stop();
        }
    }

    @Override
    public void stop() {
        if (server != null) server.shutdownNow();
        if (weld != null) weld.shutdown();
        log.info("Server stopped...");
    }
}