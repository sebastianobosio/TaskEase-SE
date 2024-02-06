package com.mycompany.taskmanager;

import com.mycompany.taskmanager.view.MainView;
import com.mycompany.taskmanager.controller.MainController;
import com.mycompany.database.SQLiteDB;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class TaskManagerApp {

	public static void main(String[] args) {
	
		// Create GUI components
        MainView mainView = new MainView();

        // Create the main controller and pass the main view
        MainController mainController = new MainController(mainView);
        
        // Initialize the DB
        try {
        	SQLiteDB.getConnection(); // Initialize database connection
        	System.out.println("Database initialized successfully.");
        } catch (SQLException e) {
        	e.printStackTrace();
        	System.exit(1); // Exit application if database initialization fails
        }
        
        mainController.updateMainView();
        
        // Set up the main frame
        JFrame frame = new JFrame("TaskEase");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(mainView);
        frame.setSize(600,600);
        frame.setVisible(true);
	}

}
