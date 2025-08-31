package org.example;

import net.datafaker.Faker;
import org.example.daos.ClientDaoImpl;
import org.example.daos.OrderDaoImpl;
import org.example.daos.ProductDaoImpl;
import org.example.daos.BrandDaoImpl;
import org.example.daos.CategoryDaoImpl;
import org.example.models.Client;
import org.example.models.Order;
import org.example.models.Product;
import org.example.models.Brand;
import org.example.models.Category;
import org.example.seeders.CategorySeeder;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

public class Main {

    public static void main(String[] args) {

        Faker faker = new Faker(new Locale("fr"));

        // DAOs
        ProductDaoImpl productDao = new ProductDaoImpl();
        ClientDaoImpl clientDao = new ClientDaoImpl();
        OrderDaoImpl orderDao = new OrderDaoImpl();
        BrandDaoImpl brandDao = new BrandDaoImpl();
        CategoryDaoImpl categoryDao = new CategoryDaoImpl();


        // SEED PRODUCTS
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


        // SEED CLIENTS
        System.out.println("Seeding clients...");
        for (int i = 0; i < 5; i++) {
            Client c = Client.builder()
                    .name(faker.name().fullName())
                    .email(faker.internet().emailAddress())
                    .phone(faker.phoneNumber().cellPhone())
                    .build();
            clientDao.save(c);
        }


        // SEED BRANDS
        System.out.println("\nSeeding brands...");
        for (int i = 0; i < 5; i++) {
            String country = (i % 2 == 0) ? "France" : faker.options().option("USA", "Germany", "Japan");
            Brand b = Brand.builder()
                    .name(faker.company().name())
                    .country(country)
                    .build();
            brandDao.save(b);
        }


        // SEED CATEGORIES
        CategorySeeder categorySeeder = new CategorySeeder();
        categorySeeder.init();


        // FETCH PRODUCTS ND CLIENTS for orders
        List<Product> products = productDao.findAll();
        List<Client> clients = clientDao.findAll();


        // SEED ORDERS
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


        // FILTER ORDERS
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


        // FILTER BRANDS from France
        System.out.println("\nBrands in DB from 'France':");
        brandDao.findAll().stream()
                .filter(b -> "France".equalsIgnoreCase(b.getCountry()))
                .forEach(b -> System.out.println(
                        "Brand ID: " + b.getId() +
                                " | Name: " + b.getName() +
                                " | Country: " + b.getCountry()
                ));


        // FILTER CATEGORIES WITH THE WORD HOME
        System.out.println("\nCategories containing 'home':");
        categoryDao.findAll().stream()
                .filter(c -> c.getName().toLowerCase().contains("home"))
                .forEach(c -> System.out.println(
                        "Category ID: " + c.getId() +
                                " | Name: " + c.getName() +
                                " | Description: " + c.getDescription()
                ));
    }
}
