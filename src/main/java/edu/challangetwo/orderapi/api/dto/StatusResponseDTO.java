package edu.challangetwo.orderapi.api.dto;

import edu.challangetwo.orderapi.model.OrderStatus;

import java.util.List;

public class StatusResponseDTO {

    private String pedido;
    private List<String> status;

    public StatusResponseDTO(String pedido, List<String> status) {
        this.pedido = pedido;
        this.status = status;
    }

    public String getPedido() {
        return pedido;
    }

    public void setPedido(String pedido) {
        this.pedido = pedido;
    }

    public List<String> getStatus() {
        return status;
    }

    public void setStatus(List<String> status) {
        this.status = status;
    }
}
