package edu.challangetwo.orderapi.api.dto;

import java.util.List;

public class OrderUpdateDTO {

    private List<ItemDTO> itens;

    public OrderUpdateDTO() {}

    public List<ItemDTO> getItens() {
        return itens;
    }
}
