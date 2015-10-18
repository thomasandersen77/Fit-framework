package com.github.fit.app;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class ForbiddenExceptionMapper implements ExceptionMapper<Exception> {
    @Override
    public Response toResponse(Exception exception) {
        exception.printStackTrace();
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
}
