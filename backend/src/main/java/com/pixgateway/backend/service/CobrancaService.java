package com.pixgateway.backend.service;

import java.time.OffsetDateTime;
import java.util.UUID;
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

    // --- MÉTODO 1: CRIAR A COBRANÇA ---
    public Cobranca criarCobranca(Cobranca cobranca) {
        // Chama a "API" (Mock)
        PixResponseDTO response = pixProvider.gerarCobranca(cobranca.getValor(), cobranca.getChavePix());

        // Preenche a nossa entidade com os dados que o "banco" mandou
        cobranca.setTxid(response.getTxid());
        cobranca.setPixCopiaCola(response.getPixCopiaCola());
        cobranca.setStatus("PENDENTE");
        
        // SALVA NO BANCO E FINALIZA O MÉTODO AQUI
        return repository.save(cobranca);
    }

    // --- MÉTODO 2: PAGAR A COBRANÇA ---
    public Cobranca pagarCobranca(UUID id) {
        // 1. Busca a cobrança no banco de dados pelo ID
        Cobranca cobrancaExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cobrança não encontrada!"));

        // 2. Verifica se já não está paga
        if ("PAGO".equals(cobrancaExistente.getStatus())) {
            throw new RuntimeException("Esta cobrança já foi paga.");
        }

        // 3. Atualiza os dados
        cobrancaExistente.setStatus("PAGO");
        cobrancaExistente.setDataPagamento(OffsetDateTime.now());

        // 4. Salva a atualização no banco
        return repository.save(cobrancaExistente);
    }

    // --- MÉTODO 3: LISTAR TODAS ---
    public List<Cobranca> listarTodas() {
        return repository.findAll();
    }
}