package org.example.mappers;

import org.example.dtos.ProductDto;
import org.example.dtos.ProductRequestDto;
import org.example.models.Product;

public interface ProductMapper {
    Product toEntity(ProductDto dto);
    Product toEntityRequest(ProductRequestDto dto);

    ProductDto toDto(Product entity);

}
