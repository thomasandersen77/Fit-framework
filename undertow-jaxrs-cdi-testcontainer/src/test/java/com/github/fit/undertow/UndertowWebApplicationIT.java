package com.github.fit.undertow;

import com.github.fit.examples.MyApplication;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import lombok.extern.slf4j.Slf4j;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@Slf4j
public class UndertowWebApplicationIT {
    public static void main(String[] args) {
        //************************* Må ha et endepunkt for å teste EJB'en *****************/
        int wiremockPort = HttpUtils.allocatePort();
        System.setProperty("it.ejb.url", "http://localhost:" + wiremockPort +"/integration/ejb/message");

        WireMock.configureFor("localhost", wiremockPort);
        WireMockServer wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().bindAddress("localhost").port(wiremockPort));
        wireMockServer.start();
        WireMock.stubFor(get(urlEqualTo("/integration/ejb/message"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "text/plain")
                        .withBody("Wiremock Respons fra EJB")));

        //************************* Her starter UndertowServer med CDI og JAX RS *****************/

        final UndertowServer server = new UndertowServer(MyApplication.class, "/rest");
        server.setBindAddress("localhost");
        server.setPort(8080);
        server.start();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                log.info("Shutting down UndertowServer");
                server.stop();
                wireMockServer.shutdown();
            }
        });
        log.info("Shut Down Hook Attached.");
    }

}
