package org.example;

import org.example.daos.DatabaseConnection;
import org.example.seeders.ClientSeeder;
import org.example.seeders.OrderSeeder;
import org.example.seeders.ProductSeeder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        // connetion to db
        DatabaseConnection.getInstance();
        log.info("✅ Connected to MySQL database!");

        // seeding products
        ProductSeeder productSeeder = new ProductSeeder();
        productSeeder.init();
        log.info("✅ Products seeded: {}", productSeeder.getProducts().size());

        // seeding clients
        ClientSeeder clientSeeder = new ClientSeeder();
        clientSeeder.init();
        log.info("✅ Clients seeded: {}", clientSeeder.getClients().size());

        // seeding orders
        OrderSeeder orderSeeder = new OrderSeeder();
        orderSeeder.init(clientSeeder.getClients(), productSeeder.getProducts());
        log.info("✅ Orders seeded: {}", orderSeeder.getOrders().size());

        log.info("✅ Database seeding complete!");
    }
}
