package edu.challangetwo.orderapi.service.interfaces;

import edu.challangetwo.orderapi.api.dto.OrderDTO;
import edu.challangetwo.orderapi.api.dto.OrderUpdateDTO;
import edu.challangetwo.orderapi.api.dto.StatusRequestDTO;
import edu.challangetwo.orderapi.api.dto.StatusResponseDTO;
import edu.challangetwo.orderapi.exception.ResourceAlreadyExistsException;
import edu.challangetwo.orderapi.exception.ResourceNotFoundException;

import java.util.List;

public interface OrderService {

    OrderDTO createOrder(OrderDTO orderDTO) throws ResourceAlreadyExistsException;
    OrderDTO updateOrder(String orderId, OrderUpdateDTO orderUpdateDTO) throws ResourceNotFoundException;
    List<OrderDTO> getAllOrders();
    OrderDTO getOrderById(String orderId) throws ResourceNotFoundException;
    void deleteOrderById(String orderId);
    StatusResponseDTO updateStatus(StatusRequestDTO statusRequestDTO);
}