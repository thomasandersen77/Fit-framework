package com.github.fit.examples;


import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/")
public class MyResource {

    @Inject MyInjectableBean myInjectableBean;

    @Inject
    @Named(value = "testProduce")
    MyProducedBean myProducedBean;

    @GET
    public String echo(){
        System.out.println("Inide echo");
        System.out.println(myProducedBean);
        return "Echo from resource. Message from injected bean: \n" +
                "\t[" + myInjectableBean +"]";
    }

}
