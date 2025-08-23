package org.example.daos;

import org.example.constants.DatabasePaths;
import java.io.File;


public class DatabaseFileConnection {

    // Instance unique du singleton
    private static DatabaseFileConnection instance;

    // Constructeur prive
    private DatabaseFileConnection() {
        initOFConnection();
    }

    // Acces a l instance
    public static DatabaseFileConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseFileConnection();
        }
        return instance;
    }


    private void initOFConnection() {
        File databaseFolder = new File(DatabasePaths.DATABASE_FOLDER);


        if (!databaseFolder.exists()) {
            if (databaseFolder.mkdirs()) {
                System.out.println("✨ Database folder created: " + DatabasePaths.DATABASE_FOLDER);
            } else {
                System.out.println("❌ Failed to create database folder!");
                throw new RuntimeException("Database Service not found");
            }
        }


        File modelsFolder = new File(DatabasePaths.MODELS_FOLDER);
        if (!modelsFolder.exists()) {
            if (modelsFolder.mkdirs()) {
                System.out.println("✨ Models folder created: " + DatabasePaths.MODELS_FOLDER);
            }
        }


        System.out.println("📂 Database initialized at: " + DatabasePaths.MODELS_FOLDER);
    }


    public File getDatabaseFile() {
        return new File(DatabasePaths.MODELS_FOLDER);
    }
}
