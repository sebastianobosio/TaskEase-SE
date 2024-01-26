package com.mycompany.taskmanager.view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import com.mycompany.taskmanager.controller.EventController;
import com.mycompany.taskmanager.controller.TaskController;

public class MainView extends JPanel {
    private List<TaskView> taskViews;
    private List<EventView> eventViews;
    private List<TaskController> taskControllers;
    private List<EventController> eventControllers;
    private JButton createTaskButton;
    private JButton createEventButton;
    private JPanel contentPanel;

    public MainView() {
        // Initialize components
        taskViews = new ArrayList<>();
        eventViews = new ArrayList<>();
        taskControllers = new ArrayList<>();
        eventControllers = new ArrayList<>();
        createTaskButton = new JButton("Create Task");
        createEventButton = new JButton("Create Event");
        contentPanel = new JPanel(); // Panel to hold task and event views
        
        // Set up layout
        setLayout(new BorderLayout());
        
        //create content panel for task and events
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        add(scrollPane, BorderLayout.CENTER);
        
        //create panel for buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(createTaskButton);
        buttonPanel.add(createEventButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void addTaskView(TaskView taskView) {
        taskViews.add(taskView);
        //TaskController taskController = new TaskController(taskView);
        //taskControllers.add(taskController);
        updateContentPanel();
    }

    public void addEventView(EventView eventView) {
        eventViews.add(eventView);
        //EventController eventController = new EventController(eventView);
        //eventControllers.add(eventController);
        updateContentPanel();
    }
    
    private void updateContentPanel() {
        // Clear the existing content panel
        contentPanel.removeAll();

        // Add each TaskView to the content panel
        for (TaskView taskView : taskViews) {
            contentPanel.add(taskView);
            contentPanel.add(Box.createVerticalStrut(10)); // vertical space
        }

        // Add a separator (you can customize this as needed)
        contentPanel.add(new JSeparator(SwingConstants.HORIZONTAL));

        // Add each EventView to the content panel
        for (EventView eventView : eventViews) {
            contentPanel.add(eventView);
            contentPanel.add(Box.createVerticalStrut(10));
        }

        // Revalidate and repaint to update the UI
        revalidate();
        repaint();
    }

    public JButton getCreateTaskButton() {
        return createTaskButton;
    }

    public JButton getCreateEventButton() {
        return createEventButton;
    }
}
