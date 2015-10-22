package com.github.fit.examples;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;

import org.hibernate.jpa.boot.spi.EntityManagerFactoryBuilder;

@ApplicationScoped
public class MyProducer {
    /*
    @Produces
    public DataSource dataSource() {
        DataSource dataSource =

         * Class.forName("org.h2.Driver");
         Connection conn = DriverManager.getConnection("jdbc:h2:file:./target/kommunetjeneste-testdb", "sa", "sa");
         Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(conn));
         Liquibase liquibase = new Liquibase("db.changelog.xml",new ClassLoaderResourceAccessor(), database);
         liquibase.update("test");
         //Denne bruker META-INF/persistence.xml fra test som har samme jdbc url som over:
         emFactory = Persistence.createEntityManagerFactory("kommunetjeneste");

    }
*/
    @Produces
    public EntityManager entityManager(){
        EntityManager entityManager = entityManagerFatory().createEntityManager();
        System.out.println("Create EntityManager : " + entityManager);
        return entityManager;
    }

    @Produces
    public EntityManagerFactory entityManagerFatory() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Test");
        System.out.println("Create EntityManagerFactory : " + entityManagerFactory);
        return entityManagerFactory;
    }
}
