package com.mycompany.taskmanager.controller;

import com.mycompany.taskmanager.view.MainView;
import com.mycompany.taskmanager.view.ViewOption;
import com.mycompany.taskmanager.view.DetailedEventView;
import com.mycompany.taskmanager.view.DetailedTaskView;
import com.mycompany.database.SQLiteEventDAO;
import com.mycompany.database.SQLiteTaskDAO;
import com.mycompany.taskmanager.model.MainModel;

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
        mainView.getFilterByComboBox().addActionListener(new ChangeViewFilterListener());
    }
    
    public void initializeMainView() {
    	mainModel.createTaskViews(sqliteTaskDAO.getAllTasks(), mainView);
    	mainModel.createEventViews(sqliteEventDAO.getAllEvents(), mainView);
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
    
    private class ChangeViewFilterListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
        	ViewOption selectedOption = (ViewOption) mainView.getFilterByComboBox().getSelectedItem();
            // Perform actions based on the selected option
            mainModel.setFilterBy(selectedOption);
            mainView.updateContentPanel();
        }
    }
}

