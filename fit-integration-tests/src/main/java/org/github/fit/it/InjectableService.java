package org.github.fit.it;

import javax.annotation.PostConstruct;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.interceptor.InvocationContext;

@Startup
@Stateless
public class InjectableService {

    @PostConstruct
    public void logStartup(InvocationContext invocationContext) {
        System.out.println( invocationContext.getTarget() );
        try {
            System.out.println( invocationContext.proceed() );
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("I am a @Startup and @Stateless bean :)");
    }

    @Override
    public String toString() {
        return "InjectableService";
    }
}
