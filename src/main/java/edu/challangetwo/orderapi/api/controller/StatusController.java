package edu.challangetwo.orderapi.api.controller;

import edu.challangetwo.orderapi.api.dto.StatusRequestDTO;
import edu.challangetwo.orderapi.api.dto.StatusResponseDTO;
import edu.challangetwo.orderapi.service.interfaces.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/status")
public class StatusController {

    private final OrderService orderService;

    @Autowired
    public StatusController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/update/{orderId}")
    public ResponseEntity<StatusResponseDTO> updateOrderStatus(@PathVariable String orderId, @RequestBody StatusRequestDTO statusRequestDTO) {
        StatusResponseDTO statusResponseDTO = orderService.updateStatus(statusRequestDTO);
        return ResponseEntity.ok(statusResponseDTO);
    }

}
