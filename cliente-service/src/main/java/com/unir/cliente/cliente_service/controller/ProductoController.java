package com.unir.cliente.cliente_service.controller;

import com.unir.cliente.cliente_service.elastic.FacetsResponse;
import com.unir.cliente.cliente_service.elastic.ProductoDocument;
import com.unir.cliente.cliente_service.elastic.ProductoFacetService;
import com.unir.cliente.cliente_service.model.Producto;
import com.unir.cliente.cliente_service.service.ProductoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService productoService;
    private final ProductoFacetService productoFacetService;

    public ProductoController(ProductoService productoService, ProductoFacetService productoFacetService) {
        this.productoService = productoService;
        this.productoFacetService = productoFacetService;
    }

    // LISTAR PRODUCTOS (DESDE H2 / JPA)
    @GetMapping
    public List<Producto> obtenerProductos() {
        return productoService.listar();
    }

    //  BUSCAR PRODUCTOS (DESDE ELASTICSEARCH - LOCAL)
    @GetMapping("/buscar")
    public List<ProductoDocument> buscarProductos(@RequestParam String texto) {
        return productoService.buscar(texto);
    }

    /**
     * FACETS:
     * 1) Intenta Elastic (local)
     * 2) Si Elastic no está disponible (Render), hace fallback con JPA/H2
     */
    @GetMapping("/facets")
    public FacetsResponse facets() {

        // 1) Intentar Elastic primero (LOCAL)
        try {
            FacetsResponse elasticFacets = productoFacetService.obtenerFacets();
            if (elasticFacets != null) {
                return elasticFacets;
            }
        } catch (Exception ignored) {
            // Elastic no disponible → fallback JPA
        }

        // 2) Fallback con JPA/H2 (sirve en Render)
        List<Producto> productos = productoService.listar();

        // Facet por nombre
        List<FacetsResponse.Bucket> nombres = productos.stream()
                .collect(Collectors.groupingBy(
                        p -> (p.getNombre() == null || p.getNombre().isBlank()) ? "Sin nombre" : p.getNombre(),
                        Collectors.counting()
                ))
                .entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> new FacetsResponse.Bucket(e.getKey(), e.getValue()))
                .toList();

        // Facet por rango de precio
        List<FacetsResponse.Bucket> precios = productos.stream()
                .collect(Collectors.groupingBy(
                        p -> rangoPrecio(p.getPrecio()),
                        Collectors.counting()
                ))
                .entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> new FacetsResponse.Bucket(e.getKey(), e.getValue()))
                .toList();

        return new FacetsResponse(nombres, precios);
    }

    // Rango de precio simple (puedes ajustar los cortes si quieres)
    private String rangoPrecio(Double precio) {
        double p = (precio == null) ? 0 : precio;
        if (p < 50) return "0-49";
        if (p < 100) return "50-99";
        if (p < 200) return "100-199";
        if (p < 500) return "200-499";
        return "500+";
    }
}
