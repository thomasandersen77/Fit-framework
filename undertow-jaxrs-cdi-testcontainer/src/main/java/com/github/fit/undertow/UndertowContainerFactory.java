package com.github.fit.undertow;

import com.github.fit.core.JsonUtil;
import com.github.fit.core.ServerConfig;
import com.github.fit.core.TestContainer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UndertowContainerFactory {

    public static TestContainer configure(ServerConfig config) {
        return new UndertowContainer(config);
    }
}
