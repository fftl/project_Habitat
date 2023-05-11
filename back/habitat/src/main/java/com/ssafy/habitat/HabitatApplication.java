package com.ssafy.habitat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@EnableJpaAuditing
@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.ssafy.habitat.repository"})
@EnableRedisRepositories(basePackages = {"com.ssafy.habitat.redis"})
public class HabitatApplication {

	public static void main(String[] args) {
		SpringApplication.run(HabitatApplication.class, args);
	}

}
