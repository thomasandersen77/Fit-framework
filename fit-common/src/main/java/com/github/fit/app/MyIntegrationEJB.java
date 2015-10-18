package com.github.fit.app;

import lombok.extern.slf4j.Slf4j;

import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

@Stateless
@Startup
@Slf4j
public class MyIntegrationEJB {

    Client myClient = ClientBuilder.newClient();

    public MyIntegrationEJB() {
        System.out.println("********************************************************************");
        System.out.println("Statless EJB instatiated at startup. Class: " + getClass().getName());
        System.out.println("********************************************************************");
    }

    public String foo(){
        String urlEjbIntegration = System.getProperty("it.ejb.url");
        System.out.println(urlEjbIntegration);
        String itResponseString = null;
        try {
            itResponseString = myClient.target(urlEjbIntegration).request(MediaType.TEXT_PLAIN_TYPE)
                    .accept(MediaType.TEXT_PLAIN_TYPE)
                    .get(String.class);
            System.out.println(itResponseString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itResponseString;
    }
}
