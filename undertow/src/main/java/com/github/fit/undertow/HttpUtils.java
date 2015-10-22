package com.github.fit.undertow;

import lombok.Cleanup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


import java.io.IOException;
import java.net.ServerSocket;


@lombok.Value

public class HttpUtils {
    public static int allocatePort() {
        try {
            @Cleanup ServerSocket serverSocket = new ServerSocket(0);
            int port = serverSocket.getLocalPort();
//            log.debug("Allocated port number: {}", port);
//            log.debug("Allocated port number: {}", port);
            return port;
        } catch (IOException e) {
            throw new RuntimeException("Could not allocate port", e);
        }
    }
}
