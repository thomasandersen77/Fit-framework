package com.github.fit.app.beans;

import javax.inject.Inject;

public class MyInjectableBean {

    @Inject
    private MyIntegrationEJB ejb;

    @Override
    public String toString() {
        String fooEjbMessage = ejb.foo();
        return "I am @Inject'ed via CDI" +
                " and got a meesage from my integration EJB: \n" +
                "\t\t[ " + fooEjbMessage +" ]";
    }
}
