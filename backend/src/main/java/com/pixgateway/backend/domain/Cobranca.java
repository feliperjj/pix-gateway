package com.pixgateway.backend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "cobrancas")
@Data
public class Cobranca {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    @NotNull(message = "O valor é obrigatório")
    @Positive(message = "O valor deve ser maior que zero")
    private BigDecimal valor;

    @Column(nullable = false)
    private String status; // PENDENTE, PAGO, EXPIRADO

    @Column(nullable = false)
    @NotBlank(message = "A chave Pix é obrigatória")
    private String chavePix;

    @Column(columnDefinition = "TEXT")
    private String qrCode;

    private String txid; 

    @Column(columnDefinition = "TEXT")
    private String pixCopiaCola; 

    private OffsetDateTime dataCriacao = OffsetDateTime.now();
    
    private OffsetDateTime dataPagamento;
}