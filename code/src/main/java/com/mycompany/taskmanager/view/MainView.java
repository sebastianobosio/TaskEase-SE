package com.mycompany.taskmanager.view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MainView extends JPanel {
    private List<TaskView> taskViews;
    private List<EventView> eventViews;
    private JButton createTaskButton;
    private JButton createEventButton;
    private JPanel contentPanel;

    public MainView() {
        // Initialize components
        taskViews = new ArrayList<>();
        eventViews = new ArrayList<>();
        createTaskButton = new JButton("Create Task");
        createEventButton = new JButton("Create Event");
        contentPanel = new JPanel(); // Panel to hold task and event views
        
        // Set up layout
        setLayout(new BorderLayout());

        // Add buttons and content panel to the main view
        add(createTaskButton, BorderLayout.NORTH);
        add(createEventButton, BorderLayout.SOUTH);
        add(contentPanel, BorderLayout.CENTER);

        // Set a layout for the content panel (BoxLayout for vertical stacking)
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
    }

    public void addTaskView(TaskView taskView) {
        taskViews.add(taskView);
        updateContentPanel();
    }

    public void addEventView(EventView eventView) {
        eventViews.add(eventView);
        updateContentPanel();
    }
    
    private void updateContentPanel() {
        // Clear the existing content panel
        contentPanel.removeAll();

        // Add each TaskView to the content panel
        for (TaskView taskView : taskViews) {
            contentPanel.add(taskView);
        }

        // Add a separator (you can customize this as needed)
        contentPanel.add(new JSeparator(SwingConstants.HORIZONTAL));

        // Add each EventView to the content panel
        for (EventView eventView : eventViews) {
            contentPanel.add(eventView);
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
