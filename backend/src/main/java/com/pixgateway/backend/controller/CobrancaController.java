package com.pixgateway.backend.controller;

import java.util.UUID;
import java.util.List;
import com.pixgateway.backend.domain.Cobranca;
import com.pixgateway.backend.service.CobrancaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*") // <--- ADICIONE ESTA LINHA AQUI!
@RestController
@RequestMapping("/api/cobrancas")
public class CobrancaController {

    @Autowired
    private CobrancaService service;

    @PostMapping
    public Cobranca criar(@Valid @RequestBody Cobranca cobranca) {
        return service.criarCobranca(cobranca);
    }

    @PostMapping("/{id}/pagar")
    public Cobranca simularPagamento(@PathVariable UUID id) {
        return service.pagarCobranca(id);
    }

    @GetMapping
    public List<Cobranca> listar() {
        return service.listarTodas();
    }
}