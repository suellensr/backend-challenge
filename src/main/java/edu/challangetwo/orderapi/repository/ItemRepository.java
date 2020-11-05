package edu.challangetwo.orderapi.repository;

import edu.challangetwo.orderapi.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
