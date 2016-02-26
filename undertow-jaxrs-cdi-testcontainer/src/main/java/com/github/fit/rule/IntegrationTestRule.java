package com.github.fit.rule;

import javax.servlet.ServletException;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;

import com.github.fit.undertow.HttpUtils;
import com.github.fit.undertow.UndertowServer;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.rules.MethodRule;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;


public class IntegrationTestRule implements MethodRule, TestRule {
    private final int testContainerPort = HttpUtils.allocatePort();
    private final int wiremockPort = HttpUtils.allocatePort();
    private WireMockServer wireMockServer;
    private boolean runWiremock;
    private UndertowServer undertowServer = new UndertowServer(testContainerPort);


    public IntegrationTestRule(Application application, boolean runWiremock) {
        if(runWiremock) {
            this.wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().port(wiremockPort));
        }
        try {
            undertowServer.startContainer(application);
        } catch (ServletException e) {
            e.printStackTrace();
        }
        this.runWiremock = runWiremock;
    }

    public int getAppPort() {
        return undertowServer.getServerPort();
    }

    public int getWiremockPort() {
        return wiremockPort;
    }

    public WebTarget target(String url) {
        return null;
    }

    @Override
    public Statement apply(Statement statement, FrameworkMethod frameworkMethod, Object o) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                if(runWiremock && wireMockServer != null && !wireMockServer.isRunning()) {
                    wireMockServer.start();
                    WireMock.configureFor("localhost", getWiremockPort());
                }

                try {
                    before();
                    statement.evaluate();
                } finally {
                    after();

                    if(runWiremock && wireMockServer != null && wireMockServer.isRunning()) {
                        wireMockServer.stop();
                    }
                    undertowServer.stopContainer();
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
