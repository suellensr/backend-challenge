package edu.challangetwo.orderapi.api.controller;


import edu.challangetwo.orderapi.api.dto.OrderDTO;
import edu.challangetwo.orderapi.api.dto.OrderUpdateDTO;
import edu.challangetwo.orderapi.service.interfaces.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedido")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) {
        OrderDTO createdOrder = orderService.createOrder(orderDTO);
        return ResponseEntity.ok(createdOrder);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable String orderId, @RequestBody OrderUpdateDTO orderUpdateDTO) {
        OrderDTO updatedOrder = orderService.updateOrder(orderId, orderUpdateDTO);
        return ResponseEntity.ok(updatedOrder);
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<OrderDTO> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable String orderId) {
        OrderDTO order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(order);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable String orderId) {
        String message = orderService.deleteOrderById(orderId);
        return ResponseEntity.ok(message);
    }
}
