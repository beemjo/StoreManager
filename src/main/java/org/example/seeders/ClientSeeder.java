package org.example.seeders;

import lombok.Getter;
import net.datafaker.Faker;
import org.example.dtos.ClientDto;
import org.example.mappers.ClientMapper;
import org.example.models.Client;
import org.example.services.ClientService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class ClientSeeder implements CommandLineRunner {

    private final Faker faker = new Faker(new Locale("fr"));

    @Getter
    private final List<Client> clients = new ArrayList<>();

    private final ClientService clientService;
    private final ClientMapper clientMapper;

    public ClientSeeder(ClientService clientService, ClientMapper clientMapper) {
        this.clientService = clientService;
        this.clientMapper = clientMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        for (int i = 0; i < 20; i++) {
            ClientDto dto = ClientDto.builder()
                    .name(faker.name().fullName())
                    .email(faker.internet().emailAddress())
                    .phone(faker.phoneNumber().phoneNumber())
                    .build();

            Client client = clientMapper.toEntity(dto);
            clientService.save(client);
            clients.add(client);

            System.out.println("Client: " + client.getName() + ", Email: " + client.getEmail());
        }
    }
}
