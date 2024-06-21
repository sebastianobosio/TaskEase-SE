package com.mycompany.taskmanager.view;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;


import com.mycompany.taskmanager.model.Task;
import com.mycompany.taskmanager.model.Event;
import com.mycompany.taskmanager.model.MainModel;

public class MainView extends JPanel{  
	
    private MainModel mainModel;
    private JButton createTaskButton;
    private JButton createEventButton;
    private JPanel contentPanel;
    private JComboBox<ViewOption> filterByComboBox;
    
    public MainView(MainModel mainModel) {
        // Initialize components
    	this.mainModel = mainModel;
    	
        contentPanel = new JPanel(); // Panel to hold task and event views        
        contentPanel.setBackground(new Color(240, 240, 240));
        
        //create content panel for task and events
        contentPanel.setLayout(new GridBagLayout());
        JScrollPane scrollPane = new JScrollPane(contentPanel);  
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
             
        //create panel for buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(100, 100, 100));
        
        createTaskButton = new JButton("Create Task");
        createTaskButton.setFont(new Font("Arial", Font.PLAIN, 14));
        createTaskButton.setBackground(new Color(240, 240, 240));
        
        createEventButton = new JButton("Create Event");
        createEventButton.setBackground(new Color(240, 240, 240));
        createEventButton.setFont(new Font("Arial", Font.PLAIN, 14));
        
        ViewOption[] options = {ViewOption.TASKS, ViewOption.EVENTS, ViewOption.BOTH};
        filterByComboBox = new JComboBox<>(options);
        filterByComboBox.setBackground(new Color(240, 240, 240));
        filterByComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        filterByComboBox.setSelectedItem(ViewOption.BOTH); // Set "Both" as the default selection
        
        buttonPanel.add(createTaskButton);
        buttonPanel.add(createEventButton);
        buttonPanel.add(filterByComboBox);
        
        JLabel titleLabel = new JLabel("Task and Event Manager");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        
        // Set up layout
        setLayout(new BorderLayout());
        add(titleLabel, BorderLayout.NORTH); // Add the label to the top
        add(scrollPane, BorderLayout.CENTER);  
        add(buttonPanel, BorderLayout.SOUTH);
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
    	
    	List<Component> combinedList = mainModel.fetchData();
        contentPanel.removeAll();
        
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.insets = new Insets(0, 0, 10, 0); // Bottom padding after each component
        
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
        	    JPanel separatorPanel = new JPanel(new BorderLayout());
        	    JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        	    
        	    // Set separator properties
        	    separator.setForeground(new Color(100, 100, 100)); // Set separator color to red
        	    
        	    separatorPanel.add(separator, BorderLayout.CENTER);
        	    
        	    // Add the separator panel with constraints
        	    contentPanel.add(separatorPanel, constraints);
        		constraints.gridy++;
            }
        	previousCmpDate = currentCmpDate;
        	contentPanel.add(cmp, constraints);
        	//contentPanel.add(Box.createVerticalStrut(10), constraints);
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
    
    public JComboBox<ViewOption> getFilterByComboBox() {
    	return filterByComboBox;
    }
}
