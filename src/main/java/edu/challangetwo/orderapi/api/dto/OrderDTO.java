package edu.challangetwo.orderapi.api.dto;

import org.antlr.v4.runtime.misc.NotNull;

import java.util.List;
import java.util.Objects;

public class OrderDTO {

    @NotNull
    private String pedido;

    @NotNull
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDTO orderDTO = (OrderDTO) o;
        return  Objects.equals(pedido, orderDTO.pedido) &&
                itemsEqual(itens, orderDTO.itens);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pedido, itens);
    }

    private boolean itemsEqual(List<ItemDTO> expectedList, List<ItemDTO> definedList) {
        if (expectedList.size() != definedList.size()) return false;
        for (int i = 0; i < expectedList.size(); i++) {
            String expectedDescription = expectedList.get(i).getDescricao();
            ItemDTO foundedItem = definedList.stream().filter(x->x.getDescricao().equalsIgnoreCase(expectedDescription))
                    .findFirst().orElse(null);

            if (foundedItem == null) return false;
            if (!foundedItem.equals(expectedList.get(i))) return false;
        }
        return true;
    }
}