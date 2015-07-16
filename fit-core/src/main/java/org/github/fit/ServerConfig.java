package org.github.fit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.ws.rs.core.Application;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ServerConfig {
    private Application application;
    private String scanPackage;
    private String address;
    private int port;

}
