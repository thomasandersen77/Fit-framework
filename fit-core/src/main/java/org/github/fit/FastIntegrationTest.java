package org.github.fit;

import lombok.Getter;
import org.github.fit.grizzly.GrizzlyTestContainer;
import org.github.fit.undertow.UndertowTestContainer;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.xnio.channels.UnsupportedOptionException;

import javax.enterprise.inject.Alternative;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;


@RunWith(BlockJUnit4ClassRunner.class)
public class FastIntegrationTest {

    private TestContainer container;

    public FastIntegrationTest(ServerConfig config, Class<? extends TestContainer> containerClz) {
        if(containerClz == GrizzlyTestContainer.class)
            container = TestContainerFactory.grizzly().config(config).createContainer();
        else if(containerClz == UndertowTestContainer.class)
            container = TestContainerFactory.undertow().config(config).createServer();
    }

    public WebTarget target(final String path) {
        return configureClient().target(path);
    }


    public Client configureClient(){
        throw new UnsupportedOptionException();
    }
}
