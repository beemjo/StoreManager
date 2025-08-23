package org.example.models;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Builder.Default
    private int id = generateId();

    @NonNull
    private String name;

    @NonNull
    private String description;

    @NonNull
    private double price;

    @NonNull
    private int quantity;


    private static int counter = 1;

    private static int generateId() {
        return counter++;
    }
}
