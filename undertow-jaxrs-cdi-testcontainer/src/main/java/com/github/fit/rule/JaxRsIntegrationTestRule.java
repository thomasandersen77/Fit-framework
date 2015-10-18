package com.github.fit.rule;

import com.github.fit.undertow.UndertowContainerFactory;
import com.github.fit.core.HttpUtils;
import com.github.fit.core.ServerConfig;
import com.github.fit.core.TestContainer;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.junit.rules.MethodRule;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;


import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;

@Slf4j
public class JaxRsIntegrationTestRule implements MethodRule, TestRule {

    private WireMockServer wireMockServer;
    private TestContainer testContainer;
    private String wmHost = "localhost";

    private final int localAppPort = HttpUtils.allocatePort();
    private final int wmPort = HttpUtils.allocatePort();

    public JaxRsIntegrationTestRule(Application application) {
        log.info("localApp Port: {}", localAppPort);
        log.info("Integration Port: {}", wmPort);

        this.wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().port(wmPort));
        this.testContainer = UndertowContainerFactory.configure(ServerConfig.builder()
                .application(application)
                .port(localAppPort)
                .rootClass(application.getClass()).build());
    }

    public WebTarget target(String url) {
        ResteasyClient client = (ResteasyClient) ResteasyClientBuilder.newClient();
        String webTargetUrl = "http://localhost:" + localAppPort + "/" + url;
        ResteasyWebTarget target = client.target(webTargetUrl);
        log.info("Created WebTarget for URL: {}", webTargetUrl);
        return target;
    }

    public int getLocalContainerPort() {
        return localAppPort;
    }

    public int getWmPort() {
        return wmPort;
    }

    @Override
    public Statement apply(Statement statement, FrameworkMethod frameworkMethod, Object o) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                wireMockServer.start();
                WireMock.configureFor(wmHost, getWmPort());
                testContainer.start();
                try {
                    before();
                    statement.evaluate();
                } finally {
                    after();
                    testContainer.stop();
                    wireMockServer.stop();
                }
            }
        };
    }

    @Override
    public Statement apply(Statement statement, Description description) {
        return apply(statement, null, null);
    }


    protected void before() {
        // NOOP
    }

    protected void after() {
        // NOOP
    }
}
