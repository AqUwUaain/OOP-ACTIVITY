package com.example.oopactivity;

import com.example.oopactivity.database.DBConnection;

public class Launcher {

    public static void main(String[] args) {

        System.out.println(DBConnection.connect());

        MainApp.main(args);
    }
}
