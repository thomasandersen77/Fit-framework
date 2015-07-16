package org.github.fit;

import lombok.Getter;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.xnio.channels.UnsupportedOptionException;

import javax.enterprise.inject.Alternative;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;


@RunWith(BlockJUnit4ClassRunner.class)
public class FastIntegrationTest {

    @Getter
    private TestContainer container;

    @Inject
    public FastIntegrationTest(TestContainerFactory.TestContainerBuilder factoryBuilder) {


    }

    public WebTarget target(final String path) {
        return configureClient().target(path);
    }


    public Client configureClient(){
        throw new UnsupportedOptionException();
    }
}
