package com.mycompany.taskmanager.controller;

import com.mycompany.taskmanager.view.MainView;
import com.mycompany.taskmanager.view.TaskView;
import com.mycompany.taskmanager.view.EventView;
import com.mycompany.taskmanager.model.Event;
import com.mycompany.taskmanager.model.Task;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainController {
    private MainView mainView;

    public MainController(MainView mainView) {
        this.mainView = mainView;

        // Attach action listeners to buttons
        mainView.getCreateTaskButton().addActionListener(new CreateTaskButtonListener());
        mainView.getCreateEventButton().addActionListener(new CreateEventButtonListener());
    }

    // Action listener for the "Create Task" button
    private class CreateTaskButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // You can handle the creation of a new task here
            // For now, let's add a mockup task to the view
        	//DetailedTaskView detailedTaskView = new DetailedTaskView();
            TaskView taskView = new TaskView(new Task("New Task", "Description for the new task", null, null));
            mainView.addTaskView(taskView);
        }
    }

    // Action listener for the "Create Event" button
    private class CreateEventButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // You can handle the creation of a new event here
            // For now, let's add a mockup event to the view
            EventView eventView = new EventView(new Event("New Event", "Description for the new event", null,null,null, null, "New Location"));
            mainView.addEventView(eventView);
        }
    }
}
