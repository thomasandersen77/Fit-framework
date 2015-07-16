package org.github.fit.undertow;


import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import java.net.URISyntaxException;

public class UndertowTest {


    UndertowWeldCdiTestContainer container = new UndertowWeldCdiTestContainer(UndertowTest.class, new MyApplication(), "0.0.0.0", 9998);


    @Before
    public void startServer() throws URISyntaxException {

        this.container.start();
    }

    @After
    public void tearDown() throws Exception {
        this.container.stop();
    }

    @Test
    public void test_dummy() throws Exception {
        String response = ResteasyClientBuilder.newClient().target("/").request(MediaType.TEXT_PLAIN_TYPE).get(String.class);
        System.out.println(response);
    }
}
