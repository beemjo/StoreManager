package org.example.mappers;

import org.example.dtos.ClientDto;
import org.example.models.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientMapperImpl implements ClientMapper {

    @Override
    public ClientDto toDto(Client client) {
        if (client == null) return null;
        return ClientDto.builder()
                .name(client.getName())
                .email(client.getEmail())
                .phone(client.getPhone())
                .build();
    }

    @Override
    public Client toEntity(ClientDto dto) {
        if (dto == null) return null;
        return Client.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .build();
    }
}
