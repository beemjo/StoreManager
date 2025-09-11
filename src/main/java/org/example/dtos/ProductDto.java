package org.example.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder


public class ProductDto {

    private String name;

    private String sku;

    private String description;

    private double price;

    private int quantity;
}
