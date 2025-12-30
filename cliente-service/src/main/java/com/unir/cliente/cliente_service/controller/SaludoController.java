package com.unir.cliente.cliente_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*")
@RestController
public class SaludoController {

    @GetMapping("/saludo")
    public String saludo() {
        return "Hola desde el microservicio cliente";
    }
}
