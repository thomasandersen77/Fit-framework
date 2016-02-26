package com.github.fit.undertow;


import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.github.fit.examples.MyApplication;
import com.github.fit.rule.IntegrationTestRule;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class UndertowServerJunitRuleTest {

    public static final String WIREMOCK_STRING_RESPONSE = "**** HELLO FROM WIREMOCK ******";

    @Rule
    public IntegrationTestRule container = new IntegrationTestRule(new MyApplication(), true);

    @Before
    public void setUpMockedAdress() {
        System.setProperty("it.ejb.url", "http://localhost:" + container.getWiremockPort() +"/integration/ejb/message");
    }

    @Before
    public void configureStub() {
        stubFor(get(urlEqualTo("/integration/ejb/message"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "text/plain")
                        .withBody(WIREMOCK_STRING_RESPONSE)));
    }

    @Ignore
    @Test
    public void test_my_resource_with_valid_username() throws Exception {
        Response response = ResteasyClientBuilder.newClient()
                .target("http://localhost:"+ container.getAppPort())
                .request(MediaType.TEXT_PLAIN_TYPE)
                .accept(MediaType.TEXT_PLAIN_TYPE)
                .header("username", "thomas")
                .get(Response.class);

        assertNotNull(response);
        assertEquals(200, response.getStatus());
    }

    @Ignore
    @Test
    public void test_my_resource_with_valid_username_to_string() throws Exception {
          String response = ResteasyClientBuilder.newClient()
                .target("http://localhost:"+ container.getAppPort())
                .request(MediaType.TEXT_PLAIN_TYPE)
                .header("username", "thomas")
                .get(String.class);

        assertNotNull(response);
        assertTrue(response.contains(WIREMOCK_STRING_RESPONSE));
        System.out.println("Response message: " + response);
    }
}
