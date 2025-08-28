package org.example.daos;

import lombok.extern.slf4j.Slf4j;
import org.example.constants.DatabaseProperties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Slf4j
public class DatabaseConnection {
    private static DatabaseConnection instance;
    private static Connection connection;

    private DatabaseConnection() {
        try {

            // Connexion mySQL avec les infos de databaseproperties
            connection = DriverManager.getConnection(
                    DatabaseProperties.DATABASE_URL,
                    DatabaseProperties.DATABASE_USER,
                    DatabaseProperties.DATABASE_PASSWORD
            );

            log.info("✅ Connected to MySQL database!");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("❌ Database connection failed!", e);
            throw new RuntimeException("Failed to connect to MySQL", e);
        }
    }

    // Singleton
    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        } else {
            log.warn("Already connected to database");
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                log.info("🔒 MySQL connection closed.");
                connection = null;
            } catch (SQLException e) {
                log.error("❌ Error closing connection.", e);
                throw new RuntimeException(e);
            }
        }
    }
}
