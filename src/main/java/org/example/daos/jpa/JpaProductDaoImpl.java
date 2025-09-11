package org.example.daos.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.daos.ProductDao;
import org.example.models.Product;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Primary
@Transactional
public class JpaProductDaoImpl implements ProductDao {

    @PersistenceContext
    private EntityManager em;

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
        em.persist(product);
    }

    @Override
    public Product update(Product product) {
        return em.merge(product);
    }

    @Override
    public void delete(Product product) {
        em.remove(em.contains(product) ? product : em.merge(product));
    }


    @Override
    public List<Product> findInStockProducts() {
        return em.createQuery("SELECT p FROM Product p WHERE p.quantity > 0", Product.class)
                .getResultList();
    }

    @Override
    public List<Product> findByName(String keyword) {
        return em.createQuery("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(:kw)", Product.class)
                .setParameter("kw", "%" + keyword + "%")
                .getResultList();
    }
}
