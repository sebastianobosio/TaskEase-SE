package com.mycompany.taskmanager;

import com.mycompany.taskmanager.model.Event;
import com.mycompany.taskmanager.model.Task;
import com.mycompany.taskmanager.view.MainView;
import com.mycompany.taskmanager.view.TaskView;
import com.mycompany.taskmanager.view.EventView;
import com.mycompany.taskmanager.controller.MainController;
import com.mycompany.database.SQLiteDB;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Date;
import java.time.LocalDate;
import java.time.LocalTime;

public class TaskManagerApp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// creating mock up cases
		Task task1 = new Task("Task 1", "Description for Task 1", new Date(), Task.TaskStatus.NOTSTARTED);
        Task task2 = new Task("Task 2", "Description for Task 2", new Date(), Task.TaskStatus.ONGOING);
        
        LocalDate startDate = LocalDate.of(2023, 4, 13);  // April 13, 2023
        LocalTime startTime = LocalTime.of(17, 5);        // 17:05

        LocalDate endDate = LocalDate.of(2023, 4, 14);    // April 14, 2023
        LocalTime endTime = LocalTime.of(18, 30);         // 18:30

        Event event = new Event("Conference", "Annual conference", startDate, startTime, endDate, endTime, "Convention Center");
        
        // Create GUI components
        MainView mainView = new MainView();
        TaskView taskView1 = new TaskView(task1);
        TaskView taskView2 = new TaskView(task2);
        EventView eventView1 = new EventView(event);

        // Add task and event views to the main view
        mainView.addTaskView(taskView1);
        mainView.addTaskView(taskView2);
        mainView.addEventView(eventView1);

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
        
        // Set up the main frame
        JFrame frame = new JFrame("Task Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(mainView);
        frame.pack();
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        frame.setVisible(true);
	}

}
