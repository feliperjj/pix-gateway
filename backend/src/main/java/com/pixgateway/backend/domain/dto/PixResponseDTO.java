package com.pixgateway.backend.domain.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class PixResponseDTO {
    private String txid;
    private String status;
    private BigDecimal valor;
    private String pixCopiaCola;
}