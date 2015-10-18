package com.github.fit.undertow.test;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class StatelessEjb {

    @Inject
    RemoteServiceInterface proxy;

    public String getName() {
        String ejbSimpleName = this.getClass().getSimpleName();
        ejbSimpleName += ", calls RemoteServiceInterface.get() via proxy: " + proxy.get();
        return ejbSimpleName;
    }
}