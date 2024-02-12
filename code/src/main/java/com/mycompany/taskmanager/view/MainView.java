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

public class MainView extends JPanel {  
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
    
    public void updateContentPanel() {
        // Clear the existing content panel
        contentPanel.removeAll();
        
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.NORTH;
        
        List<Component> combinedList = new ArrayList<>();
        combinedList.addAll(taskViews);
        combinedList.addAll(eventViews);
        
        // Sort the combined list by date and time
        Collections.sort(combinedList, new Comparator<Object>() {
            @Override
            public int compare(Object o1, Object o2) {
                if (o1 instanceof TaskView && o2 instanceof TaskView) {
                    TaskView taskView1 = (TaskView) o1;
                    TaskView taskView2 = (TaskView) o2;
                    int dateComparison = taskView1.getTask().getDueDate().compareTo(taskView2.getTask().getDueDate());
                    if (dateComparison != 0) {
                        return dateComparison;
                    }
                    return taskView1.getTask().getDueTime().compareTo(taskView2.getTask().getDueTime());
                } else if (o1 instanceof EventView && o2 instanceof EventView) {
                    EventView eventView1 = (EventView) o1;
                    EventView eventView2 = (EventView) o2;
                    int dateComparison = eventView1.getEvent().getStartDate().compareTo(eventView2.getEvent().getStartDate());
                    if (dateComparison != 0) {
                        return dateComparison;
                    }
                    return eventView1.getEvent().getStartTime().compareTo(eventView2.getEvent().getStartTime());
                } else if (o1 instanceof EventView && o2 instanceof TaskView) {
                	EventView eventView = (EventView) o1;
                	TaskView taskView = (TaskView) o2;
                	int dateComparison = eventView.getEvent().getStartDate().compareTo(taskView.getTask().getDueDate());
                	if (dateComparison != 0) {
                		return dateComparison;
                	}
                	return eventView.getEvent().getStartTime().compareTo(taskView.getTask().getDueTime());
                } else if (o1 instanceof TaskView && o2 instanceof EventView) {
                	TaskView taskView = (TaskView) o1;
                	EventView eventView = (EventView) o2;
                	int dateComparison = eventView.getEvent().getStartDate().compareTo(taskView.getTask().getDueDate());
                	if (dateComparison != 0) {
                		return dateComparison;
                	}
                	return eventView.getEvent().getStartTime().compareTo(taskView.getTask().getDueTime());
                }
                return 0;
            }
        });
        
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
