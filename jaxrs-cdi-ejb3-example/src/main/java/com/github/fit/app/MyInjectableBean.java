package com.github.fit.app;

import javax.inject.Inject;

public class MyInjectableBean {

    @Inject
    MyIntegrationEJB ejb;

    @Override
    public String toString() {
        String fooEjbMessage = ejb.foo();
        return "I am @Inject'ed via CDI" +
                " and depend on " + fooEjbMessage;
    }
}
