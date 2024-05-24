package edu.challangetwo.orderapi.api.dto;

import java.util.List;

public class OrderDTO {

    private String pedido;
    private List<ItemDTO> itens;

    public OrderDTO() {}

    public String getPedido() {
        return pedido;
    }

    public void setPedido(String pedido) {
        this.pedido = pedido;
    }

    public List<ItemDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemDTO> itens) {
        this.itens = itens;
    }
}