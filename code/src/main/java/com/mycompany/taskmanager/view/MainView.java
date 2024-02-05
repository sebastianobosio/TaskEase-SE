package com.mycompany.taskmanager.view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.mycompany.taskmanager.controller.EventController;
import com.mycompany.taskmanager.controller.TaskController;
import com.mycompany.taskmanager.model.Task;
import com.mycompany.taskmanager.model.Event;

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
        
        contentPanel = new JPanel(); // Panel to hold task and event views        
        
        //create content panel for task and events
        contentPanel.setLayout(new GridBagLayout());
        JScrollPane scrollPane = new JScrollPane(contentPanel);  
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
             
        //create panel for buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        createTaskButton = new JButton("Create Task");
        createEventButton = new JButton("Create Event");
        buttonPanel.add(createTaskButton);
        buttonPanel.add(createEventButton);
        
        // Set up layout
        setLayout(new BorderLayout());
        add(buttonPanel, BorderLayout.SOUTH);
        add(scrollPane);
        
    }
    
    public void createTaskViews(List<Task> taskList) {
    	for (Task task: taskList) {
    		addTaskView(task);
    	}
    }
    
    public void addTaskView(Task task) {
    	TaskView taskView = new TaskView(task);
		TaskController taskController = new TaskController(taskView, this);
		taskControllers.add(taskController);
		taskViews.add(taskView);
    }
    
    public void deleteTaskView(Task taskToDelete) {
    	// Remove taskViews that are build on the specific task model
    	Iterator<TaskView> iterator = taskViews.iterator();
        while (iterator.hasNext()) {
            Task task = iterator.next().getTask();
            if (task == taskToDelete) {
                iterator.remove(); // Remove the current task
                System.out.println("delete task view: " + taskViews);
            }
    	
        }
    }
    
    public void createEventView(List<Event> eventList) {
    	for (Event event: eventList) {
    		EventView eventView = new EventView(event);
    		EventController eventController = new EventController(eventView);
    		eventControllers.add(eventController);
    		eventViews.add(eventView);
    	}
    }
    
    public void updateContentPanel() {
    	System.out.println(taskViews);
        // Clear the existing content panel
        contentPanel.removeAll();
        
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.NORTH;
        
        // Add each TaskView to the content panel
        for (TaskView taskView : taskViews) {
        	contentPanel.add(taskView, constraints);
            constraints.gridy++; // Move to the next row
            contentPanel.add(Box.createVerticalStrut(10), constraints); // Add vertical space
            constraints.gridy++; // Move to the next row
        }

        // Add a separator (you can customize this as needed)
        contentPanel.add(new JSeparator(SwingConstants.HORIZONTAL));

        // Add each EventView to the content panel
        for (EventView eventView : eventViews) {
        	constraints.gridy++; // Move to the next row
            contentPanel.add(eventView, constraints);
            constraints.gridy++; // Move to the next row
            contentPanel.add(Box.createVerticalStrut(10), constraints); // Add vertical space
        }

        // Revalidate and repaint to update the UI
        revalidate();
        repaint();
    }
    
    // used by MainController
    public JButton getCreateTaskButton() {
        return createTaskButton;
    }

    public JButton getCreateEventButton() {
        return createEventButton;
    }
}
