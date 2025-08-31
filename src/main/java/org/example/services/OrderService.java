package org.example.services;

import org.example.models.Order;

import java.time.LocalDate;
import java.util.List;

public interface OrderService extends CrudService<Order,Integer>{
    List<Order> findByClientId(int clientId);
    List<Order> findByDateRange(LocalDate start, LocalDate end);
}
