package com.pixgateway.backend.infrastructure.pix;

import com.pixgateway.backend.domain.dto.PixResponseDTO;
import java.math.BigDecimal;

public interface PixProvider {
    PixResponseDTO gerarCobranca(BigDecimal valor, String chave);
}