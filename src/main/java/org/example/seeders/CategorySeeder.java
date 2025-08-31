package org.example.seeders;

import lombok.Getter;
import net.datafaker.Faker;
import org.example.daos.CategoryDaoImpl;
import org.example.models.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CategorySeeder {

    private final Faker faker = new Faker(new Locale("fr"));
    @Getter
    private final List<Category> categories = new ArrayList<>();
    private final CategoryDaoImpl dao = new CategoryDaoImpl();

    public void init() {
        System.out.println("Seeding categories...");
        for (int i = 0; i < 10; i++) {
            Category category = Category.builder()
                    .name(faker.commerce().department())
                    .description(faker.lorem().sentence())
                    .build();
            dao.save(category);
            categories.add(category);
        }
    }
}
