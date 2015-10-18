package com.github.fit.app;


import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@HttpResource
@Path("/")
public class MyResource {

    @Inject MyInjectableBean myInjectableBean;

    @GET
    public String echo(){
        return "From Server: " + myInjectableBean;
    }

}
