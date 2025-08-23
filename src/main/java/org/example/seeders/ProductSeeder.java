package org.example.seeders;

import net.datafaker.Faker;
import org.example.models.Product;
import org.example.constants.DatabasePaths;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProductSeeder {

    private final Faker faker = new Faker(new Locale("fr"));
    private final List<Product> products = new ArrayList<>();

    public void init() {

        for (int i = 0; i < 30; i++) {
            Product product = Product.builder()
                    .name(faker.commerce().productName())
                    .description(faker.lorem().sentence())
                    .price(faker.number().numberBetween(50, 5000))
                    .quantity(faker.number().numberBetween(10, 100))
                    .build();
            products.add(product);
        }


        saveToFile();
    }

    private void saveToFile() {
        try (FileWriter writer = new FileWriter(DatabasePaths.PRODUCT_FILE, false)) {
            for (Product p : products) {
                writer.write(p.toString() + "\n");
            }
            System.out.println("💾 " + products.size() + " products saved in " + DatabasePaths.PRODUCT_FILE);
        } catch (IOException ex) {
            System.out.println("❌ cannot save the products: " + ex.getMessage());
        }
    }

    public List<Product> getProducts() {
        return products;
    }
}
