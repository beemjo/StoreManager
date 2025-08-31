package org.example.services;

import org.example.daos.CategoryDao;
import org.example.exceptions.CategoryNotFoundException;
import org.example.models.Category;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {

    private final CategoryDao categoryDao;

    public CategoryServiceImpl(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Override
    public List<Category> findAll() {
        return categoryDao.findAll();
    }

    @Override
    public Category findById(Integer id) {
        Category category = categoryDao.findById(id);
        if (category == null) {
            throw new CategoryNotFoundException("Category with id " + id + " not found.");
        }
        return category;
    }

    @Override
    public void save(Category category) {
        categoryDao.save(category);
    }

    @Override
    public Category update(Category category) {
        if (categoryDao.findById(category.getId()) == null) {
            throw new CategoryNotFoundException("Cannot update. Category with id " + category.getId() + " not found.");
        }
        return categoryDao.update(category);
    }

    @Override
    public void delete(Category category) {
        if (categoryDao.findById(category.getId()) == null) {
            throw new CategoryNotFoundException("Cannot delete. Category with id " + category.getId() + " not found.");
        }
        categoryDao.delete(category);
    }

    @Override
    public List<Category> findByName(String keyword) {
        return categoryDao.findByName(keyword);
    }
}
