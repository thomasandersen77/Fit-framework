package com.github.fit.undertow.test;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("api/remote")
public interface RemoteServiceInterface {
    @GET
    String get();

    @POST
    void post();

    @DELETE
    boolean delete();
}
