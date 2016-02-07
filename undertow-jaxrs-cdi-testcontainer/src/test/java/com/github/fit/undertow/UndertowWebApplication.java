package com.github.fit.undertow;

import com.github.fit.examples.MyApplication;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UndertowWebApplication {
    public static void main(String[] args) {

        final UndertowServer server = new UndertowServer(new MyApplication());
        server.setBindAddress("localhost");
        server.setContextPath("test");
        server.setPort(8080);
        server.start();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                log.info("Shutting down UndertowServer");
                server.stop();
            }
        });
        log.info("Shut Down Hook Attached.");
    }

}
