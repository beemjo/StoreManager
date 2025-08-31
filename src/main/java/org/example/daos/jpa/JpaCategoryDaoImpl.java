package org.example.daos.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.example.daos.CategoryDao;
import org.example.models.Category;

import java.util.List;

public class JpaCategoryDaoImpl implements CategoryDao {

    private final EntityManager em;

    public JpaCategoryDaoImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Category> findAll() {
        return em.createQuery("SELECT c FROM Category c", Category.class).getResultList();
    }

    @Override
    public Category findById(Integer id) {
        return em.find(Category.class, id);
    }

    @Override
    public void save(Category category) {
        em.getTransaction().begin();
        em.persist(category);
        em.getTransaction().commit();
    }

    @Override
    public Category update(Category category) {
        em.getTransaction().begin();
        Category c = em.merge(category);
        em.getTransaction().commit();
        return c;
    }

    @Override
    public void delete(Category category) {
        em.getTransaction().begin();
        em.remove(em.contains(category) ? category : em.merge(category));
        em.getTransaction().commit();
    }

    @Override
    public List<Category> findByName(String keyword) {
        TypedQuery<Category> query = em.createQuery(
                "SELECT c FROM Category c WHERE LOWER(c.name) LIKE LOWER(:kw)", Category.class
        );
        query.setParameter("kw", "%" + keyword + "%");
        return query.getResultList();
    }
}
