package org.example.daos;

import org.example.models.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDaoImpl implements CategoryDao {

    private final Connection connection;

    public CategoryDaoImpl() {
        connection = DatabaseConnection.getInstance().getConnection();
        createTable();
    }

    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS categories (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(100) NOT NULL UNIQUE, " +
                "description VARCHAR(255)" +
                ")";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("✅ Table 'categories' ready.");
        } catch (SQLException e) {
            System.err.println("❌ Error creating table: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public List<Category> findAll() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM categories";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                categories.add(Category.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .description(rs.getString("description"))
                        .build());
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching all categories", e);
        }
        return categories;
    }

    @Override
    public Category findById(Integer id) {
        String sql = "SELECT * FROM categories WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Category.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .description(rs.getString("description"))
                        .build();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding category with id " + id, e);
        }
        return null;
    }

    @Override
    public void save(Category category) {

        List<Category> existing = findByName(category.getName());
        if (!existing.isEmpty()) {
            System.out.println("Category '" + category.getName() + "' already exists, skipping...");
            return;
        }

        String sql = "INSERT INTO categories (name, description) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, category.getName());
            stmt.setString(2, category.getDescription());
            stmt.executeUpdate();


            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    category.setId(rs.getInt(1));
                }
            }

            System.out.println("Category '" + category.getName() + "' saved successfully!");
        } catch (SQLException e) {
            throw new RuntimeException("Error saving category " + category.getName(), e);
        }
    }


    @Override
    public Category update(Category category) {
        String sql = "UPDATE categories SET name=?, description=? WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, category.getName());
            ps.setString(2, category.getDescription());
            ps.setInt(3, category.getId());
            int rows = ps.executeUpdate();
            if (rows > 0) return category;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating category " + category.getId(), e);
        }
        return null;
    }

    @Override
    public void delete(Category category) {
        String sql = "DELETE FROM categories WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, category.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting category " + category.getId(), e);
        }
    }

    @Override
    public List<Category> findByName(String keyword) {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM categories WHERE LOWER(name) LIKE LOWER(?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                categories.add(Category.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .description(rs.getString("description"))
                        .build());
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding categories by name", e);
        }
        return categories;
    }
}
