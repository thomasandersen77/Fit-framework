package org.github.fit.grizzly;

import org.github.fit.ServerConfig;
import org.github.fit.TestContainer;
import org.github.fit.TestContainerFactory;
import org.github.fit.MyApplication;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.client.ClientBuilder;

import static org.junit.Assert.*;

public class GrizzlyTestContainerTest {

    @Test
    public void test_call_resource() throws Exception {
        String res = ClientBuilder.newClient().target(container.getAddress()).request().get(String.class);
        assertNotNull(res);
    }


    static TestContainer container;

    @BeforeClass
    public static void start() throws RuntimeException {
        container = TestContainerFactory.grizzly().config(ServerConfig.builder()
                .application(new MyApplication())
                .address("http://localhost")
                .port(9998)
                .scanPackage("org.github.fit").build())
                .createContainer();
        container.start();
    }

    @AfterClass
    public static void stop() throws RuntimeException {
        container.stop();
    }
}