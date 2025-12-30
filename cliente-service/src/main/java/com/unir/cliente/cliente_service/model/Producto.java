package com.unir.cliente.cliente_service.model;

import jakarta.persistence.*;

@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private Double precio;
    private String descripcion;
    private String imagen;

    // Constructor vacío (OBLIGATORIO para JPA)
    public Producto() {}

    // Constructor con datos
    public Producto(String nombre, Double precio, String descripcion, String imagen) {
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.imagen = imagen;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public Double getPrecio() { return precio; }
    public String getDescripcion() { return descripcion; }
    public String getImagen() { return imagen; }

    public void setId(Long id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setPrecio(Double precio) { this.precio = precio; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setImagen(String imagen) { this.imagen = imagen; }
}
