package org.example.services;

import java.util.List;

public interface CrudService<M, ID> {
    List<M> findAll();
    M findById(ID id);
    void save(M m);
    M update(M m);
    void delete(M m);
}
