package com.example.oopactivity.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection connect() {

        try {

            return DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/studentdb",
                    "postgres",
                    "177013"
            );

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
