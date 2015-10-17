package com.github.fit.jersey;

import com.github.fit.common.ServerConfig;
import com.github.fit.common.TestContainer;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class GrizzlyTestContainerFactory {

    @Builder(buildMethodName = "createContainer", builderMethodName = "grizzly")
    public static TestContainer create(ServerConfig config) {
        return new GrizzlyTestContainer(config);
    }

}
