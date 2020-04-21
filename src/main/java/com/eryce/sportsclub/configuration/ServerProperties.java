package com.eryce.sportsclub.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServerProperties {

    @Value("${server.port}")
    private String port;

    public String getPort() {
        return port;
    }
}
