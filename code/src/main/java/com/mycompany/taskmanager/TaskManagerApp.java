package com.mycompany.taskmanager;

import com.mycompany.taskmanager.view.MainView;
import com.mycompany.taskmanager.controller.MainController;
import com.mycompany.taskmanager.model.MainModel;
import com.mycompany.database.SQLiteDB;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class TaskManagerApp {

	public static void main(String[] args) {
		
		// Initialize the main model
		MainModel mainModel = new MainModel();
		
		// Create GUI components
        MainView mainView = new MainView(mainModel);

        // Create the main controller and pass the main view
        MainController mainController = new MainController(mainView, mainModel);
        
        // Initialize the DB
        try {
            SQLiteDB dbInstance = SQLiteDB.getInstance(); // Get the singleton instance
            dbInstance.getConnection(); // Initialize database connection
            System.out.println("Database initialized successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1); // Exit application if database initialization fails
        }
        
        mainController.initializeMainView();
        
        // Set up the main frame
        JFrame frame = new JFrame("TaskEase");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(mainView);
        frame.setSize(600,600);
        frame.setVisible(true);
	}

}
