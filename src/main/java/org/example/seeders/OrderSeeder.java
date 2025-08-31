package org.example.seeders;

import lombok.Getter;
import net.datafaker.Faker;
import org.example.daos.OrderDaoImpl;
import org.example.models.Order;
import org.example.models.Product;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class OrderSeeder {

    private final Faker faker = new Faker(new Locale("fr"));

    @Getter
    private final List<Order> orders = new ArrayList<>();
    private final OrderDaoImpl dao = new OrderDaoImpl();

    public void init(List<Product> availableProducts) {
        for (int i = 0; i < 20; i++) {


            Product randomProduct = availableProducts.get(faker.number().numberBetween(0, availableProducts.size() - 1));
            int quantity = faker.number().numberBetween(1, 5);


            LocalDate randomDate = LocalDate.of(
                    faker.number().numberBetween(2023, 2025),
                    faker.number().numberBetween(1, 12),
                    faker.number().numberBetween(1, 28)
            );

            // creeer l ordre
            Order order = Order.builder()
                    .clientId(faker.number().numberBetween(1, 30)) // random client id
                    .productId(randomProduct.getId())
                    .quantity(quantity)
                    .date(randomDate)
                    .build();


            dao.save(order);
            orders.add(order);
        }
    }
}
