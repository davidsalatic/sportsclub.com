package com.eryce.sportsclub.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class ServerProperties {

    @Value("${server.port}")
    private String port;
}
