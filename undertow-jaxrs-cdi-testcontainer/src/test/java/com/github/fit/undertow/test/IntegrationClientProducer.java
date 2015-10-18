package com.github.fit.undertow.test;

import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@Slf4j
@ApplicationScoped
public class IntegrationClientProducer {


    @Produces
    public RemoteServiceInterface productRemoteProxy() {
        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target("http://example.com/base/uri");
        RemoteServiceInterface remoteServiceInterface = target.proxy(RemoteServiceInterface.class);
        return remoteServiceInterface;
    }

}
