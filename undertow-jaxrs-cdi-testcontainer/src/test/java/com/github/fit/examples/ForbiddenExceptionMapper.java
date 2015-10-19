package com.github.fit.examples;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class ForbiddenExceptionMapper implements ExceptionMapper<Exception> {
    @Override
    public Response toResponse(Exception exception) {
        System.err.println("Exception occured: " + exception.toString() +"\n");
        exception.printStackTrace();
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
}
