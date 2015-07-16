package org.github.fit;

import lombok.Builder;
import lombok.NoArgsConstructor;
import org.github.fit.grizzly.GrizzlyTestContainer;
import org.github.fit.undertow.UndertowTestContainer;

import javax.inject.Inject;

@NoArgsConstructor
public class TestContainerFactory {

    @Builder(buildMethodName = "createServer", builderMethodName = "undertow")
    public static TestContainer createUndertow(ServerConfig config) {
        return new UndertowTestContainer(config);
    }

    @Builder(buildMethodName = "createContainer", builderMethodName = "grizzly")
    public static TestContainer createGrizzly(ServerConfig config) {
        return new GrizzlyTestContainer(config);
    }

}
