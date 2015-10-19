package com.github.fit.app;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/")
public class MyApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(MyResource.class);
        classes.add(CheckUsernameRequestFilter.class);
        classes.add(ForbiddenExceptionMapper.class);
        return classes;
    }
}
