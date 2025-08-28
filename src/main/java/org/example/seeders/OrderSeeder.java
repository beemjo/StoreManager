package org.example.seeders;

import lombok.Getter;
import net.datafaker.Faker;
import org.example.daos.ClientDaoImpl;
import org.example.daos.OrderDaoImpl;
import org.example.models.Client;
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
    public void init(List<Client> clients, List<Product> availableProducts) {

        for (int i = 0; i < 20; i++) {
            List<Product> productsInOrder = new ArrayList<>();
            int numberOfProducts = faker.number().numberBetween(1, 4);

            for (int j = 0; j < numberOfProducts; j++) {// boucle pr selec les produits
                int index = faker.number().numberBetween(0, availableProducts.size() - 1);// random index
                productsInOrder.add(availableProducts.get(index));// ajouter le prod selec a l order avec l index

            }

            double total = 0;
            for (Product p : productsInOrder) {// boucle for every product in the order
                total += p.getPrice();
            }

            LocalDate randomDate = LocalDate.of(//random date
                    faker.number().numberBetween(2023, 2025),
                    faker.number().numberBetween(1, 12), // careful: months must be 1–12
                    faker.number().numberBetween(1, 28)  // safer: 1–28 for all months
            );

            Order order = Order.builder()
                    .clientId(faker.number().numberBetween(1, 30))
                    .date(randomDate)
                    .total(total)
                    .products(productsInOrder)
                    .build();// make the order
            dao.save(order);
            orders.add(order);

        }
    }

}
