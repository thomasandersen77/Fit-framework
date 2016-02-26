package com.github.fit.examples.data;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import no.husbanken.config.Config;

public class EntityManagerProducer {

    @Config(key = "db.url")
    @Inject
    private String dbUrl;

    @Config(key = "db.user")
    @Inject
    private String user;

    @Config(key = "db.password")
    @Inject
    private String password;

    @Produces
    @RequestScoped
    protected EntityManager createEntityManager() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("primary", getDbProperties());
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return entityManager;
    }

    protected void closeEntityManager(@Disposes EntityManager entityManager) {
        if (entityManager.isOpen()) {
            entityManager.close();
        }
    }

    private Map<String, String> getDbProperties() {
        Map<String, String> properties = new HashMap<>();
        properties.put("javax.persistence.jdbc.url", dbUrl);
        properties.put("javax.persistence.jdbc.user", user);
        properties.put("javax.persistence.jdbc.password", password);
        return properties;
    }
}