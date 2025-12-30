package com.unir.eureka.eureka.server;

// Importa la anotación para habilitar Eureka Server
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

// Importaciones básicas de Spring Boot
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Marca esta aplicación como un servidor Eureka
@EnableEurekaServer
@SpringBootApplication
public class EurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaServerApplication.class, args);
	}

}
