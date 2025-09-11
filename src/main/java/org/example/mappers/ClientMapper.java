package org.example.mappers;

import org.example.dtos.ClientDto;
import org.example.models.Client;

public interface ClientMapper {
    Client toEntity(ClientDto dto);
    ClientDto toDto(Client entity);
}
