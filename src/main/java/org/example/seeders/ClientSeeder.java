package org.example.seeders;

import lombok.Getter;
import net.datafaker.Faker;
import org.example.daos.ClientDaoImpl;
import org.example.models.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;



public class ClientSeeder {

    private final Faker faker = new Faker(new Locale("fr"));
    @Getter
    private final List<Client> clients = new ArrayList<>();
    private final ClientDaoImpl dao = new ClientDaoImpl();

    public void init() {
        for (int i = 0; i < 20; i++) {
            Client client = Client.builder()
                    .name(faker.name().fullName())
                    .email(faker.internet().emailAddress())
                    .phone(faker.phoneNumber().phoneNumber())

                    .build();
            dao.save(client);
            clients.add(client);
        }

        }
    }


