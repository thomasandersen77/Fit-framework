package com.github.fit.examples;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class MyIntegrationEJB {

    Client myClient = ClientBuilder.newClient();

    public MyIntegrationEJB() {
        log.info("Startup Stateless");
    }

    public String foo(){
        String urlEjbIntegration = System.getProperty("it.ejb.url");

        try {
            return myClient.target(urlEjbIntegration).request(MediaType.TEXT_PLAIN_TYPE)
                    .accept(MediaType.TEXT_PLAIN_TYPE)
                    .get(String.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
