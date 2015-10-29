package com.github.fit.examples.jaxrs;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class CheckUsernameRequestFilter implements ContainerRequestFilter{

    @Context
    HttpHeaders headers;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        System.err.println("CheckUsernameRequestFilter RequestURI: {"+requestContext.getUriInfo().getRequestUri());
        if(!validUsername()) {
            throw new ForbiddenException("Username missing from HTTP header");
        }
    }

    private boolean validUsername() {
        return headers.getHeaderString("username") != null && headers.getHeaderString("username").equals("thomas");
    }
}
