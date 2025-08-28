package org.example.daos;

import org.example.models.Client;
import java.util.List;

public interface ClientDao extends CrudDao<Client,Integer> {
    List<Client> findByName(String name);
}
