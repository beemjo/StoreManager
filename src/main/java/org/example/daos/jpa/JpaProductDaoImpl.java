package org.example.daos.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.example.daos.ProductDao;
import org.example.models.Product;

import java.util.List;

public class JpaProductDaoImpl implements ProductDao {

    private final EntityManager em;

    public JpaProductDaoImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Product> findAll() {
        return em.createQuery("SELECT p FROM Product p", Product.class)
                .getResultList();
    }

    @Override
    public Product findById(Integer id) {
        return em.find(Product.class, id);
    }

    @Override
    public void save(Product product) {
        em.getTransaction().begin();
        em.persist(product);
        em.getTransaction().commit();
    }

    @Override
    public Product update(Product product) {
        em.getTransaction().begin();
        Product p = em.merge(product);
        em.getTransaction().commit();
        return p;
    }

    @Override
    public void delete(Product product) {
        em.getTransaction().begin();
        em.remove(em.contains(product) ? product : em.merge(product));
        em.getTransaction().commit();
    }


    @Override
    public List<Product> findInStockProducts() {
        return em.createQuery(
                "SELECT p FROM Product p WHERE p.quantity > 0", Product.class
        ).getResultList();
    }

    @Override
    public List<Product> findByName(String keyword) {
        return em.createQuery(
                        "SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(:kw)", Product.class
                )
                .setParameter("kw", "%" + keyword + "%")
                .getResultList();
    }
}
