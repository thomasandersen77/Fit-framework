package com.github.fit.common;

import lombok.*;

import javax.inject.Inject;
import javax.ws.rs.core.Application;

@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ServerConfig {
    private Application application;
    private String scanPackage;
    private int port = 9998;

}
