package com.unir.cliente.cliente_service.controller;

import com.unir.cliente.cliente_service.elastic.FacetsResponse;
import com.unir.cliente.cliente_service.elastic.ProductoDocument;
import com.unir.cliente.cliente_service.elastic.ProductoFacetService;
import com.unir.cliente.cliente_service.model.Producto;
import com.unir.cliente.cliente_service.service.ProductoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService productoService;
    private final ProductoFacetService productoFacetService;

    public ProductoController(ProductoService productoService, ProductoFacetService productoFacetService) {
        this.productoService = productoService;
        this.productoFacetService = productoFacetService;
    }

    // LISTAR PRODUCTOS (DESDE H2)
    @GetMapping
    public List<Producto> obtenerProductos() {
        return productoService.listar();
    }

    // BUSCAR PRODUCTOS (DESDE ELASTICSEARCH)
    @GetMapping("/buscar")
    public List<ProductoDocument> buscarProductos(@RequestParam String texto) {
        return productoService.buscar(texto);
    }

    // FACETS (DESDE ELASTICSEARCH)
    @GetMapping("/facets")
    public FacetsResponse facets() {
        return productoFacetService.obtenerFacets();
    }
}
