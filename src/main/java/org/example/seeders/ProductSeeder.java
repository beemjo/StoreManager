package org.example.seeders;

import lombok.Getter;
import net.datafaker.Faker;
import org.example.daos.ProductDaoImpl;
import org.example.models.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
//class pr inserer et generer les produits
public class ProductSeeder {

    private final Faker faker = new Faker(new Locale("fr"));
    @Getter
    private final List<Product> products = new ArrayList<>();
    private final ProductDaoImpl dao = new ProductDaoImpl();

    public void init() { // methode pour initialiser les produits
        for (int i = 0; i < 30; i++) {
            Product product = Product.builder()
                    .name(faker.commerce().productName())
                    .description(faker.lorem().sentence())
                    .price(faker.number().numberBetween(50, 5000))
                    .quantity(faker.number().numberBetween(10, 100))
                    .build();
            products.add(product);
            dao.save(product);// save le produit dans la base

        }
    }

}
