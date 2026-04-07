package com.pixgateway.backend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "cobrancas")
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

    // --- GETTERS E SETTERS MANUAIS ---

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getChavePix() { return chavePix; }
    public void setChavePix(String chavePix) { this.chavePix = chavePix; }

    public String getQrCode() { return qrCode; }
    public void setQrCode(String qrCode) { this.qrCode = qrCode; }

    public String getTxid() { return txid; }
    public void setTxid(String txid) { this.txid = txid; }

    public String getPixCopiaCola() { return pixCopiaCola; }
    public void setPixCopiaCola(String pixCopiaCola) { this.pixCopiaCola = pixCopiaCola; }

    public OffsetDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(OffsetDateTime dataCriacao) { this.dataCriacao = dataCriacao; }

    public OffsetDateTime getDataPagamento() { return dataPagamento; }
    public void setDataPagamento(OffsetDateTime dataPagamento) { this.dataPagamento = dataPagamento; }
}