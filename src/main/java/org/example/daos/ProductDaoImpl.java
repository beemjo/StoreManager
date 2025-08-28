package org.example.daos;

import org.example.models.Product;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class ProductDaoImpl implements ProductDao {
    private final Connection connection;

    public ProductDaoImpl() {
        connection = DatabaseConnection.getInstance().getConnection();
        createTable();
    }

    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS products (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(100), " +
                "description VARCHAR(255), " +
                "price DOUBLE, " +
                "quantity INT" +
                ")";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("✅ Table 'products' ready.");
        } catch (SQLException e) {
            System.err.println("❌ Error creating table: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public List<Product> findInStockProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE quantity > 0";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                products.add(Product.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .description(rs.getString("description"))
                        .price(rs.getDouble("price"))
                        .quantity(rs.getInt("quantity"))
                        .build());
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching in-stock products", e);
        }
        return products;
    }


    @Override
    public List<Product> findByName(String keyword) {

        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE name LIKE ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                products.add(Product.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .description(rs.getString("description"))
                        .price(rs.getDouble("price"))
                        .quantity(rs.getInt("quantity"))
                        .build());
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding products by name", e);
        }
        return products;
    }

    @Override
    public List<Product> findAll() {

        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";
        try {
            Statement stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                products.add(Product.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .price(rs.getDouble("price"))
                        .description(rs.getString("description"))
                        .quantity(rs.getInt("quantity"))
                        .build());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;
    }

    @Override
    public Product findById(Integer id) {
        String sql = "SELECT * FROM products WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Product.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .description(rs.getString("description"))
                        .price(rs.getDouble("price"))
                        .quantity(rs.getInt("quantity"))
                        .build();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding product with id " + id, e);
        }
        return null;
    }

    @Override
    public void save(Product product) {
        String sql = """
                INSERT INTO products (
                name, description, quantity, price
                ) Values (?, ?, ?, ?)
                """;
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setInt(3, product.getQuantity());
            ps.setDouble(4, product.getPrice());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error saving order for client " + product.getName(), e);
        }
    }

    @Override
    public Product update(Product product) {
        String sql = "UPDATE products SET name=?, description=?, price=?, quantity=? WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setDouble(3, product.getPrice());
            ps.setInt(4, product.getQuantity());
            ps.setInt(5, product.getId());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                return product;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating product " + product.getId(), e);
        }
        return null;
    }


    @Override
    public void delete(Product product) {
        String sql = "DELETE FROM products WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, product.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting product " + product.getId(), e);
        }

    }
}
