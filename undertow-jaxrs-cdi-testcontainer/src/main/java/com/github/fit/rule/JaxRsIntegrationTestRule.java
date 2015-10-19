package com.github.fit.rule;

import com.github.fit.undertow.UndertowContainer;
import com.github.fit.undertow.HttpUtils;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.rules.MethodRule;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import javax.ws.rs.core.Application;


public class JaxRsIntegrationTestRule implements MethodRule, TestRule {
    private final int wiremockPort = HttpUtils.allocatePort();
    private WireMockServer wireMockServer;
    private UndertowContainer testContainer;

    public JaxRsIntegrationTestRule(Application application) {
        this.wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().port(wiremockPort));
        this.testContainer = new UndertowContainer(application);
    }

    public int getAppPort() {
        return testContainer.getPort();
    }

    public int getWiremockPort() {
        return wiremockPort;
    }

    @Override
    public Statement apply(Statement statement, FrameworkMethod frameworkMethod, Object o) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                wireMockServer.start();
                WireMock.configureFor("localhost", getWiremockPort());
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
