package edu.challangetwo.orderapi.repository;

import edu.challangetwo.orderapi.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
