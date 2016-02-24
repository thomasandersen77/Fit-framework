package com.github.fit.examples.cdi;

import javax.inject.Inject;

public class MyInjectableBean {

    @Inject
    private MyRemoteIntegrationBean ejb;

    public void setEjb(MyRemoteIntegrationBean ejb) {
        this.ejb = ejb;
    }

    @Override
    public String toString() {
        String fooEjbMessage = ejb.foo();
        return "I am @Inject'ed via CDI" +
                " and got a meesage from my integration EJB: \n" +
                "\t\t[ " + fooEjbMessage +" ]";
    }
}
