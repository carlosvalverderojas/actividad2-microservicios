package com.unir.cliente.cliente_service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@Profile("!render") // en Render NO se activa
@EnableElasticsearchRepositories(basePackages = "com.unir.cliente.cliente_service.elastic")
public class ElasticRepositoriesConfig {
}
