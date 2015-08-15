package org.github.fit.grizzly;


import lombok.extern.slf4j.Slf4j;
import org.github.fit.ServerConfig;
import org.github.fit.TestContainer;
import org.github.fit.core.ProviderExtension;
import org.github.fit.core.ResourceExtension;
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
    private Package rootPackage;
    private Application application;
    private String address;

    public GrizzlyTestContainer(ServerConfig config) {
        this.address = config.getAddress();
        this.rootPackage = Package.getPackage(config.getScanPackage());
        this.application = config.getApplication();
        this.port = config.getPort();
    }

    @Override
    public void start() {
        try {
            long start = System.currentTimeMillis();
            weld = new Weld();
            weld.addPackage(true, rootPackage.getClass());
            weld.extensions(new ResourceExtension(), new ProviderExtension());
            weld.initialize();
            server = GrizzlyHttpServerFactory.createHttpServer(new URI(this.address + ":"+port),
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

    @Override
    public String getAddress() {
        return address + ":" +port;
    }

    @Override
    public Weld getWeld() {
        return weld;
    }
}