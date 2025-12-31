package com.unir.cliente.cliente_service.service;

import com.unir.cliente.cliente_service.elastic.ProductoDocument;
import com.unir.cliente.cliente_service.elastic.ProductoElasticRepository;
import com.unir.cliente.cliente_service.model.Producto;
import com.unir.cliente.cliente_service.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final Optional<ProductoElasticRepository> elasticRepository;

    public ProductoService(ProductoRepository productoRepository,
                           Optional<ProductoElasticRepository> elasticRepository) {
        this.productoRepository = productoRepository;
        this.elasticRepository = elasticRepository;
    }

    public List<Producto> listar() {
        return productoRepository.findAll();
    }

    public Producto guardar(Producto producto) {
        Producto guardado = productoRepository.save(producto);

        // Si Elastic existe (local), indexa. Si no (Render), no hace nada.
        elasticRepository.ifPresent(repo -> {
            ProductoDocument doc = new ProductoDocument(
                    guardado.getId(),
                    guardado.getNombre(),
                    guardado.getDescripcion(),
                    guardado.getPrecio()
            );
            repo.save(doc);
        });

        return guardado;
    }

    public List<ProductoDocument> buscar(String texto) {
        // Si Elastic existe (local), busca. Si no (Render), devuelve vacío.
        return elasticRepository
                .map(repo -> repo.buscarFullText(texto))
                .orElse(Collections.emptyList());
    }
}
