package org.github.fit;

import javax.inject.Inject;

public class MyInjectableBean {

    @Inject
    MyEJB ejb;

    @Override
    public String toString() {
        return "I am @Inject'ed via CDI" +
                " and depend on " + ejb.foo();
    }
}
