package com.unir.cliente.cliente_service.config;

import com.unir.cliente.cliente_service.elastic.ProductoDocument;
import com.unir.cliente.cliente_service.elastic.ProductoElasticRepository;
import com.unir.cliente.cliente_service.model.Producto;
import com.unir.cliente.cliente_service.repository.ProductoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final ProductoRepository productoRepository;
    private final ProductoElasticRepository elasticRepository;

    public DataLoader(ProductoRepository productoRepository,
                      ProductoElasticRepository elasticRepository) {
        this.productoRepository = productoRepository;
        this.elasticRepository = elasticRepository;
    }

    @Override
    public void run(String... args) {

        System.out.println("DataLoader EJECUTÁNDOSE");

        // 1) Si H2 está vacío, insertamos los 12 productos
        if (productoRepository.count() == 0) {

            List<Producto> productos = List.of(
                    new Producto("Laptop básica", 600.0, "Perfecta para estudio", "laptop.jpg"),
                    new Producto("Mouse inalámbrico", 20.0, "Ergonómico y preciso", "mouse.jpg"),
                    new Producto("Teclado mecánico", 80.0, "Ideal para escribir", "teclado.webp"),
                    new Producto("Monitor 24\"", 150.0, "Resolución Full HD", "monitor.jpg"),
                    new Producto("Audífonos", 45.0, "Sonido envolvente", "audifonos.jpg"),
                    new Producto("Webcam HD", 60.0, "Ideal para videollamadas", "webcam.jpg"),
                    new Producto("Disco SSD 1TB", 120.0, "Alta velocidad de lectura", "ssd.jpg"),
                    new Producto("Memoria RAM 16GB", 70.0, "DDR4 de alto rendimiento", "ram.jpg"),
                    new Producto("Router WiFi", 90.0, "Alta cobertura inalámbrica", "router.jpg"),
                    new Producto("Tablet", 200.0, "Pantalla de 10 pulgadas", "tablet.jpg"),
                    new Producto("Impresora", 180.0, "Multifuncional", "impresora.jpg"),
                    new Producto("Smartphone", 350.0, "128GB de almacenamiento", "smartphone.jpg")
            );

            productoRepository.saveAll(productos);
            System.out.println("Productos insertados en H2: " + productoRepository.count());
        }

        // 2) Reindexar en Elasticsearch desde H2
        elasticRepository.deleteAll();

        List<Producto> lista = productoRepository.findAll();
        for (Producto p : lista) {
            ProductoDocument doc = new ProductoDocument(
                    p.getId(),
                    p.getNombre(),
                    p.getDescripcion(),
                    p.getPrecio()
            );
            elasticRepository.save(doc);
        }

        System.out.println("Productos indexados en Elasticsearch: " + lista.size());
    }
}
