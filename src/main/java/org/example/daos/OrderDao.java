package org.example.daos;

import org.example.models.Order;

import java.time.LocalDate;
import java.util.List;

public interface OrderDao extends CrudDao<Order,Integer> {

    List<Order> findByClientId(int clientId);
    List<Order> findByDateRange(LocalDate start, LocalDate end);

}