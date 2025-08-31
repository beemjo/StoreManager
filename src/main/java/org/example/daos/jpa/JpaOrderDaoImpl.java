package org.example.daos.jpa;

import jakarta.persistence.EntityManager;
import org.example.daos.OrderDao;
import org.example.models.Order;

import java.time.LocalDate;
import java.util.List;

public class JpaOrderDaoImpl implements OrderDao {

    private final EntityManager em;

    public JpaOrderDaoImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Order> findAll() {
        return em.createQuery("SELECT o FROM Order o", Order.class)
                .getResultList();
    }

    @Override
    public Order findById(Integer id) {
        return em.find(Order.class, id);
    }

    @Override
    public void save(Order order) {
        em.getTransaction().begin();
        em.persist(order);
        em.getTransaction().commit();
    }

    @Override
    public Order update(Order order) {
        em.getTransaction().begin();
        Order o = em.merge(order);
        em.getTransaction().commit();
        return o;
    }

    @Override
    public void delete(Order order) {
        em.getTransaction().begin();
        em.remove(em.contains(order) ? order : em.merge(order));
        em.getTransaction().commit();
    }


    @Override
    public List<Order> findByClientId(int clientId) {
        return em.createQuery(
                        "SELECT o FROM Order o WHERE o.client.id = :clientId", Order.class
                ).setParameter("clientId", clientId)
                .getResultList();
    }

    @Override
    public List<Order> findByDateRange(LocalDate start, LocalDate end) {
        return em.createQuery(
                        "SELECT o FROM Order o WHERE o.date BETWEEN :start AND :end", Order.class
                ).setParameter("start", start)
                .setParameter("end", end)
                .getResultList();
    }
}
