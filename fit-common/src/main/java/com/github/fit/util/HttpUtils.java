package com.github.fit.util;

import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;

@Slf4j
public class HttpUtils {
    public static int allocatePort() {
        try {
            @Cleanup ServerSocket serverSocket = new ServerSocket(0);
            int port = serverSocket.getLocalPort();
            log.debug("Allocated port number: {}", port);
            return port;
        } catch (IOException e) {
            throw new RuntimeException("Could not allocate port", e);
        }
    }
}
