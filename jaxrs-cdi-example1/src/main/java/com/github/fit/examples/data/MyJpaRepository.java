package com.github.fit.examples.data;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.github.fit.examples.entites.BaseEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.deltaspike.jpa.api.transaction.Transactional;

@Slf4j
public class MyJpaRepository {

    @Inject
    EntityManager entityManager;

    @Transactional
    public String executeInTransaction() {
        log.info("***** executeInTransaction called **********");
        log.info("EntityManager is {}:", entityManager);
        BaseEntity baseEntity = new BaseEntity();
        baseEntity.setFornavn("thomas");
        entityManager.persist(baseEntity);
        return "SUCCESS";
    }

    @Transactional
    public List<BaseEntity> getEntity(){
        log.info("***** getEntity called **********");
        TypedQuery<BaseEntity> query = entityManager.createQuery("select b from BaseEntity as b", BaseEntity.class);
        List<BaseEntity> resultList = query.getResultList();
        log.info("Fant {} entiteter", resultList.size());
        return resultList;
    }
}
