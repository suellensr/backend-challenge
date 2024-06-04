package edu.challangetwo.orderapi.api.controller;

import edu.challangetwo.orderapi.api.dto.StatusRequestDTO;
import edu.challangetwo.orderapi.api.dto.StatusResponseDTO;
import edu.challangetwo.orderapi.service.interfaces.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/status")
public class StatusController {

    private final OrderService orderService;

    @Autowired
    public StatusController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<StatusResponseDTO> updateOrderStatus(@RequestBody StatusRequestDTO statusRequestDTO) {
        StatusResponseDTO statusResponseDTO = orderService.updateStatus(statusRequestDTO);
        return ResponseEntity.ok(statusResponseDTO);
    }
}
