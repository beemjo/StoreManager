package org.example.daos;

import org.example.models.Category;
import java.util.List;

public interface CategoryDao extends CrudDao<Category, Integer> {
    List<Category> findByName(String keyword);
}
