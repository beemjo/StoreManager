package org.example.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientDto {
    private String name;
    private String email;
    private String phone;
}
