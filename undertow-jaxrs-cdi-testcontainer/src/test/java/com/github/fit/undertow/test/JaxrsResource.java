package com.github.fit.undertow.test;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("test")
public class JaxrsResource {
    @Inject
    StatelessEjb statelessEjb;

    @GET
    public String foo() {
        return statelessEjb.getName();
    }
}
