package org.github.fit.it;

import org.github.fit.FastIntegrationTest;
import org.github.fit.ServerConfig;
import org.github.fit.TestContainer;
import org.github.fit.TestContainerFactory;
import org.junit.*;

import javax.ws.rs.client.ClientBuilder;

import static org.junit.Assert.*;

public class MyApplicationTest {

    static TestContainer containerGrizzly = TestContainerFactory.grizzly()
                                        .config(
                                                ServerConfig.builder()
                                                .address("localhost")
                                                .application(new MyApplication())
                                                .port(9998)
                                                .scanPackage("org.github.fit.it")
                                                .build()
                                        ).createContainer();

    static TestContainer containerUndertow = TestContainerFactory.undertow()
                                        .config(
                                                ServerConfig.builder()
                                                .address("localhost")
                                                .application(new MyApplication())
                                                .port(8080)
                                                .scanPackage("org.github.fit.it")
                                                .build()
                                        ).createServer();

    @Test
    public void test_ejb_and_injection() throws Exception {
        String res1 = ClientBuilder.newClient().target("http://localhost:9998/hello").request().get(String.class);
        System.out.println(res1);
       // String res2 = ClientBuilder.newClient().target("http://localhost:8080/hello").request().get(String.class);
       // System.out.println(res2);
    }

    @BeforeClass
    public static void setUp() throws Exception {
        containerGrizzly.start();
        // containerUndertow.start();
    }

    @AfterClass
    public static void tearDown() throws Exception {
        containerGrizzly.stop();
        // containerUndertow.stop();
    }
}