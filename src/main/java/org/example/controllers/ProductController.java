package org.example.controllers;

import org.example.dtos.ProductDto;
import org.example.dtos.ProductRequestDto;
import org.example.mappers.ProductMapper;
import org.example.models.Product;
import org.example.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @Autowired
    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @GetMapping
    public List<ProductDto> getAllProducts() {
        return productService.findAll()
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable Integer id) {
        Product product = productService.findById(id);
        return productMapper.toDto(product);
    }

    @PostMapping
    public ProductDto createProduct(@RequestBody ProductRequestDto requestDto) {
        Product product = productMapper.toEntityRequest(requestDto);
        productService.save(product);
        return productMapper.toDto(product);
    }

    @PutMapping("/{id}")
    public ProductDto updateProduct(@PathVariable Integer id, @RequestBody ProductRequestDto requestDto) {
        Product existingProduct = productService.findById(id);

        existingProduct.setName(requestDto.getName());
        existingProduct.setDescription(requestDto.getDescription());
        existingProduct.setPrice(requestDto.getPrice());
        existingProduct.setQuantity(requestDto.getQuantity());

        Product updatedProduct = productService.update(existingProduct);
        return productMapper.toDto(updatedProduct);
    }

    @GetMapping("/in-stock")
    public List<ProductDto> getInStockProducts() {
        return productService.findInStockProducts()
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/search")
    public List<ProductDto> searchProducts(@RequestParam String keyword) {
        return productService.findByName(keyword)
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }
}