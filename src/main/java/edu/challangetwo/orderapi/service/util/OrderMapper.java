package edu.challangetwo.orderapi.service.util;

import edu.challangetwo.orderapi.api.dto.ItemDTO;
import edu.challangetwo.orderapi.api.dto.OrderDTO;
import edu.challangetwo.orderapi.api.dto.OrderUpdateDTO;
import edu.challangetwo.orderapi.model.Item;
import edu.challangetwo.orderapi.model.Order;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public static Order orderDTOToOrder(OrderDTO orderDTO) {
        Order order = new Order();
        order.setId(orderDTO.getPedido());
        order.setItems(orderDTO.getItens().stream()
                .map(itemDTO -> {
                    Item item = itemDTOtoItem(itemDTO);
                    item.setOrder(order); // Set the order reference
                    return item;
                })
                .collect(Collectors.toList()));
        return order;
    }

    public static OrderDTO orderToOrderDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setPedido(order.getId());
        orderDTO.setItens(order.getItems().stream()
                .map(OrderMapper::itemToItemDTO)
                .collect(Collectors.toList()));
        return orderDTO;
    }

    public static Order orderUpdateDTOToOrder(String pedido, OrderUpdateDTO orderUpdateDTO) {
        Order order = new Order();
        order.setId(pedido);
        order.setItems(orderUpdateDTO.getItens().stream()
                .map(itemDTO -> {
                    Item item = itemDTOtoItem(itemDTO);
                    item.setOrder(order); // Set the order reference
                    return item;
                })
                .collect(Collectors.toList()));
        return order;
    }

//    public static OrderUpdateDTO orderToOrderUpdateDTO(Order order) {
//        OrderUpdateDTO orderUpdateDTO = new OrderUpdateDTO();
//        orderUpdateDTO.setItens(order.getItems().stream()
//                .map(OrderMapper::itemToItemDTO)
//                .collect(Collectors.toList()));
//        return orderUpdateDTO;
//    }

    public static Item itemDTOtoItem(ItemDTO itemDTO) {
        Item item = new Item();
        item.setDescription(itemDTO.getDescricao());
        item.setUnitPrice(itemDTO.getPrecoUnitario());
        item.setQuantity(itemDTO.getQtd());
        return item;
    }

    public static ItemDTO itemToItemDTO(Item item) {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setDescricao(item.getDescription());
        itemDTO.setPrecoUnitario(item.getUnitPrice());
        itemDTO.setQtd(item.getQuantity());
        return itemDTO;
    }
}
