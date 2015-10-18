package com.github.fit.core;

import org.jboss.resteasy.cdi.CdiInjectorFactory;

import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;


public class CDIInjectorFactory extends CdiInjectorFactory {
    @Override
    protected BeanManager lookupBeanManager() {
        return CDI.current().getBeanManager();
    }
}
