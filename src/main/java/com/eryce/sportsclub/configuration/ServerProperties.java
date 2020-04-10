package com.eryce.sportsclub.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServerProperties {

    @Value("${server.address}")
    private String address;

    @Value("${server.port}")
    private String port;

    public String getAddress() {
        return address;
    }

    public String getPort() {
        return port;
    }
}
