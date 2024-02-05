package com.mycompany.taskmanager.controller;

import com.mycompany.taskmanager.view.MainView;
import com.mycompany.taskmanager.view.TaskView;
import com.mycompany.taskmanager.view.DetailedTaskView;
import com.mycompany.taskmanager.view.EventView;
import com.mycompany.database.SQLiteTaskDAO;
import com.mycompany.taskmanager.model.Event;
import com.mycompany.taskmanager.model.Task;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainController {
    private MainView mainView;
    SQLiteTaskDAO sqliteTaskDAO = new SQLiteTaskDAO();
    
    public MainController(MainView mainView) {
        this.mainView = mainView;

        // Attach action listeners to buttons
        mainView.getCreateTaskButton().addActionListener(new CreateTaskButtonListener());
        mainView.getCreateEventButton().addActionListener(new CreateEventButtonListener());
    }
    
    public void updateMainView() {
    	mainView.createTaskViews(sqliteTaskDAO.getAllTasks());
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
            // You can handle the creation of a new event here
            // For now, let's add a mockup event to the view
            //EventView eventView = new EventView(new Event("New Event", "Description for the new event", null,null,null, null, "New Location"));
            //mainView.addEventView(eventView);
        }
    }
}
