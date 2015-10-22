package com.github.fit.examples;


import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/")
public class MyResource {

    @Inject MyInjectableBean myInjectableBean;

    @Inject
    EntityManager entityManager;

    @GET
    public String echo(){
        System.out.println("Inide echo");
        System.out.println(entityManager);
        return "Echo from resource. Message from injected bean: \n" +
                "\t[" + myInjectableBean +"]";
    }

}
