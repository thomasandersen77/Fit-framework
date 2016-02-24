package com.github.fit.undertow;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

import org.apache.log4j.Logger;

@ApplicationScoped
public class InitializationResourceBean {

    private static final Logger log = Logger.getLogger(InitializationResourceBean.class);


    public void listen(@Observes ApplicationInitialisationListener.SomeDummyEvent event) {
        log.info("Startup " + event);
    }

    @PostConstruct
    public void init() {
        log.info("init");
    }

    @PreDestroy
    public void destroy() {
        // Do some other thing with healthCheck
    }
}