package org.example.services;

import org.example.daos.ClientDao;
import org.example.exceptions.ClientNotFoundException;
import org.example.models.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientDao clientDao;

    @Autowired
    public ClientServiceImpl(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    @Override
    public List<Client> findAll() {
        return clientDao.findAll();
    }

    @Override
    public Client findById(Integer id) {
        Client client = clientDao.findById(id);
        if (client == null) {
            throw new ClientNotFoundException("Client with ID " + id + " not found");
        }
        return client;
    }

    @Override
    public void save(Client client) {
        clientDao.save(client);
    }

    @Override
    public Client update(Client client) {
        if (clientDao.findById(client.getId()) == null) {
            throw new ClientNotFoundException("Cannot update. Client with ID " + client.getId() + " not found");
        }
        return clientDao.update(client);
    }

    @Override
    public void delete(Client client) {
        if (clientDao.findById(client.getId()) == null) {
            throw new ClientNotFoundException("Cannot delete. Client with ID " + client.getId() + " not found");
        }
        clientDao.delete(client);
    }

    @Override
    public List<Client> findByName(String name) {
        return clientDao.findByName(name);
    }
}
