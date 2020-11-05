package edu.challangetwo.orderapi.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")

@Entity
@Table(name = "tb_order")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order")
    private Long id;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<Item> items = new ArrayList<>();

    @ElementCollection(targetClass = OrderStatus.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "order_status", joinColumns = @JoinColumn(name = "order_id"))
    @Column(name = "status", nullable = true)
    private List<OrderStatus> status = new ArrayList<>();
}
