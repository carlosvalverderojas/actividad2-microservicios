package com.unir.cliente.cliente_service.service;

import com.unir.cliente.cliente_service.elastic.ProductoDocument;
import com.unir.cliente.cliente_service.elastic.ProductoElasticRepository;
import com.unir.cliente.cliente_service.model.Producto;
import com.unir.cliente.cliente_service.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final ProductoElasticRepository elasticRepository;

    public ProductoService(ProductoRepository productoRepository,
                            ProductoElasticRepository elasticRepository) {
        this.productoRepository = productoRepository;
        this.elasticRepository = elasticRepository;
    }

    public List<Producto> listar() {
        return productoRepository.findAll();
    }

    public Producto guardar(Producto producto) {
        Producto guardado = productoRepository.save(producto);

        ProductoDocument doc = new ProductoDocument(
                guardado.getId(),
                guardado.getNombre(),
                guardado.getDescripcion(),
                guardado.getPrecio()
        );

        elasticRepository.save(doc);
        return guardado;
    }

    public List<ProductoDocument> buscar(String texto) {
        return elasticRepository.buscarFullText(texto);
    }
}
