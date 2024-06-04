package edu.challangetwo.orderapi.api.dto;

import org.antlr.v4.runtime.misc.NotNull;

import java.util.List;

public class OrderUpdateDTO {

    @NotNull
    private List<ItemDTO> itens;

    public OrderUpdateDTO() {}

    public List<ItemDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemDTO> itens) {
        this.itens = itens;
    }
}
