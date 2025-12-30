package com.unir.cliente.cliente_service.service;

import com.unir.cliente.cliente_service.elastic.ProductoDocument;
import com.unir.cliente.cliente_service.elastic.ProductoElasticRepository;
import com.unir.cliente.cliente_service.model.Producto;
import com.unir.cliente.cliente_service.repository.ProductoRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoIndexService {

    private final ProductoRepository productoRepository;
    private final ProductoElasticRepository productoElasticRepository;

    public ProductoIndexService(ProductoRepository productoRepository,
                                ProductoElasticRepository productoElasticRepository) {
        this.productoRepository = productoRepository;
        this.productoElasticRepository = productoElasticRepository;
    }

    // Se ejecuta automáticamente al iniciar el microservicio
    @PostConstruct
    public void indexarProductos() {

        productoElasticRepository.deleteAll();

        List<Producto> productos = productoRepository.findAll();

        productos.forEach(producto -> {
            ProductoDocument doc = new ProductoDocument(
                    producto.getId(),
                    producto.getNombre(),
                    producto.getDescripcion(),
                    producto.getPrecio()
            );
            productoElasticRepository.save(doc);
        });

        System.out.println("Productos indexados en Elasticsearch: " + productos.size());
    }
}
