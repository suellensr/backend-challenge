package edu.challangetwo.orderapi.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "tb_item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_item")
    private Long id;

    @Column(nullable = false)
    private String description;

    @Column(name = "unit_price", nullable = false)
    private BigDecimal unitPrice;

    @Column(nullable = false)
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    public Item() {}

    public Item(String description, BigDecimal unitPrice, int quantity, Order order) {
        this.description = description;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.order = order;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
