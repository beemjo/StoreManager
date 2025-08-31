package org.example.services;

import org.example.daos.ProductDao;
import org.example.exceptions.ProductNotFoundException;
import org.example.models.Product;

import java.util.List;

public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao;

    public ProductServiceImpl(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public List<Product> findAll() {
        return productDao.findAll();
    }

    @Override
    public Product findById(Integer id) {
        Product product = productDao.findById(id);
        if (product == null) {
            throw new ProductNotFoundException("Product with id " + id + " not found.");
        }
        return product;
    }

    @Override
    public void save(Product product) {
        productDao.save(product);
    }

    @Override
    public Product update(Product product) {
        if (productDao.findById(product.getId()) == null) {
            throw new ProductNotFoundException("Cannot update. Product with id " + product.getId() + " not found.");
        }
        return productDao.update(product);
    }

    @Override
    public void delete(Product product) {
        if (productDao.findById(product.getId()) == null) {
            throw new ProductNotFoundException("Cannot delete. Product with id " + product.getId() + " not found.");
        }
        productDao.delete(product);
    }

    @Override
    public List<Product> findInStockProducts() {
        return productDao.findInStockProducts();
    }

    @Override
    public List<Product> findByName(String keyword) {
        return productDao.findByName(keyword);
    }
}
