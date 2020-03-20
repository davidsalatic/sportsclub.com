package com.eryce.sportsclub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync(proxyTargetClass = true)
@EnableJpaRepositories
@SpringBootApplication
public class SportsclubApplication {


	public static void main(String[] args) {
		SpringApplication.run(SportsclubApplication.class, args);
	}
}
