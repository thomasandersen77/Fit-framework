package com.github.fit.rule;

import com.github.fit.UndertowContainerFactory;
import com.github.fit.app.MyApplication;
import com.github.fit.common.TestContainer;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.rules.MethodRule;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import static com.github.fit.common.ServerConfig.builder;

public class TestAppConfigurationRule implements MethodRule, TestRule {
    private final int integrationPort;
    private final int localPort;
    private WireMockServer wireMockServer;
    private TestContainer testContainer;

    public TestAppConfigurationRule(int localPort, int integrationPort, String scanPackage) {
        System.out.println("local: " + localPort);
        System.out.println("integration: " + integrationPort);
        this.localPort = localPort;
        this.integrationPort = integrationPort;
        this.wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().port(integrationPort));
        this.testContainer = UndertowContainerFactory.configure(builder()
                .application(new MyApplication())
                .port(localPort)
                .scanPackage(scanPackage).build());
    }

    public int getLocalPort() {
        return localPort;
    }

    public int getIntegrationPort() {
        return integrationPort;
    }

    @Override
    public Statement apply(Statement statement, FrameworkMethod frameworkMethod, Object o) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                wireMockServer.start();
                WireMock.configureFor("localhost", getIntegrationPort());
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
