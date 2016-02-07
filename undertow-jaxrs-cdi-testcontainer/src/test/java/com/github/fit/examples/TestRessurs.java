package com.github.fit.examples;

import javax.ws.rs.GET;
import javax.ws.rs.QueryParam;

public class TestRessurs {

    @GET
    @QueryParam("{test}")
    public String echo(@QueryParam(value = "tester") String melding){
        return melding;
    }
}
