package com.unir.cliente.cliente_service.repository;

import com.unir.cliente.cliente_service.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findByNombreContainingIgnoreCase(String texto);
}
