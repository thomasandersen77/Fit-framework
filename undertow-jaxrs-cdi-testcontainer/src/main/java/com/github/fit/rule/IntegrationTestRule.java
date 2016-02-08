package com.github.fit.rule;

import com.github.fit.mongo.EmbeddedMongoRunner;
import com.github.fit.undertow.UndertowServer;
import com.github.fit.undertow.HttpUtils;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.rules.MethodRule;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;


public class IntegrationTestRule implements MethodRule, TestRule {
    private final int testContainerPort = HttpUtils.allocatePort();
    private final int wiremockPort = HttpUtils.allocatePort();
    private WireMockServer wireMockServer;
    private UndertowServer testContainer;
    private EmbeddedMongoRunner embeddedMongoRunner=new EmbeddedMongoRunner();
    private boolean runWiremock;
    private boolean runEmbeddedMongo;


    public IntegrationTestRule(Application application, boolean runWiremock, boolean runEmbeddedMongo) {
        if(runWiremock) {
            this.wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().port(wiremockPort));
        }
        this.testContainer = new UndertowServer(testContainerPort, application.getClass());
        this.runWiremock = runWiremock;
        this.runEmbeddedMongo =runEmbeddedMongo;
    }

    public int getAppPort() {
        return testContainer.getPort();
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
                if(runEmbeddedMongo) {
                    embeddedMongoRunner.startMongo();
                }
                testContainer.start();
                try {
                    before();
                    statement.evaluate();
                } finally {
                    after();
                    testContainer.stop();
                    if(runWiremock && wireMockServer != null && wireMockServer.isRunning()) {
                        wireMockServer.stop();
                    }
                    if(runEmbeddedMongo) {
                        embeddedMongoRunner.shutdownMongo();
                    }
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
