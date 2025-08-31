package org.example.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer clientId;

    @Column(nullable = false)
    private Integer productId;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private LocalDate date;
}
