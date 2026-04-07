package com.pixgateway.backend.domain.dto;

import java.math.BigDecimal;

public class PixResponseDTO {
    private String txid;
    private String status;
    private BigDecimal valor;
    private String pixCopiaCola;

    // --- GETTERS E SETTERS ---
    
    public String getTxid() { return txid; }
    public void setTxid(String txid) { this.txid = txid; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }

    public String getPixCopiaCola() { return pixCopiaCola; }
    public void setPixCopiaCola(String pixCopiaCola) { this.pixCopiaCola = pixCopiaCola; }
}