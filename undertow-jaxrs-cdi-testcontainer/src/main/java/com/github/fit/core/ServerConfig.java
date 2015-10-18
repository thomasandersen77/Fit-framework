package com.github.fit.core;

import lombok.*;

import javax.ws.rs.core.Application;

@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ServerConfig {
    private Application application;
    private Class<? extends Application> rootClass;
    private int port = 9998;

}
