package org.example.seeders;

import lombok.Getter;
import net.datafaker.Faker;
import org.example.daos.BrandDaoImpl;
import org.example.models.Brand;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BrandSeeder {

    private final Faker faker = new Faker(new Locale("fr"));
    @Getter
    private final List<Brand> brands = new ArrayList<>();
    private final BrandDaoImpl dao = new BrandDaoImpl();

    public void init() {
        for (int i = 0; i < 20; i++) {
            Brand brand = Brand.builder()
                    .name(faker.company().name())
                    .description(faker.company().industry())
                    .country(faker.address().country())
                    .build();

            dao.save(brand);
            brands.add(brand);
        }
    }
}
