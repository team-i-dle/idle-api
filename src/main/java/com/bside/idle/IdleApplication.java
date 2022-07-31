package com.bside.idle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class IdleApplication {

	public static void main(String[] args) {
		SpringApplication.run(IdleApplication.class, args);
	}

}
