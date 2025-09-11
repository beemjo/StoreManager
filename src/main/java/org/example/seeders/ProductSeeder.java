package org.example.seeders;

import lombok.Getter;
import net.datafaker.Faker;
import org.example.dtos.ProductRequestDto;
import org.example.mappers.ProductMapper;
import org.example.models.Product;
import org.example.services.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class ProductSeeder implements CommandLineRunner {

    private final Faker faker = new Faker(new Locale("fr"));

    @Getter
    private final List<Product> products = new ArrayList<>();

    private final ProductService productService;
    private final ProductMapper productMapper;

    public ProductSeeder(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Seeding products...");

        for (int i = 0; i < 30; i++) {
            ProductRequestDto dto = ProductRequestDto.builder()
                    .name(faker.commerce().productName())
                    .description(faker.lorem().sentence())
                    .price(faker.number().numberBetween(50, 5000))
                    .quantity(faker.number().numberBetween(10, 100))
                    .build();

            Product product = productMapper.toEntityRequest(dto); // Mapper generates SKU
            productService.save(product); // save avec JPA service

            products.add(product);
        }

        System.out.println("=== Products in DB ===");
        for (Product p : products) {
            System.out.printf("ID: %d, Name: %s, SKU: %s, Price: %.2f, Quantity: %d%n",
                    p.getId(), p.getName(), p.getSku(), p.getPrice(), p.getQuantity());
        }
    }
}
