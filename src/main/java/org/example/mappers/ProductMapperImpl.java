package org.example.mappers;

import org.example.dtos.ProductDto;
import org.example.dtos.ProductRequestDto;
import org.example.models.Product;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public Product toEntity(ProductDto dto) {
        if (dto == null) return null;
        return Product.builder()
                .name(dto.getName())
                .sku(dto.getSku())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .quantity(dto.getQuantity())
                .build();
    }

    @Override
    public Product toEntityRequest(ProductRequestDto dto) {
        if (dto == null) return null;
        return Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .quantity(dto.getQuantity())
                .sku("PROD-" + UUID.randomUUID().toString().substring(0, 8)) // génération ici
                .build();
    }


    @Override
    public ProductDto toDto(Product entity) {
        if (entity == null) return null;
        return ProductDto.builder()
                .name(entity.getName())
                .sku(entity.getSku())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .quantity(entity.getQuantity())
                .build();
    }

}
