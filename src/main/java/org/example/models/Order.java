package org.example.models;

import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Builder.Default
    private int id = generateId();

    @NonNull
    private int clientId;

    @NonNull
    private LocalDate date;

    @NonNull
    private double total;

    @Builder.Default
    private List<Product> products = new java.util.ArrayList<>();

    private static int counter = 1;

    private static int generateId() {
        return counter++;
    }
}
