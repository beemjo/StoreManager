package org.example.daos;

import org.example.models.Product;

import java.util.List;

public interface ProductDao extends CrudDao<Product,Integer> {
    List<Product> findInStockProducts();
    List<Product> findByName(String keyword);
}
