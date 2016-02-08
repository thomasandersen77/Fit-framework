package com.github.fit.examples;

import javax.inject.Named;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Named(value = "testProduce")
public class MyProducedBean {
    public MyProducedBean() {
        log.info("produced created");
    }

    public String produced(){
        log.info("produced...");
        return "I am produced";
    }
}
