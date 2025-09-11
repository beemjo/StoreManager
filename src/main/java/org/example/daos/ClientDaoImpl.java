package org.example.daos;

import org.example.models.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Primary
public class ClientDaoImpl implements ClientDao {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Client> rowMapper = new BeanPropertyRowMapper<>(Client.class);

    @Autowired
    public ClientDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        createTable();
    }

    private void createTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS clients (
                    id INT PRIMARY KEY AUTO_INCREMENT,
                    name VARCHAR(100),
                    email VARCHAR(100),
                    phone VARCHAR(20)
                )
                """;
        jdbcTemplate.execute(sql);
    }

    @Override
    public List<Client> findAll() {
        String sql = "SELECT * FROM clients";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Client findById(Integer id) {
        String sql = "SELECT * FROM clients WHERE id = ?";
        List<Client> result = jdbcTemplate.query(sql, rowMapper, id);
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public void save(Client client) {
        String sql = "INSERT INTO clients (name, email, phone) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, client.getName(), client.getEmail(), client.getPhone());
    }

    @Override
    public Client update(Client client) {
        String sql = "UPDATE clients SET name=?, email=?, phone=? WHERE id=?";
        int rows = jdbcTemplate.update(sql, client.getName(), client.getEmail(), client.getPhone(), client.getId());
        return rows > 0 ? client : null;
    }

    @Override
    public void delete(Client client) {
        String sql = "DELETE FROM clients WHERE id=?";
        jdbcTemplate.update(sql, client.getId());
    }

    // --- Custom method ---
    @Override
    public List<Client> findByName(String name) {
        String sql = "SELECT * FROM clients WHERE name LIKE ?";
        return jdbcTemplate.query(sql, rowMapper, "%" + name + "%");
    }
}
