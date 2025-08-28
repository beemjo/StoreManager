package org.example.daos;

import org.example.models.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ClientDaoImpl implements ClientDao {
    private final Connection connection;

    public ClientDaoImpl() {
        connection = DatabaseConnection.getInstance().getConnection();
        createTable();
    }

    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS clients (" +
                "id INT PRIMARY KEY , " +
                "name VARCHAR(100) , " +
                "email VARCHAR(100) , " +
                "phone VARCHAR(20) " +
                ")";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("✅ Table 'clients' is ready.");
        } catch (SQLException e) {
            System.err.println("❌ Error creating table: " + e.getMessage());
            e.printStackTrace();
        }
    }


    @Override
    public List<Client> findByName(String name) {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM clients WHERE name LIKE ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + name + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                clients.add(Client.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .email(rs.getString("email"))
                        .phone(rs.getString("phone"))
                        .build());
            }
        } catch (SQLException e) {
            throw new RuntimeException("❌ Error finding clients by name", e);
        }
        return clients;
    }

    @Override
    public List<Client> findAll() {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM clients";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                clients.add(Client.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .email(rs.getString("email"))
                        .phone(rs.getString("phone"))
                        .build());
            }
        } catch (SQLException e) {
            throw new RuntimeException("❌ Error fetching all clients", e);
        }
        return clients;
    }

    @Override
    public Client findById(Integer id) {
        String sql = "SELECT * FROM clients WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Client.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .email(rs.getString("email"))
                        .phone(rs.getString("phone"))
                        .build();
            }
        } catch (SQLException e) {
            throw new RuntimeException("❌ Error finding client by id", e);
        }
        return null;
    }

    @Override
    public void save(Client client) {
        String sql = "INSERT INTO clients (name, email, phone) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, client.getName());
            ps.setString(2, client.getEmail());
            ps.setString(3, client.getPhone());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("❌ Error saving client " + client.getName(), e);
        }
    }


    @Override
    public Client update(Client client) {
        String sql = "UPDATE clients SET name = ?, email = ?, phone = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, client.getName());
            ps.setString(2, client.getEmail());
            ps.setString(3, client.getPhone());
            ps.setInt(4, client.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("❌ Error updating client " + client.getId(), e);
        }
        return client;
    }

    @Override
    public void delete(Client client) {
        String sql = "DELETE FROM clients WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, client.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("❌ Error deleting client " + client.getId(), e);
        }
    }
}