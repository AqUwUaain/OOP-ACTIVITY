package com.example.oopactivity.database;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final Dotenv dotenv = Dotenv.load();

    private static final String URL =
            dotenv.get("DB_URL");

    private static final String USER =
            dotenv.get("DB_USER");

    private static final String PASSWORD =
            dotenv.get("DB_PASSWORD");

    public static Connection connect() {

        try {

            Connection connection =
                    DriverManager.getConnection(URL, USER, PASSWORD);

            System.out.println("SUPABASE CONNECTED");

            return connection;

        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }
    }
}