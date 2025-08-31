package org.example.daos;

import org.example.models.Order;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl implements OrderDao {

    private final Connection connection;

    public OrderDaoImpl() {
        connection = DatabaseConnection.getInstance().getConnection();
        createTable();
    }

    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS orders (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "clientId INT, " +
                "productId INT, " +
                "quantity INT, " +
                "date DATE" +
                ")";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("✅ Table 'orders' is ready.");
        } catch (SQLException e) {
            System.err.println("❌ Error creating table: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void save(Order order) {
        String sql = "INSERT INTO orders (clientId, productId, quantity, date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, order.getClientId());
            ps.setInt(2, order.getProductId());
            ps.setInt(3, order.getQuantity());
            ps.setDate(4, Date.valueOf(order.getDate()));
            ps.executeUpdate();

            // Retrieve generated ID
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    order.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error saving order for client " + order.getClientId(), e);
        }
    }

    @Override
    public Order update(Order order) {
        String sql = "UPDATE orders SET clientId=?, productId=?, quantity=?, date=? WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, order.getClientId());
            ps.setInt(2, order.getProductId());
            ps.setInt(3, order.getQuantity());
            ps.setDate(4, Date.valueOf(order.getDate()));
            ps.setInt(5, order.getId());
            int rows = ps.executeUpdate();
            if (rows > 0) return order;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating order " + order.getId(), e);
        }
        return null;
    }

    @Override
    public void delete(Order order) {
        String sql = "DELETE FROM orders WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, order.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting order " + order.getId(), e);
        }
    }

    @Override
    public Order findById(Integer id) {
        String sql = "SELECT * FROM orders WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapOrder(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding order with id " + id, e);
        }
        return null;
    }

    @Override
    public List<Order> findAll() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                orders.add(mapOrder(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching all orders", e);
        }
        return orders;
    }

    @Override
    public List<Order> findByClientId(int clientId) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE clientId=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, clientId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                orders.add(mapOrder(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding orders for client " + clientId, e);
        }
        return orders;
    }

    @Override
    public List<Order> findByDateRange(LocalDate start, LocalDate end) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE date BETWEEN ? AND ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(start));
            ps.setDate(2, Date.valueOf(end));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                orders.add(mapOrder(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding orders between " + start + " and " + end, e);
        }
        return orders;
    }

    // helper method to map ResultSet to Order
    private Order mapOrder(ResultSet rs) throws SQLException {
        return Order.builder()
                .id(rs.getInt("id"))
                .clientId(rs.getInt("clientId"))
                .productId(rs.getInt("productId"))
                .quantity(rs.getInt("quantity"))
                .date(rs.getDate("date").toLocalDate())
                .build();
    }
}
