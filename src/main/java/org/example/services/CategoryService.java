package org.example.services;

import org.example.models.Category;

import java.util.List;

public interface CategoryService extends CrudService<Category, Integer> {
    List<Category> findByName(String keyword);
}
