package com.github.fit;

import com.github.fit.common.ServerConfig;
import com.github.fit.common.TestContainer;
import com.github.fit.undertow.UndertowContainer;

public class UndertowContainerFactory {

    public static TestContainer configure(ServerConfig config) {
        return new UndertowContainer(config);
    }
}
