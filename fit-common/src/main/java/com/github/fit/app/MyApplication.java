package com.github.fit.app;

import com.github.fit.util.HttpUtils;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by thomas on 15.07.15.
 */
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
