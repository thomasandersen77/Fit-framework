package org.github.fit;

import org.github.fit.api.Resource;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Resource
@Path("/")
public class MyResource {

    //@Inject
    private CdiBean cdiBean;

    public MyResource() {
        // org.jboss.resteasy.spi.metadata.ResourceBuilder must have a public default constructor
    }

    @GET
    public String echo(){
        return "From Server: echo";
    }

}
