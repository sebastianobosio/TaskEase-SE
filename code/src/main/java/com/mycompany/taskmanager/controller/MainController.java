package com.mycompany.taskmanager.controller;

import com.mycompany.taskmanager.view.MainView;
import com.mycompany.taskmanager.view.TaskView;
import com.mycompany.taskmanager.view.DetailedEventView;
import com.mycompany.taskmanager.view.DetailedTaskView;
import com.mycompany.taskmanager.view.EventView;
import com.mycompany.database.SQLiteEventDAO;
import com.mycompany.database.SQLiteTaskDAO;
import com.mycompany.taskmanager.model.Event;
import com.mycompany.taskmanager.model.MainModel;
import com.mycompany.taskmanager.model.Task;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainController {
    private MainView mainView;
    private MainModel mainModel;
    SQLiteTaskDAO sqliteTaskDAO = new SQLiteTaskDAO();
    SQLiteEventDAO sqliteEventDAO = new SQLiteEventDAO();
    
    public MainController(MainView mainView, MainModel mainModel) {
        this.mainView = mainView;
        this.mainModel = mainModel;
        // Attach action listeners to buttons
        mainView.getCreateTaskButton().addActionListener(new CreateTaskButtonListener());
        mainView.getCreateEventButton().addActionListener(new CreateEventButtonListener());
    }
    
    public void updateMainView() {
    	mainModel.createTaskViews(sqliteTaskDAO.getAllTasks());
    	mainModel.createEventViews(sqliteEventDAO.getAllEvents());
    	mainView.updateContentPanel();
    }
    
    // Action listener for the "Create Task" button
    private class CreateTaskButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
        	DetailedTaskView detailedTaskView = new DetailedTaskView();
        	TaskController taskController = new TaskController(detailedTaskView, mainView);
        }
    }

    // Action listener for the "Create Event" button
    private class CreateEventButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
        	DetailedEventView detailedEventView = new DetailedEventView();
        	EventController eventController = new EventController(detailedEventView, mainView);
        }
    }
}
