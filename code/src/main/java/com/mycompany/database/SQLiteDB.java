package com.mycompany.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteDB {
   
	public static final String DB_REL_FILE = "../db/tasks_events.db3";
	public static final String DB_URL = "jdbc:sqlite:" + DB_REL_FILE;
	
    static {
        try {
            createDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1); // Exit application if database creation fails
        }
    }

    private static void createDatabase() throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            Statement statement = connection.createStatement();
            
            // Create tables if they don't exist
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS tasks (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, description TEXT, due_date TEXT, status TEXT)");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS events (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, description TEXT, location TEXT, start_date TEXT, start_time TEXT, end_date TEXT, end_time TEXT)");
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }
}
