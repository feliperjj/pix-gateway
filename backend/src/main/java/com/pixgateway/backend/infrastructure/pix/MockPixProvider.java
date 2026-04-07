package com.pixgateway.backend.infrastructure.pix;

import com.pixgateway.backend.domain.dto.PixResponseDTO;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.UUID;

@Service
public class MockPixProvider implements PixProvider {

    @Override
    public PixResponseDTO gerarCobranca(BigDecimal valor, String chave) {
        try { Thread.sleep(800); } catch (InterruptedException e) {}

        PixResponseDTO mock = new PixResponseDTO();
        mock.setTxid(UUID.randomUUID().toString().replace("-", "").substring(0, 20));
        mock.setStatus("ATIVA");
        mock.setValor(valor);
        mock.setPixCopiaCola("00020101021226580014br.gov.bcb.pix0114" + chave + "520400005303986");
        
        return mock;
    }
}