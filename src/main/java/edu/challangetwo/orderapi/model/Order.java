package edu.challangetwo.orderapi.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tb_order")
public class Order {

    @Id
    @Column(name = "id_order")
    private String id;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Item> items;

    public Order() {}

    public Order(String id, List<Item> items) {
        this.id = id;
        this.items = items;
        this.items.forEach(item -> item.setOrder(this)); // Ensuring bidirectional relationship
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
        this.items.forEach(item -> item.setOrder(this)); // Ensuring bidirectional relationship
    }
}
