package com.mycompany.taskmanager.view;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.mycompany.taskmanager.controller.EventController;
import com.mycompany.taskmanager.controller.TaskController;
import com.mycompany.taskmanager.model.Task;
import com.mycompany.taskmanager.model.Event;
import com.mycompany.taskmanager.model.MainModel;

public class MainView extends JPanel{  
    private MainModel mainModel;
    private JButton createTaskButton;
    private JButton createEventButton;
    private JPanel contentPanel;
    
    public MainView(MainModel mainModel) {
        // Initialize components
    	this.mainModel = mainModel;
        
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
    
    // Methods to interact with the model
    public void addTaskView(Task task) {
        mainModel.addTaskView(task, this);
    }

    public void deleteTaskView(Task task) {
        mainModel.deleteTaskView(task);
    }
    
    // Methods to interact with the model
    public void addEventView(Event event) {
        mainModel.addEventView(event, this);
    }

    public void deleteEventView(Event event) {
        mainModel.deleteEventView(event);
    }
    
    public void updateContentPanel() {
        // Clear the existing content panel
    	
    	List<Component> combinedList = mainModel.getTaskEventList();
        contentPanel.removeAll();
        
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.NORTH;
        
        LocalDate previousCmpDate = null;
        
        for (Component cmp : combinedList) {
        	LocalDate currentCmpDate = null;
        	if (cmp instanceof TaskView) {
        		currentCmpDate = ((TaskView) cmp).getTask().getDueDate();
        	} else if (cmp instanceof EventView) {
        		currentCmpDate = ((EventView) cmp).getEvent().getStartDate();
        	}
        	// if current cmp due/start date is different from the previous task/event due/start date than add a separator
        	if (previousCmpDate != null && !currentCmpDate.equals(previousCmpDate)) {
        		contentPanel.add(new JSeparator(SwingConstants.HORIZONTAL), constraints);
            }
        	previousCmpDate = currentCmpDate;
        	
        	contentPanel.add(cmp, constraints);
        	constraints.gridy++;
        	contentPanel.add(Box.createVerticalStrut(10), constraints);
        	constraints.gridy++;
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
