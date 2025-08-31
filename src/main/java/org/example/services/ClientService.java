package org.example.services;

import org.example.models.Client;

import java.util.List;

public interface ClientService extends CrudService<Client,Integer> {
    List<Client> findByName(String name);
}
