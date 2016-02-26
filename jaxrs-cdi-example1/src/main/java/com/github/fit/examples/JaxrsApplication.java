package com.github.fit.examples;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.github.fit.examples.cdi.MyInjectableBean;
import com.github.fit.examples.cdi.MyRemoteIntegrationBean;
import com.github.fit.examples.data.EntityManagerProducer;
import com.github.fit.examples.jaxrs.CheckUsernameRequestFilter;
import com.github.fit.examples.jaxrs.ForbiddenExceptionMapper;
import com.github.fit.examples.jaxrs.MyResource;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/")
public class JaxrsApplication extends Application {
    public JaxrsApplication() {
    }

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> c = new HashSet<>();
        c.add(MyResource.class);
        c.add(ForbiddenExceptionMapper.class);
        c.add(CheckUsernameRequestFilter.class);
        c.add(MyInjectableBean.class);
        c.add(MyRemoteIntegrationBean.class);
        c.add(MyResource.class);
        c.add(EntityManagerProducer.class);

        return c;
    }
}
