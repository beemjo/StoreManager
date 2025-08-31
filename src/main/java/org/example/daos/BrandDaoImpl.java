package org.example.daos;

import org.example.models.Brand;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BrandDaoImpl implements BrandDao {
    private final Connection connection;

    public BrandDaoImpl() {
        connection = DatabaseConnection.getInstance().getConnection();
        createTable();
    }

    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS brands (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(100) NOT NULL, " +
                "country VARCHAR(100) NOT NULL" +
                ")";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("✅ Table 'brands' ready.");
        } catch (SQLException e) {
            System.err.println("❌ Error creating table: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public List<Brand> findByCountry(String country) {
        List<Brand> brands = new ArrayList<>();
        String sql = "SELECT * FROM brands WHERE country = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, country);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                brands.add(Brand.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .country(rs.getString("country"))
                        .build());
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching brands from " + country, e);
        }
        return brands;
    }

    @Override
    public List<Brand> findAll() {
        List<Brand> brands = new ArrayList<>();
        String sql = "SELECT * FROM brands";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                brands.add(Brand.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .country(rs.getString("country"))
                        .build());
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching all brands", e);
        }
        return brands;
    }

    @Override
    public Brand findById(Integer id) {
        String sql = "SELECT * FROM brands WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Brand.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .country(rs.getString("country"))
                        .build();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding brand with id " + id, e);
        }
        return null;
    }

    @Override
    public void save(Brand brand) {
        String sql = "INSERT INTO brands (name, country) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, brand.getName());
            ps.setString(2, brand.getCountry());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error saving brand " + brand.getName(), e);
        }
    }

    @Override
    public Brand update(Brand brand) {
        String sql = "UPDATE brands SET name=?, country=? WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, brand.getName());
            ps.setString(2, brand.getCountry());
            ps.setInt(3, brand.getId());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                return brand;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating brand " + brand.getId(), e);
        }
        return null;
    }

    @Override
    public void delete(Brand brand) {
        String sql = "DELETE FROM brands WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, brand.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting brand " + brand.getId(), e);
        }
    }
}
