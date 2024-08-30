package com.devsu.accounts.movements;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.devsu.accounts.movements.domain.repositories")
@EnableFeignClients
public class AccountsMovementApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsMovementApplication.class, args);
	}

}
