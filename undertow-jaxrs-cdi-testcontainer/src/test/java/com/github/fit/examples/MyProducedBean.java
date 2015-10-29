package com.github.fit.examples;

import javax.inject.Named;

@Named(value = "testProduce")
public class MyProducedBean {
    public MyProducedBean() {
        System.out.println("produced created");
    }

    public String produced(){
        System.out.println("produced...");
        return "I am produced";
    }
}
