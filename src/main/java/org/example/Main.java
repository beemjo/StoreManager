package org.example;

import net.datafaker.Faker;
import org.example.daos.ClientDaoImpl;
import org.example.daos.OrderDaoImpl;
import org.example.daos.ProductDaoImpl;
import org.example.models.Client;
import org.example.models.Order;
import org.example.models.Product;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

public class Main {

    public static void main(String[] args) {

        Faker faker = new Faker(new Locale("fr"));

        ProductDaoImpl productDao = new ProductDaoImpl();
        ClientDaoImpl clientDao = new ClientDaoImpl();
        OrderDaoImpl orderDao = new OrderDaoImpl();

        // seed products
        System.out.println("Seeding products...");
        for (int i = 0; i < 5; i++) {
            Product p = Product.builder()
                    .name(faker.commerce().productName())
                    .description(faker.lorem().sentence())
                    .price(faker.number().randomDouble(2, 10, 500))
                    .quantity(faker.number().numberBetween(1, 20))
                    .build();
            productDao.save(p);
        }

        // seed client
        System.out.println("Seeding clients...");
        for (int i = 0; i < 5; i++) {
            Client c = Client.builder()
                    .name(faker.name().fullName())
                    .email(faker.internet().emailAddress())
                    .phone(faker.phoneNumber().cellPhone())
                    .build();
            clientDao.save(c);
        }

        // fetching ids des clients et des produits
        List<Product> products = productDao.findAll();
        List<Client> clients = clientDao.findAll();

        // crrer des orders
        System.out.println("\nSeeding orders...");
        for (int i = 0; i < 5; i++) {
            Client client = clients.get(faker.number().numberBetween(0, clients.size()));
            Product product = products.get(faker.number().numberBetween(0, products.size()));

            Order o = Order.builder()
                    .clientId(client.getId())
                    .productId(product.getId())
                    .quantity(faker.number().numberBetween(1, 5))
                    .date(LocalDate.now().minusDays(faker.number().numberBetween(0, 30)))
                    .build();

            orderDao.save(o);
        }

        // fetching
        System.out.println("\nOrders in DB with quantity > 2:");
        orderDao.findAll().stream()
                .filter(o -> o.getQuantity() > 2)
                .forEach(o -> System.out.println(
                        "Order ID: " + o.getId() +
                                " | ClientID: " + o.getClientId() +
                                " | ProductID: " + o.getProductId() +
                                " | Quantity: " + o.getQuantity() +
                                " | Date: " + o.getDate()
                ));
    }
}
