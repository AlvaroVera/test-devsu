package com.devsu.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.devsu.client.domain.repositories")
public class ClientPersonApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientPersonApplication.class, args);
	}

}
