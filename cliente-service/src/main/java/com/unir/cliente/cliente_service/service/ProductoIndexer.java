package com.unir.cliente.cliente_service.service;

import com.unir.cliente.cliente_service.elastic.ProductoDocument;
import com.unir.cliente.cliente_service.elastic.ProductoElasticRepository;
import com.unir.cliente.cliente_service.model.Producto;
import com.unir.cliente.cliente_service.repository.ProductoRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Profile("!render") // En Render NO se crea este bean
@Component
public class ProductoIndexer {

    private final ProductoRepository productoRepository;
    private final ProductoElasticRepository elasticRepository;

    public ProductoIndexer(ProductoRepository productoRepository,
                           ProductoElasticRepository elasticRepository) {
        this.productoRepository = productoRepository;
        this.elasticRepository = elasticRepository;
    }

    @PostConstruct
    public void indexarProductos() {

        System.out.println("INICIANDO INDEXACIÓN EN ELASTICSEARCH");

        List<Producto> productos = productoRepository.findAll();
        System.out.println("Productos encontrados en H2: " + productos.size());

        elasticRepository.deleteAll();

        for (Producto p : productos) {
            ProductoDocument doc = new ProductoDocument(
                    p.getId(),
                    p.getNombre(),
                    p.getDescripcion(),
                    p.getPrecio()
            );
            elasticRepository.save(doc);
        }

        System.out.println("INDEXACIÓN COMPLETADA");
    }
}
