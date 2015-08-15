package org.github.fit.undertow;


import org.github.fit.MyApplication;
import org.github.fit.ServerConfig;
import org.github.fit.TestContainer;
import org.github.fit.TestContainerFactory;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import java.net.URISyntaxException;

public class UndertowContainerTest {

    static TestContainer testContainer =
            TestContainerFactory.undertow().config(
                    ServerConfig.builder()
                        .application(new MyApplication())
                        .address("localhost")
                        .port(9998)
                        .scanPackage("org.github.fit").build())
                        .createServer();

    @BeforeClass
    public static void startServer() throws URISyntaxException {
        testContainer.start();
    }

    @AfterClass
    public static void tearDown() throws Exception {
        testContainer.stop();
    }

    @Test
    public void test_dummy() throws Exception {
        String response = ResteasyClientBuilder.newClient()
                .target("http://localhost:9998/")
                .request(MediaType.TEXT_PLAIN_TYPE).get(String.class);
        System.out.println(response);
    }
}
