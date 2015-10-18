package org.github.fit;

import lombok.extern.slf4j.Slf4j;

import javax.ejb.Startup;
import javax.ejb.Stateless;

@Stateless
@Startup
@Slf4j
public class MyEJB {

    public MyEJB() {
        System.out.println("********************************************************************");
        System.out.println("Statless EJB instatiated at startup. Class: " + getClass().getName());
        System.out.println("********************************************************************");
    }

    public String foo(){
        return "a stateless EJB";
    }
}
