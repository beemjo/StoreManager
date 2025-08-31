package org.example.services;

import org.example.models.Product;

import java.util.List;

public interface ProductService extends CrudService<Product, Integer> {
    List<Product> findInStockProducts();
    List<Product> findByName(String keyword);
}
