package com.groomthon.univ;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class UnivApplication {

	public static void main(String[] args) {
		SpringApplication.run(UnivApplication.class, args);
	}

}
