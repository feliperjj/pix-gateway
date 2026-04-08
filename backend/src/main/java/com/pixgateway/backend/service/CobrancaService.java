package com.pixgateway.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.OffsetDateTime;
import java.util.UUID;
import java.util.List;
import com.pixgateway.backend.domain.Cobranca;
import com.pixgateway.backend.domain.dto.PixResponseDTO;
import com.pixgateway.backend.infrastructure.pix.PixProvider;
import com.pixgateway.backend.repository.CobrancaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CobrancaService {

    private static final Logger log = LoggerFactory.getLogger(CobrancaService.class);

    @Autowired
    private CobrancaRepository repository;

    @Autowired
    private PixProvider pixProvider;

    public Cobranca criarCobranca(Cobranca cobranca) {
    
        String chaveOriginal = cobranca.getChavePix();
        if (chaveOriginal != null && !chaveOriginal.contains("@")) {
            String chaveLimpa = chaveOriginal.replaceAll("[^0-9]", "");
            cobranca.setChavePix(chaveLimpa);
            log.info("Chave Pix sanitizada para o processamento: {}", chaveLimpa);
        }

        log.info("Gerando novo Pix no valor de R$ {} para a chave {}", cobranca.getValor(), cobranca.getChavePix());
        PixResponseDTO response = pixProvider.gerarCobranca(cobranca.getValor(), cobranca.getChavePix());

        cobranca.setTxid(response.getTxid());
        cobranca.setPixCopiaCola(response.getPixCopiaCola());
        cobranca.setStatus("PENDENTE");
        
        return repository.save(cobranca);
    }

    public Cobranca pagarCobranca(UUID id) {
        log.info("Simulando pagamento para a cobrança: {}", id);
        Cobranca cobranca = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cobrança não encontrada!"));

        cobranca.setStatus("PAGO");
        cobranca.setDataPagamento(OffsetDateTime.now());
        
        log.info("Pagamento confirmado com sucesso para o ID: {}", id);
        return repository.save(cobranca);
    }

    public List<Cobranca> listarTodas() {
        log.info("Buscando histórico de cobranças no PostgreSQL...");
        return repository.findAll();
    }
}