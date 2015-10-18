package com.github.fit.undertow;


import com.github.fit.rule.JaxRsIntegrationTestRule;
import com.github.fit.undertow.test.TestApplication;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ContainerIntegrationTest {

    @Rule
    public JaxRsIntegrationTestRule testContainerRule =
            new JaxRsIntegrationTestRule(new TestApplication());

    @Before
    public void setUpMockedAdress() {
        System.setProperty("it.ejb.url", "http://localhost:" + testContainerRule.getWmPort() +"/api/remote");
    }

    @Test
    public void test_my_resource_with_valid_username() throws Exception {
        stubFor(get(urlEqualTo("/api/remote"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "text/plain")
                        .withBody("a response from an EJB with a REST integration")));


        Response response = ResteasyClientBuilder.newClient()
                .target("http://localhost:"+testContainerRule.getLocalContainerPort()+"/app/test")
                .request(MediaType.TEXT_PLAIN_TYPE)
                .accept(MediaType.TEXT_PLAIN_TYPE)
                .header("username", "thomas")
                .get(Response.class);

        assertNotNull(response);
        assertEquals(200, response.getStatus());
    }

    @Test
    public void test_my_resource_with_valid_username_to_string() throws Exception {
        stubFor(get(urlEqualTo("/integration/ejb/message"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "text/plain")
                        .withBody("a response from an EJB with a REST integration")));


        String response = ResteasyClientBuilder.newClient()
                .target("http://localhost:"+testContainerRule.getLocalContainerPort()+"/app/test")
                .request(MediaType.TEXT_PLAIN_TYPE)
                .header("username", "thomas")
                .get(String.class);

        assertNotNull(response);
        System.out.println("Response message: " + response);
    }
}
