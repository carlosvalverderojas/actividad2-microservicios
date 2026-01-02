package com.unir.cliente.cliente_service.service;

import com.unir.cliente.cliente_service.elastic.ProductoDocument;
import com.unir.cliente.cliente_service.elastic.ProductoElasticRepository;
import com.unir.cliente.cliente_service.model.Producto;
import com.unir.cliente.cliente_service.repository.ProductoRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.context.annotation.Profile;

@Profile("!render")
@Service
public class ProductoIndexService {

    private final ProductoRepository productoRepository;
    private final Optional<ProductoElasticRepository> productoElasticRepository;

    public ProductoIndexService(ProductoRepository productoRepository,
                                Optional<ProductoElasticRepository> productoElasticRepository) {
        this.productoRepository = productoRepository;
        this.productoElasticRepository = productoElasticRepository;
    }

    // Se ejecuta automáticamente al iniciar el microservicio
    @PostConstruct
    public void indexarProductos() {

        // En Render (sin Elastic): no hace nada y NO falla
        if (productoElasticRepository.isEmpty()) {
            System.out.println("Elasticsearch no disponible. Se omite indexación inicial.");
            return;
        }

        //En local/Docker (con Elastic): funciona igual que antes
        ProductoElasticRepository repo = productoElasticRepository.get();

        repo.deleteAll();

        List<Producto> productos = productoRepository.findAll();

        productos.forEach(producto -> {
            ProductoDocument doc = new ProductoDocument(
                    producto.getId(),
                    producto.getNombre(),
                    producto.getDescripcion(),
                    producto.getPrecio()
            );
            repo.save(doc);
        });

        System.out.println("Productos indexados en Elasticsearch: " + productos.size());
    }
}
