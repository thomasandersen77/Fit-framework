package com.github.fit.app;

import com.github.fit.app.jaxrs.CheckUsernameRequestFilter;
import com.github.fit.app.jaxrs.ForbiddenExceptionMapper;
import com.github.fit.app.jaxrs.MyResource;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/")
public class JaxrsApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(MyResource.class);
        classes.add(CheckUsernameRequestFilter.class);
        classes.add(ForbiddenExceptionMapper.class);
        return classes;
    }
}
