package com.github.fit.examples;


import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Path("/")
public class MyResource {

    @Inject MyInjectableBean myInjectableBean;

    @Inject
    @Named(value = "testProduce")
    MyProducedBean myProducedBean;

    @GET
    public String echo(){
        log.info("Inide echo");
        log.info("\techo myProducedBean instance", myProducedBean);
        return "Echo from resource. Message from injected bean: \n" +
                "\t[" + myInjectableBean +"]";
    }

}
