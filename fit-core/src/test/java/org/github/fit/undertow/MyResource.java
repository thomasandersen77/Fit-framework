package org.github.fit.undertow;

import org.github.fit.api.Resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * Created by thomas on 15.07.15.
 */
@Resource
@Path("/")
public class MyResource {

    @GET
    public String echo(){
        return "From Server: echo";
    }

}
