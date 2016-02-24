package com.github.fit.examples;

import java.util.Collections;
import java.util.Set;

import com.github.fit.examples.cdi.MyInjectableBean;
import com.github.fit.examples.cdi.MyRemoteIntegrationBean;
import com.github.fit.examples.jaxrs.CheckUsernameRequestFilter;
import com.github.fit.examples.jaxrs.ForbiddenExceptionMapper;
import com.github.fit.examples.jaxrs.MyResource;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/")
public class JaxrsApplication extends ResourceConfig {
    public JaxrsApplication() {
        register(MyResource.class);
        register(ForbiddenExceptionMapper.class);
        register(CheckUsernameRequestFilter.class);
        register(MyRemoteIntegrationBean.class);
        register(MyInjectableBean.class);
    }
}
