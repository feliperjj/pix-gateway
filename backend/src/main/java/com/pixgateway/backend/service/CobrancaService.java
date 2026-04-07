package com.pixgateway.backend.service;

import com.pixgateway.backend.domain.Cobranca;
import com.pixgateway.backend.domain.dto.PixResponseDTO; // Importe o DTO
import com.pixgateway.backend.infrastructure.pix.PixProvider; // Importe a Interface
import com.pixgateway.backend.repository.CobrancaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CobrancaService {

    @Autowired
    private CobrancaRepository repository;

    @Autowired
    private PixProvider pixProvider; // O Spring vai injetar o Mock automaticamente

    public Cobranca criarCobranca(Cobranca cobranca) {
        // Chama a "API" (Mock)
        PixResponseDTO response = pixProvider.gerarCobranca(cobranca.getValor(), cobranca.getChavePix());

        // Preenche a nossa entidade com os dados que o "banco" mandou
        cobranca.setTxid(response.getTxid());
        cobranca.setPixCopiaCola(response.getPixCopiaCola());
        cobranca.setStatus("PENDENTE");

        return repository.save(cobranca);
    }
    public List<Cobranca> listarTodas() {
        return repository.findAll();
    }
}