package com.pixgateway.backend.controller;

import com.pixgateway.backend.domain.Cobranca;
import com.pixgateway.backend.service.CobrancaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cobrancas")
public class CobrancaController {

    @Autowired
    private CobrancaService service;

    @PostMapping
    public Cobranca criar(@Valid @RequestBody Cobranca cobranca) {
        return service.criarCobranca(cobranca);
    }

    @GetMapping
    public List<Cobranca> listar() {
        return service.listarTodas();
    }
}