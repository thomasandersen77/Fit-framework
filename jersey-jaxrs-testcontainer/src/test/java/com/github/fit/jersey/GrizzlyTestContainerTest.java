package com.github.fit.jersey;

import com.github.fit.app.MyApplication;
import com.github.fit.common.ServerConfig;
import com.github.fit.common.TestContainer;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Slf4j
public class GrizzlyTestContainerTest {

    @Test
    public void test_my_resource() throws Exception {
        Response res = ClientBuilder.newClient()
                .target("http://localhost:9998")
                .request(MediaType.TEXT_PLAIN_TYPE)
                .header("username", "thomas")
                .get(Response.class);

        assertNotNull(res);
        assertEquals(200, res.getStatus());
        System.out.println(res.getEntity());
    }



    static TestContainer container = GrizzlyTestContainerFactory.create(
            ServerConfig.builder()
            .application(new MyApplication())
            .port(9998)
            .scanPackage("com.github.fit.common").build());

    @BeforeClass
    public static void start() throws RuntimeException {
        container.start();
    }

    @AfterClass
    public static void stop() throws RuntimeException {
        container.stop();
    }
}