package org.example.daos.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.example.daos.ClientDao;
import org.example.models.Client;

import java.util.List;

public class JpaClientDaoImpl implements ClientDao {

    private final EntityManager em;

    public JpaClientDaoImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Client> findAll() {
        return em.createQuery("SELECT c FROM Client c", Client.class)
                .getResultList();
    }

    @Override
    public Client findById(Integer id) {
        return em.find(Client.class, id);
    }

    @Override
    public void save(Client client) {
        em.getTransaction().begin();
        em.persist(client);
        em.getTransaction().commit();
    }

    @Override
    public Client update(Client client) {
        em.getTransaction().begin();
        Client c = em.merge(client);
        em.getTransaction().commit();
        return c;
    }

    @Override
    public void delete(Client client) {
        em.getTransaction().begin();
        em.remove(em.contains(client) ? client : em.merge(client));
        em.getTransaction().commit();
    }


    @Override
    public List<Client> findByName(String name) {
        return em.createQuery(
                        "SELECT c FROM Client c WHERE LOWER(c.name) LIKE LOWER(:name)", Client.class
                ).setParameter("name", "%" + name + "%")
                .getResultList();
    }
}
