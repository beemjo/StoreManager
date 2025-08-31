package org.example.services;

import org.example.daos.OrderDao;
import org.example.exceptions.OrderNotFoundException;
import org.example.models.Order;

import java.time.LocalDate;
import java.util.List;

public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;

    public OrderServiceImpl(OrderDao orderDao, ClientService clientService, ProductService productService) {
        this.orderDao = orderDao;
    }

    @Override
    public List<Order> findAll() {
        return orderDao.findAll();
    }

    @Override
    public Order findById(Integer id) {
        Order order = orderDao.findById(id);
        if (order == null) {
            throw new OrderNotFoundException("Order with ID " + id + " not found");
        }
        return order;
    }

    @Override
    public void save(Order order) {
        orderDao.save(order);
    }

    @Override
    public Order update(Order order) {
        if (orderDao.findById(order.getId()) == null) {
            throw new OrderNotFoundException("Cannot update. Order with ID " + order.getId() + " not found");
        }
        return orderDao.update(order);
    }

    @Override
    public void delete(Order order) {
        if (orderDao.findById(order.getId()) == null) {
            throw new OrderNotFoundException("Cannot delete. Order with ID " + order.getId() + " not found");
        }
        orderDao.delete(order);
    }

    @Override
    public List<Order> findByClientId(int clientId) {
        return orderDao.findByClientId(clientId);
    }

    @Override
    public List<Order> findByDateRange(LocalDate start, LocalDate end) {
        return orderDao.findByDateRange(start, end);
    }
}
