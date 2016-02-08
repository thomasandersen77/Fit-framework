package com.github.fit.examples;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("test")
public class TestRessurs {

    @GET
    public String echo(){
        return "Echo tolbake, melding";
    }
}
