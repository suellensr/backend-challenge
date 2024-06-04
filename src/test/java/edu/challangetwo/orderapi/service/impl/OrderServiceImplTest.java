package edu.challangetwo.orderapi.service.impl;

import edu.challangetwo.orderapi.api.dto.*;
import edu.challangetwo.orderapi.exception.ResourceAlreadyExistsException;
import edu.challangetwo.orderapi.exception.ResourceNotFoundException;
import edu.challangetwo.orderapi.model.Item;
import edu.challangetwo.orderapi.model.Order;
import edu.challangetwo.orderapi.model.OrderStatus;
import edu.challangetwo.orderapi.repository.ItemRepository;
import edu.challangetwo.orderapi.repository.OrderRepository;
import edu.challangetwo.orderapi.service.util.CheckOrderStatus;
import edu.challangetwo.orderapi.service.util.OrderMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    OrderRepository orderRepository;

    @Mock
    ItemRepository itemRepository;

    @Mock
    CheckOrderStatus checkOrderStatus;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    @DisplayName("Should create an order successfully")
    void createOrderSuccess() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setPedido("123456");
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setDescricao("Item A");
        itemDTO.setPrecoUnitario(BigDecimal.valueOf(10.00));
        itemDTO.setQtd(1);
        orderDTO.setItens(List.of(itemDTO));

        Order order = OrderMapper.orderDTOToOrder(orderDTO);
        order.setId(orderDTO.getPedido());
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        OrderDTO createdOrder = orderService.createOrder(orderDTO);

        assertThat(orderDTO.equals(createdOrder)).isTrue();
    }

    @Test
    @DisplayName("Should throw ResourceAlreadyExistsException when order already exists")
    void createOrderException() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setPedido("123456");
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setDescricao("Item A");
        itemDTO.setPrecoUnitario(BigDecimal.valueOf(10.00));
        itemDTO.setQtd(1);
        orderDTO.setItens(List.of(itemDTO));

        Order order = OrderMapper.orderDTOToOrder(orderDTO);
        order.setId(orderDTO.getPedido());

        when(orderRepository.findById("123456")).thenReturn(Optional.of(order));
        assertThatThrownBy(() -> orderService.createOrder(orderDTO))
                .isInstanceOf(ResourceAlreadyExistsException.class)
                .hasMessageContaining("Order with id 123456 already exists.");
    }

    @Test
    @DisplayName("Should update an order successfully")
    void updateOrderSuccess() {
        String orderId = "123456";
        Order order = new Order();
        order.setId(orderId);
        Item itemOder = new Item();
        itemOder.setDescription("Item A");
        itemOder.setUnitPrice(BigDecimal.valueOf(10.00));
        itemOder.setQuantity(2);
        order.setItems(List.of(itemOder));

        OrderUpdateDTO orderUpdateDTO = new OrderUpdateDTO();
        ItemDTO itemOrderUpdateDTO = new ItemDTO();
        itemOrderUpdateDTO.setDescricao("Item B");
        itemOrderUpdateDTO.setPrecoUnitario(BigDecimal.valueOf(20.00));
        itemOrderUpdateDTO.setQtd(1);
        orderUpdateDTO.setItens(List.of(itemOrderUpdateDTO));

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        order.setItems(List.of(OrderMapper.itemDTOtoItem(itemOrderUpdateDTO)));

        when(orderRepository.save(any(Order.class))).thenReturn(order);

        OrderDTO expectedOrderDTO = OrderMapper.orderToOrderDTO(order);
        OrderDTO updatedOrder = orderService.updateOrder(orderId, orderUpdateDTO);

        assertThat(expectedOrderDTO.equals(updatedOrder)).isTrue();
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when updating non-existing order")
    void updateOrderException() {
        String orderId = "123456";
        OrderUpdateDTO orderUpdateDTO = new OrderUpdateDTO();
        ItemDTO itemOrderUpdateDTO = new ItemDTO();
        itemOrderUpdateDTO.setDescricao("Item B");
        itemOrderUpdateDTO.setPrecoUnitario(BigDecimal.valueOf(20.00));
        itemOrderUpdateDTO.setQtd(1);
        orderUpdateDTO.setItens(List.of(itemOrderUpdateDTO));

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.updateOrder(orderId, orderUpdateDTO))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Order with id 123456 does not exists.");
    }

    @Test
    @DisplayName("Should get all orders successfully")
    void getAllOdersSuccess() {

        Order order1 = new Order();
        order1.setId("123456");
        Item itemAOder1 = new Item();
        itemAOder1.setDescription("Item A");
        itemAOder1.setUnitPrice(BigDecimal.valueOf(10.00));
        itemAOder1.setQuantity(2);
        order1.setItems(List.of(itemAOder1));

        Order order2 = new Order();
        order2.setId("123457");
        Item itemBOder2 = new Item();
        itemBOder2.setDescription("Item B");
        itemBOder2.setUnitPrice(BigDecimal.valueOf(20.00));
        itemBOder2.setQuantity(1);
        Item itemCOrder2 = new Item();
        itemCOrder2.setDescription("Item C");
        itemCOrder2.setUnitPrice(BigDecimal.valueOf(2.50));
        itemCOrder2.setQuantity(4);
        order2.setItems(List.of(itemBOder2, itemCOrder2));

        List<Order> allOrders = List.of(order1, order2);

        when(orderRepository.findAll()).thenReturn(allOrders);

        List<OrderDTO> allOrdersDTO = new ArrayList<>();
        for (Order order : allOrders) {
            allOrdersDTO.add(OrderMapper.orderToOrderDTO(order));
        }

        assertThat(orderService.getAllOrders()).isEqualTo(allOrdersDTO);
    }

    @Test
    @DisplayName("Should get order by ID successfully")
    void getOrderByIdSuccess() {
        String orderId = "123456";
        Order order = new Order();
        order.setId(orderId);
        Item itemOder = new Item();
        itemOder.setDescription("Item A");
        itemOder.setUnitPrice(BigDecimal.valueOf(10.00));
        itemOder.setQuantity(2);
        order.setItems(List.of(itemOder));

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        OrderDTO expectedOrderDTO = OrderMapper.orderToOrderDTO(order);

        assertThat(orderService.getOrderById(orderId)).isEqualTo(expectedOrderDTO);
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when order ID does not exist")
    void getOrderByIdException() {
        String orderId = "123456";
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.getOrderById(orderId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Order not found with id 123456");
    }

    @Test
    @DisplayName("Should delete order by ID successfully")
    void deleteOrderByIdSuccess() {
        String orderId = "123456";
        Order order = new Order();
        order.setId(orderId);
        Item itemOder = new Item();
        itemOder.setDescription("Item A");
        itemOder.setUnitPrice(BigDecimal.valueOf(10.00));
        itemOder.setQuantity(2);
        order.setItems(List.of(itemOder));

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        doNothing().when(orderRepository).deleteById(orderId);

        assertThat(orderService.deleteOrderById(orderId)).isEqualTo("Order with id " + orderId + " has been deleted successfully.");
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when deleting non-existing order")
    void deleteOrderByIdException() {
        String orderId = "123456";
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.deleteOrderById(orderId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Order with id 123456 does not exist.");
    }


    @Test
    @DisplayName("Should return CODIGO_PEDIDO_INVALIDO status when order does not exist")
    void updateStatusOrderNotFound() {
        String orderId = "123456";
        StatusRequestDTO statusRequestDTO = new StatusRequestDTO();
        statusRequestDTO.setPedido(orderId);
        statusRequestDTO.setStatus("APROVADO");
        statusRequestDTO.setValorAprovado(BigDecimal.valueOf(100.00));
        statusRequestDTO.setItensAprovados(1);

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        StatusResponseDTO statusResponseDTO = orderService.updateStatus(statusRequestDTO);

        assertThat(statusResponseDTO.getStatus()).isEqualTo(List.of(OrderStatus.CODIGO_PEDIDO_INVALIDO.toString()));
    }

    @Test
    @DisplayName("Should update status successfully")
    void updateStatusSuccess() {
        String orderId = "123456";
        StatusRequestDTO statusRequestDTO = new StatusRequestDTO();
        statusRequestDTO.setPedido(orderId);
        statusRequestDTO.setStatus("APROVADO");
        statusRequestDTO.setValorAprovado(BigDecimal.valueOf(100.00));
        statusRequestDTO.setItensAprovados(1);

        Order order = new Order();
        order.setId(orderId);
        Item item = new Item();
        item.setDescription("Item A");
        item.setUnitPrice(BigDecimal.valueOf(100.00));
        item.setQuantity(1);
        order.setItems(List.of(item));

        OrderDTO orderDTO = OrderMapper.orderToOrderDTO(order);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(checkOrderStatus.updateStatus(orderDTO, statusRequestDTO)).thenReturn(List.of(OrderStatus.APROVADO.toString()));

        StatusResponseDTO statusResponseDTO = orderService.updateStatus(statusRequestDTO);

        assertThat(statusResponseDTO.getStatus()).isEqualTo(List.of(OrderStatus.APROVADO.toString()));
    }


}