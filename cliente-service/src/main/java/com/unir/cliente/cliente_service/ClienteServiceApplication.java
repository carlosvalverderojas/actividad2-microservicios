package com.unir.cliente.cliente_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication(scanBasePackages = "com.unir.cliente.cliente_service")
@EnableElasticsearchRepositories(basePackages = "com.unir.cliente.cliente_service.elastic")
public class ClienteServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClienteServiceApplication.class, args);
    }
}
