package org.example.daos.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import org.example.daos.BrandDao;
import org.example.models.Brand;

import java.util.List;

public class JpaBrandDaoImpl implements BrandDao {

    private final EntityManager em;

    public JpaBrandDaoImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Brand> findAll() {
        return em.createQuery("SELECT b FROM Brand b", Brand.class)
                .getResultList();
    }

    @Override
    public Brand findById(Integer id) {
        return em.find(Brand.class, id);
    }

    @Override
    public void save(Brand brand) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(brand);
        tx.commit();
    }

    @Override
    public Brand update(Brand brand) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Brand merged = em.merge(brand);
        tx.commit();
        return merged;
    }

    @Override
    public void delete(Brand brand) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Brand managed = em.contains(brand) ? brand : em.merge(brand);
        em.remove(managed);
        tx.commit();
    }

    @Override
    public List<Brand> findByCountry(String country) {
        TypedQuery<Brand> q = em.createQuery(
                "SELECT b FROM Brand b WHERE LOWER(b.country) = LOWER(:country)",
                Brand.class
        );
        q.setParameter("country", country);
        return q.getResultList();
    }
}
