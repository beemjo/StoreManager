package org.example.daos;

import org.example.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProductDaoImpl implements ProductDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        createTable();
    }

    private void createTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS products (
                    id INT PRIMARY KEY AUTO_INCREMENT,
                    name VARCHAR(100),
                    description VARCHAR(255),
                    price DOUBLE,
                    quantity INT
                )
                """;
        jdbcTemplate.execute(sql);
    }

    private final RowMapper<Product> rowMapper = new BeanPropertyRowMapper<>(Product.class);

    @Override
    public List<Product> findAll() {
        String sql = "SELECT * FROM products";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Product findById(Integer id) {
        String sql = "SELECT * FROM products WHERE id = ?";
        List<Product> result = jdbcTemplate.query(sql, rowMapper, id);
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public void save(Product product) {
        String sql = "INSERT INTO products (name, description, quantity, price) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, product.getName(), product.getDescription(), product.getQuantity(), product.getPrice());
    }

    @Override
    public Product update(Product product) {
        String sql = "UPDATE products SET name=?, description=?, price=?, quantity=? WHERE id=?";
        int rows = jdbcTemplate.update(sql, product.getName(), product.getDescription(), product.getPrice(), product.getQuantity(), product.getId());
        return rows > 0 ? product : null;
    }

    @Override
    public void delete(Product product) {
        String sql = "DELETE FROM products WHERE id=?";
        jdbcTemplate.update(sql, product.getId());
    }

    // --- Custom methods ---

    @Override
    public List<Product> findInStockProducts() {
        String sql = "SELECT * FROM products WHERE quantity > 0";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public List<Product> findByName(String keyword) {
        String sql = "SELECT * FROM products WHERE name LIKE ?";
        return jdbcTemplate.query(sql, rowMapper, "%" + keyword + "%");
    }
}
