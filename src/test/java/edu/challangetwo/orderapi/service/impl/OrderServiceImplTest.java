package edu.challangetwo.orderapi.service.impl;

import edu.challangetwo.orderapi.api.dto.*;
import edu.challangetwo.orderapi.exception.ResourceAlreadyExistsException;
import edu.challangetwo.orderapi.exception.ResourceNotFoundException;
import edu.challangetwo.orderapi.model.Item;
import edu.challangetwo.orderapi.model.Order;
import edu.challangetwo.orderapi.model.OrderStatus;
import edu.challangetwo.orderapi.repository.ItemRepository;
import edu.challangetwo.orderapi.repository.OrderRepository;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    @DisplayName("Should create an order successfully")
    public void createOrderSuccess() {

        ItemDTO item1DTO = getDefaultItemDTO("Item A", BigDecimal.valueOf(10.00), 1);
        ItemDTO item2DTO = getDefaultItemDTO("Item B", BigDecimal.valueOf(5.00), 2);
        String orderDTOId = "123456";
        OrderDTO orderDTO = getDefaultOrderDTO(orderDTOId, item1DTO, item2DTO);

        Order order = OrderMapper.orderDTOToOrder(orderDTO);
        order.setId(orderDTO.getPedido());
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        OrderDTO createdOrder = orderService.createOrder(orderDTO);

        assertThat(orderDTO.equals(createdOrder)).isTrue();
    }

    @Test
    @DisplayName("Should throw ResourceAlreadyExistsException when order already exists")
    public void createOrderException() {
        ItemDTO item1DTO = getDefaultItemDTO("Item A", BigDecimal.valueOf(10.00), 1);
        ItemDTO item2DTO = getDefaultItemDTO("Item B", BigDecimal.valueOf(5.00), 2);
        String orderDTOId = "123456";
        OrderDTO orderDTO = getDefaultOrderDTO(orderDTOId, item1DTO, item2DTO);

        Order order = OrderMapper.orderDTOToOrder(orderDTO);
        order.setId(orderDTO.getPedido());

        when(orderRepository.findById(orderDTOId)).thenReturn(Optional.of(order));
        assertThatThrownBy(() -> orderService.createOrder(orderDTO))
                .isInstanceOf(ResourceAlreadyExistsException.class)
                .hasMessageContaining("Order with id " + orderDTOId + " already exists.");
    }

    @Test
    @DisplayName("Should update an order successfully")
    public void updateOrderSuccess() {
        Item item1 = getDefaultItem("Item A", BigDecimal.valueOf(10.00), 1);
        Item item2 = getDefaultItem("Item B", BigDecimal.valueOf(5.00), 2);
        String orderId = "123456";
        Order order = getDefaultOrder(orderId, item1, item2);

        ItemDTO itemUpdateDTO1 = getDefaultItemDTO("Item A", BigDecimal.valueOf(15.00), 4);
        OrderUpdateDTO orderUpdateDTO =getDefaultOrderUpdateDTO(itemUpdateDTO1);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        order.setItems(List.of(OrderMapper.itemDTOtoItem(itemUpdateDTO1)));

        when(orderRepository.save(any(Order.class))).thenReturn(order);

        OrderDTO expectedOrderDTO = OrderMapper.orderToOrderDTO(order);
        OrderDTO updatedOrder = orderService.updateOrder(orderId, orderUpdateDTO);

        assertThat(expectedOrderDTO.equals(updatedOrder)).isTrue();
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when updating non-existing order")
    public void updateOrderException() {
        String orderId = "123456";
        ItemDTO itemOrderUpdateDTO = getDefaultItemDTO("Item A", BigDecimal.valueOf(15.00), 4);
        OrderUpdateDTO orderUpdateDTO = getDefaultOrderUpdateDTO(itemOrderUpdateDTO);

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.updateOrder(orderId, orderUpdateDTO))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Order with id " + orderId + " does not exists.");
    }

    @Test
    @DisplayName("Should get all orders successfully")
    public void getAllOdersSuccess() {
        Item item1 = getDefaultItem("Item A", BigDecimal.valueOf(10.00), 1);
        Item item2 = getDefaultItem("Item B", BigDecimal.valueOf(5.00), 2);
        String orderId1 = "123456";
        Order order1 = getDefaultOrder(orderId1, item1, item2);

        Item item3 = getDefaultItem("Item C", BigDecimal.valueOf(15.00), 1);
        Item item4 = getDefaultItem("Item D", BigDecimal.valueOf(2.50), 2);
        String orderId2 = "123457";
        Order order2 = getDefaultOrder(orderId2, item3, item4);

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
    public void getOrderByIdSuccess() {
        Item item1 = getDefaultItem("Item A", BigDecimal.valueOf(10.00), 1);
        Item item2 = getDefaultItem("Item B", BigDecimal.valueOf(5.00), 2);
        String orderId = "123456";
        Order order = getDefaultOrder(orderId, item1, item2);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        OrderDTO expectedOrderDTO = OrderMapper.orderToOrderDTO(order);

        assertThat(orderService.getOrderById(orderId)).isEqualTo(expectedOrderDTO);
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when order ID does not exist")
    public void getOrderByIdException() {
        String orderId = "123456";
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.getOrderById(orderId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Order not found with id " + orderId);
    }

    @Test
    @DisplayName("Should delete order by ID successfully")
    public void deleteOrderByIdSuccess() {
        Item item1 = getDefaultItem("Item A", BigDecimal.valueOf(10.00), 1);
        Item item2 = getDefaultItem("Item B", BigDecimal.valueOf(5.00), 2);
        String orderId = "123456";
        Order order = getDefaultOrder(orderId, item1, item2);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        doNothing().when(orderRepository).deleteById(orderId);

        assertThat(orderService.deleteOrderById(orderId)).isEqualTo("Order with id " + orderId + " has been deleted successfully.");
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when deleting non-existing order")
    public void deleteOrderByIdException() {
        String orderId = "123456";
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.deleteOrderById(orderId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Order with id " + orderId +" does not exist.");
    }


    @Test
    @DisplayName("Should return CODIGO_PEDIDO_INVALIDO status when order does not exist")
    public void updateStatusOrderNotFound() {
        String orderId = "123456";
        StatusRequestDTO statusRequestDTO = getDefaultStatusRequestDTO(orderId, "APROVADO", BigDecimal.valueOf(100.00), 1);

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        StatusResponseDTO statusResponseDTO = orderService.updateStatus(statusRequestDTO);

        assertThat(statusResponseDTO.getStatus()).isEqualTo(List.of(OrderStatus.CODIGO_PEDIDO_INVALIDO.toString()));
    }

    @Test
    @DisplayName("Should return REPROVADO status")
    public void updateStatusOrderReproved() {
        String orderId = "123456";
        StatusRequestDTO statusRequestDTO = getDefaultStatusRequestDTO(orderId, "REPROVADO", BigDecimal.valueOf(0), 0);

        Item item1 = getDefaultItem("Item A", BigDecimal.valueOf(10.00), 1);
        Item item2 = getDefaultItem("Item B", BigDecimal.valueOf(5.00), 2);
        Order order = getDefaultOrder(orderId, item1, item2);

        OrderDTO orderDTO = OrderMapper.orderToOrderDTO(order);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        StatusResponseDTO statusResponseDTO = orderService.updateStatus(statusRequestDTO);

        assertThat(statusResponseDTO.getStatus()).isEqualTo(List.of(OrderStatus.REPROVADO.toString()));
    }

    @Test
    @DisplayName("Should return APROVADO_VALOR_A_MAIOR status")
    public void updateStatusOrderValueHigher() {
        String orderId = "123456";
        StatusRequestDTO statusRequestDTO = getDefaultStatusRequestDTO(orderId, "APROVADO", BigDecimal.valueOf(100), 3);

        Item item1 = getDefaultItem("Item A", BigDecimal.valueOf(10.00), 1);
        Item item2 = getDefaultItem("Item B", BigDecimal.valueOf(5.00), 2);
        Order order = getDefaultOrder(orderId, item1, item2);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        StatusResponseDTO statusResponseDTO = orderService.updateStatus(statusRequestDTO);

        assertThat(statusResponseDTO.getStatus()).isEqualTo(List.of(OrderStatus.APROVADO_VALOR_A_MAIOR.toString()));
    }

    @Test
    @DisplayName("Should return APROVADO_QTD_A_MAIOR status")
    public void updateStatusOrderQuantityHigher() {
        String orderId = "123456";
        StatusRequestDTO statusRequestDTO = getDefaultStatusRequestDTO(orderId, "APROVADO", BigDecimal.valueOf(20), 5);

        Item item1 = getDefaultItem("Item A", BigDecimal.valueOf(10.00), 1);
        Item item2 = getDefaultItem("Item B", BigDecimal.valueOf(5.00), 2);
        Order order = getDefaultOrder(orderId, item1, item2);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        StatusResponseDTO statusResponseDTO = orderService.updateStatus(statusRequestDTO);

        assertThat(statusResponseDTO.getStatus()).isEqualTo(List.of(OrderStatus.APROVADO_QTD_A_MAIOR.toString()));
    }

    @Test
    @DisplayName("Should return APROVADO_VALOR_A_MENOR status")
    public void updateStatusOrderValueLower() {
        String orderId = "123456";
        StatusRequestDTO statusRequestDTO = getDefaultStatusRequestDTO(orderId, "APROVADO", BigDecimal.valueOf(15.00), 3);

        Item item1 = getDefaultItem("Item A", BigDecimal.valueOf(10.00), 1);
        Item item2 = getDefaultItem("Item B", BigDecimal.valueOf(5.00), 2);
        Order order = getDefaultOrder(orderId, item1, item2);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        StatusResponseDTO statusResponseDTO = orderService.updateStatus(statusRequestDTO);

        assertThat(statusResponseDTO.getStatus()).isEqualTo(List.of(OrderStatus.APROVADO_VALOR_A_MENOR.toString()));
    }

    @Test
    @DisplayName("Should return APROVADO_QTD_A_MENOR status")
    public void updateStatusOrderQuantityLower() {
        String orderId = "123456";
        StatusRequestDTO statusRequestDTO = getDefaultStatusRequestDTO(orderId, "APROVADO", BigDecimal.valueOf(20), 2);

        Item item1 = getDefaultItem("Item A", BigDecimal.valueOf(10.00), 1);
        Item item2 = getDefaultItem("Item B", BigDecimal.valueOf(5.00), 2);
        Order order = getDefaultOrder(orderId, item1, item2);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        StatusResponseDTO statusResponseDTO = orderService.updateStatus(statusRequestDTO);

        assertThat(statusResponseDTO.getStatus()).isEqualTo(List.of(OrderStatus.APROVADO_QTD_A_MENOR.toString()));
    }

    @Test
    @DisplayName("Should return APROVADO status")
    public void updateStatusOrderApproved() {
        String orderId = "123456";
        StatusRequestDTO statusRequestDTO = getDefaultStatusRequestDTO(orderId, "APROVADO", BigDecimal.valueOf(20), 3);

        Item item1 = getDefaultItem("Item A", BigDecimal.valueOf(10.00), 1);
        Item item2 = getDefaultItem("Item B", BigDecimal.valueOf(5.00), 2);
        Order order = getDefaultOrder(orderId, item1, item2);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        StatusResponseDTO statusResponseDTO = orderService.updateStatus(statusRequestDTO);

        assertThat(statusResponseDTO.getStatus()).isEqualTo(List.of(OrderStatus.APROVADO.toString()));
    }

    private Item getDefaultItem(String description, BigDecimal unitPrice, int quantity) {
        Item item = new Item();
        item.setDescription(description);
        item.setUnitPrice(unitPrice);
        item.setQuantity(quantity);

        return item;
    }

    private ItemDTO getDefaultItemDTO(String descricao, BigDecimal PrecoUnitario, int qtd) {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setDescricao(descricao);
        itemDTO.setPrecoUnitario(PrecoUnitario);
        itemDTO.setQtd(qtd);

        return itemDTO;
    }

    private Order getDefaultOrder(String orderId, Item item1, Item item2) {
        Order order = new Order();
        order.setId(orderId);
        order.setItems(List.of(item1, item2));

        return order;
    }

    private OrderDTO getDefaultOrderDTO(String orderId, ItemDTO itemDTO1, ItemDTO itemDTO2) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setPedido(orderId);
        orderDTO.setItens(List.of(itemDTO1, itemDTO2));

        return orderDTO;
    }

    private OrderUpdateDTO getDefaultOrderUpdateDTO(ItemDTO itemDTOUpdate1) {
        OrderUpdateDTO orderUpdateDTO = new OrderUpdateDTO();
        orderUpdateDTO.setItens(List.of(itemDTOUpdate1));

        return orderUpdateDTO;
    }

    private StatusRequestDTO getDefaultStatusRequestDTO (String orderId, String status, BigDecimal valorAprovado, int itensAprovados) {
        StatusRequestDTO statusRequestDTO = new StatusRequestDTO();
        statusRequestDTO.setPedido(orderId);
        statusRequestDTO.setStatus(status);
        statusRequestDTO.setValorAprovado(valorAprovado);
        statusRequestDTO.setItensAprovados(itensAprovados);

        return statusRequestDTO;
    }
}