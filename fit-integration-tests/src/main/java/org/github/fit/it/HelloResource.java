package org.github.fit.it;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

@Path("hello")
public class HelloResource {

    @Resource
    private ManagedExecutorService executorService;

    //@Inject
    private InjectableService injectableService;

    @GET
    public String say(@Context HttpHeaders headers) {
        final StringBuilder builder = new StringBuilder("ManagedExecutorService: " + executorService);
        builder.append("\n").append(InjectableService.class);

        headers.getRequestHeaders().forEach((key, value) -> {
            builder.append(key).append("\t").append(value).append("\n");
        });
        return builder.toString();
    }

}
