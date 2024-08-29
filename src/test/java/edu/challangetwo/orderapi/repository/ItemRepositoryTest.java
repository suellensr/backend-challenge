package edu.challangetwo.orderapi.repository;

import edu.challangetwo.orderapi.model.Item;
import edu.challangetwo.orderapi.model.Order;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@Nested
@DataJpaTest
@ActiveProfiles("test")
public class ItemRepositoryTest {

    @Autowired
    public ItemRepository itemRepository;

    @Autowired
    public OrderRepository orderRepository;

    @Autowired
    public EntityManager entityManager;

    @Test
    @DisplayName("Should delete an Item by Order number successfully from DB")
    public void deleteByOrder() {
        Order order = new Order();
        order.setId("123456");
        entityManager.persist(order);

        createItem(order);

        //Check if items were saved
        assertThat(itemRepository.findAll()).isNotEmpty();
        assertThat(itemRepository.findAll()).hasSize(2);

        itemRepository.deleteByOrder(order);

        assertThat(itemRepository.findAll()).isEmpty();
    }

    private void createItem(Order order) {
        Item itemA = new Item();
        itemA.setDescription("Item A");
        itemA.setUnitPrice(BigDecimal.valueOf(10.00));
        itemA.setQuantity(1);
        itemA.setOrder(order);
        entityManager.persist(itemA);


        Item itemB = new Item();
        itemB.setDescription("Item B");
        itemB.setUnitPrice(BigDecimal.valueOf(10.00));
        itemB.setQuantity(1);
        itemB.setOrder(order);
        entityManager.persist(itemB);
    }
}