package com.github.fit.examples;

import javax.ws.rs.GET;

public class TestRessurs {

    @GET
    public String echo(){
        return "Echo tolbake, melding";
    }
}
