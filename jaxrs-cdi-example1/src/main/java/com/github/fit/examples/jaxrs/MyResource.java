package com.github.fit.examples.jaxrs;


import com.github.fit.examples.cdi.MyInjectableBean;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/")
public class MyResource {

    @Inject
    MyInjectableBean myInjectableBean;

    @GET
    public String echo(){
        return "Echo from resource. Message from injected bean: \n" +
                "\t[" + myInjectableBean +"]";
    }

}
