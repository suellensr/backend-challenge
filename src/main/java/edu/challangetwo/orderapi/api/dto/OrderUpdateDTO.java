package edu.challangetwo.orderapi.api.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class OrderUpdateDTO {

    @NotNull
    @NotEmpty
    private List<ItemDTO> itens;

    public OrderUpdateDTO() {}

    public List<ItemDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemDTO> itens) {
        this.itens = itens;
    }
}
