package com.unir.cliente.cliente_service.elastic;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "productos")
public class ProductoDocument {

    @Id
    private Long id;

    // Necesario para hacer agregaciones (facets) por nombre usando terms
    @Field(type = FieldType.Text, fielddata = true)
    private String nombre;

    @Field(type = FieldType.Text)
    private String descripcion;

    @Field(type = FieldType.Double)
    private Double precio;

    public ProductoDocument() {}

    public ProductoDocument(Long id, String nombre, String descripcion, Double precio) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public Double getPrecio() { return precio; }

    public void setId(Long id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setPrecio(Double precio) { this.precio = precio; }
}
