package com.dunnas.reservasalas;

import com.dunnas.reservasalas.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ReservasalasApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReservasalasApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(
			UserService userService
	) {
		return args -> {
			userService.CreateFirstAdmin();
		};
	}
}
