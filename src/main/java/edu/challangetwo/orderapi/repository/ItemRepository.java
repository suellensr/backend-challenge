package edu.challangetwo.orderapi.repository;

import edu.challangetwo.orderapi.model.Item;
import edu.challangetwo.orderapi.model.Order;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Item i WHERE i.order = :order")
    void deleteByOrder(@Param("order") Order order);
}
