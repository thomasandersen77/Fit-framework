package com.github.fit.client;


import java.sql.Connection;

import com.github.fit.examples.JaxrsApplication;
import com.github.fit.undertow.UndertowServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.h2.jdbcx.JdbcDataSource;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import javax.servlet.ServletException;
import javax.sql.DataSource;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.*;

public class ClientIntegrationTest {

    public static final String WIREMOCK_STRING_RESPONSE = "**** HELLO FROM WIREMOCK ******";

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(WireMockConfiguration.wireMockConfig().port(9090));

    UndertowServer undertowServer = new UndertowServer(8080);

    @Before
    public void setUpMockedAdress() {
        System.setProperty("it.ejb.url", "http://localhost:" + 9090 +"/integration/ejb/message");
    }

    @Before public void configureStub() throws ServletException {
        stubFor(get(urlEqualTo("/integration/ejb/message"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "text/plain")
                        .withBody(WIREMOCK_STRING_RESPONSE)));
        undertowServer.startContainer(new JaxrsApplication());
    }

    @Test
    public void test_my_resource_with_valid_username_to_string() throws Exception {
        String response = ResteasyClientBuilder.newClient()
                .target("http://localhost:" + 8080)
                .request(MediaType.TEXT_PLAIN_TYPE)
                .header("username", "thomas")
                .get(String.class);

        assertNotNull(response);
        System.err.println(response);
    }

    @After
    public void tearDown() throws Exception {
        undertowServer.stopContainer();
    }

    @BeforeClass
    public static void liquidBaseUpdate()  {
        try (final Connection connection = lageH2Datasource().getConnection()) {
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase = new Liquibase("db.changelog.xml", new ClassLoaderResourceAccessor(), database);
            liquibase.update("");
        } catch (final Exception e) {
            System.err.println("Antar at update er gjort: " + e);
        }
    }

    public static DataSource lageH2Datasource() {
        JdbcDataSource jdbcdatasource = new JdbcDataSource();
        jdbcdatasource.setUrl("jdbc:h2:mem:startlan;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false;MODE=MSSQLServer");
        jdbcdatasource.setPassword("sa");
        jdbcdatasource.setUser("sa");
        return jdbcdatasource;
    }



}
