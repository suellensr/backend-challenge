package edu.challangetwo.orderapi.api.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.math.BigDecimal;

public class StatusRequestDTO {

    @NotBlank
    private String status;

    @NotNull
    private int itensAprovados;

    @NotNull
    private BigDecimal valorAprovado;

    @NotBlank
    private String pedido;

    public StatusRequestDTO() {}

    public StatusRequestDTO(String status, int itensAprovados, BigDecimal valorAprovado, String pedido) {
        this.status = status;
        this.itensAprovados = itensAprovados;
        this.valorAprovado = valorAprovado;
        this.pedido = pedido;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getItensAprovados() {
        return itensAprovados;
    }

    public void setItensAprovados(int itensAprovados) {
        this.itensAprovados = itensAprovados;
    }

    public BigDecimal getValorAprovado() {
        return valorAprovado;
    }

    public void setValorAprovado(BigDecimal valorAprovado) {
        this.valorAprovado = valorAprovado;
    }

    public String getPedido() {
        return pedido;
    }

    public void setPedido(String pedido) {
        this.pedido = pedido;
    }
}
