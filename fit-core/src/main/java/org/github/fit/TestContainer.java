package org.github.fit;

import org.jboss.weld.environment.se.Weld;

import java.net.UnknownHostException;

/**
 * Created by thomas on 15.07.15.
 */
public interface TestContainer {
    void start();
    void stop();
    String getAddress() throws UnknownHostException;
    Weld getWeld();
}
