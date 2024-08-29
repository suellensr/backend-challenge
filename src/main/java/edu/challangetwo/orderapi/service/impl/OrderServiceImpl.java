package edu.challangetwo.orderapi.service.impl;

import edu.challangetwo.orderapi.api.dto.ItemDTO;
import edu.challangetwo.orderapi.api.dto.OrderDTO;
import edu.challangetwo.orderapi.api.dto.OrderUpdateDTO;
import edu.challangetwo.orderapi.api.dto.StatusRequestDTO;
import edu.challangetwo.orderapi.api.dto.StatusResponseDTO;
import edu.challangetwo.orderapi.exception.InvalidPriceOrQuantityException;
import edu.challangetwo.orderapi.exception.ResourceAlreadyExistsException;
import edu.challangetwo.orderapi.exception.ResourceNotFoundException;
import edu.challangetwo.orderapi.model.Item;
import edu.challangetwo.orderapi.model.Order;
import edu.challangetwo.orderapi.model.OrderStatus;
import edu.challangetwo.orderapi.repository.ItemRepository;
import edu.challangetwo.orderapi.repository.OrderRepository;
import edu.challangetwo.orderapi.service.interfaces.OrderService;
import edu.challangetwo.orderapi.service.util.CheckOrderStatus;
import edu.challangetwo.orderapi.service.util.OrderMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final CheckOrderStatus checkOrderStatus;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ItemRepository itemRepository, CheckOrderStatus checkOrderStatus) {
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
        this.checkOrderStatus = checkOrderStatus;
    }

    @Override
    @Transactional
    public OrderDTO createOrder(OrderDTO orderDTO) {
        String orderDTOId = orderDTO.getPedido();
        if (orderExists(orderDTOId))
            throw new ResourceAlreadyExistsException("Order with id " + orderDTOId + " already exists.");

        Order order = OrderMapper.orderDTOToOrder(orderDTO);
        Order savedOrder = orderRepository.save(order);
        createItem(order);

        return OrderMapper.orderToOrderDTO(savedOrder);
    }

    @Override
    @Transactional
    public OrderDTO updateOrder(String orderId, OrderUpdateDTO orderUpdateDTO) {
        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order with id " + orderId + " does not exists."));

        itemRepository.deleteByOrder(existingOrder);

        List<Item> newItems = new ArrayList<>();
        for (ItemDTO itemDTO : orderUpdateDTO.getItens()) {
            newItems.add(OrderMapper.itemDTOtoItem(itemDTO));
        }

        existingOrder.setItems(newItems);
        createItem(existingOrder);
        Order updatedOrder = orderRepository.save(existingOrder);

        return OrderMapper.orderToOrderDTO(updatedOrder);
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        List<OrderDTO> ordersDTO = new ArrayList<>();
        for (Order order : orders) {
            ordersDTO.add(OrderMapper.orderToOrderDTO(order));
        }
        return ordersDTO;
    }

    @Override
    public OrderDTO getOrderById(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id " + orderId));

        return OrderMapper.orderToOrderDTO(order);
    }

    @Override
    public String deleteOrderById(String orderId) {
        if (!orderExists(orderId))
            throw new ResourceNotFoundException("Order with id " + orderId + " does not exist.");

        orderRepository.deleteById(orderId);
        return "Order with id "  + orderId + " has been deleted successfully.";
    }

    @Override
    public StatusResponseDTO updateStatus(StatusRequestDTO statusRequestDTO) {
        String orderId = statusRequestDTO.getPedido();
        List<String> statusList = new ArrayList<>();
        if (!orderExists(orderId))
            statusList.add(OrderStatus.CODIGO_PEDIDO_INVALIDO.toString());
        else {
            OrderDTO orderDTO = getOrderById(orderId);
            statusList = checkOrderStatus.updateStatus(orderDTO, statusRequestDTO);
        }

        return new StatusResponseDTO(orderId, statusList);
    }

    private boolean orderExists(String orderId) {
        return orderRepository.findById(orderId).isPresent();
    }

    private void createItem(Order order) {
        order.getItems().forEach(item -> {
            item.setOrder(order); // Ensure bidirectional relationship
            validateItems(item);
        });
    }

    private void validateItems(Item item) {
        if (item.getUnitPrice().compareTo(BigDecimal.ZERO) <= 0 || item.getQuantity() <= 0)
            throw new InvalidPriceOrQuantityException("Invalid price or quantity for item: " + item.getDescription());
    }
}