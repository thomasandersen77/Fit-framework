package com.github.fit.examples.jaxrs;


import java.util.List;

import com.github.fit.examples.cdi.MyInjectableBean;
import com.github.fit.examples.data.MyJpaRepository;
import com.github.fit.examples.entites.BaseEntity;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/")
@Slf4j
public class MyResource {

    @Inject
    MyInjectableBean myInjectableBean;

    @Inject
    MyJpaRepository myJpaRepository;

    @GET
    @Transactional
    public String echo(){
        String echoMsg = "Echo from resource. Message from injected bean: \n" +
                "\t[" + myInjectableBean +"]";

        String executeInTransactionMsg = null;
        try {
            executeInTransactionMsg = myJpaRepository.executeInTransaction();
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<BaseEntity> entity = myJpaRepository.getEntity();
        log.info("Entiteter: {}", entity);

        return echoMsg +"\n\t"+executeInTransactionMsg;
    }

}
