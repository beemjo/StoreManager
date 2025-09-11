package org.example.daos.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.daos.ClientDao;
import org.example.models.Client;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class JpaClientDaoImpl implements ClientDao {

    @PersistenceContext
    private EntityManager em;

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
        em.persist(client);
    }

    @Override
    public Client update(Client client) {
        return em.merge(client);
    }

    @Override
    public void delete(Client client) {
        em.remove(em.contains(client) ? client : em.merge(client));
    }

    @Override
    public List<Client> findByName(String name) {
        return em.createQuery(
                        "SELECT c FROM Client c WHERE LOWER(c.name) LIKE LOWER(:name)", Client.class
                )
                .setParameter("name", "%" + name + "%")
                .getResultList();
    }
}
