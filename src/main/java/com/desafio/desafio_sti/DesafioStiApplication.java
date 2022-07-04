package com.desafio.desafio_sti;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import com.desafio.desafio_sti.Controller.MensagemController;

@EnableMongoRepositories
@SpringBootApplication
public class DesafioStiApplication {
	public static void main(String[] args) {
		SpringApplication.run(MensagemController.class, args);
	}
}
