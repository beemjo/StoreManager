package org.example;

import org.example.daos.DatabaseFileConnection;
import org.example.seeders.ProductSeeder;

public class Main {
    public static void main(String[] args) {


        DatabaseFileConnection dbConnection = DatabaseFileConnection.getInstance();


        ProductSeeder seeder = new ProductSeeder();
        seeder.init();


        System.out.println("✅ Products created");

    }
}
