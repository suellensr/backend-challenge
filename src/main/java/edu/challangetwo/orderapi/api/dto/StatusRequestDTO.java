package edu.challangetwo.orderapi.api.dto;

import edu.challangetwo.orderapi.model.OrderStatus;

import java.math.BigDecimal;
import java.util.List;

public class StatusRequestDTO {

    private String status;
    private int itensAprovados;
    private BigDecimal valorAprovado;
    private String pedido;

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
