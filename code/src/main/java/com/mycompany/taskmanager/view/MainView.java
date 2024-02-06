package com.mycompany.taskmanager.view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
        	TaskView taskView = iterator.next();
            Task task = taskView.getTask();
            if (task == taskToDelete) {
                iterator.remove(); // Remove the current task
                System.out.println("delete task view: " + taskViews);
            }
    	
        }
    }
    
    public void createEventViews(List<Event> eventList) {
    	for (Event event: eventList) {
    		addEventView(event);
    	}
    }
    
    public void addEventView(Event event) {
    	EventView eventView = new EventView(event);
		EventController eventController = new EventController(eventView, this);
		eventControllers.add(eventController);
		eventViews.add(eventView);
    }
    
    public void deleteEventView(Event eventToDelete) {
    	// Remove eventViews that are build on the specific event model
    	Iterator<EventView> iterator = eventViews.iterator();
        while (iterator.hasNext()) {
        	EventView eventView = iterator.next();
        	Event event = eventView.getEvent();
            if (event == eventToDelete) {
                iterator.remove(); // Remove the current event
                System.out.println("delete event view: " + eventViews);
            }
    	
        }
    }
    
    public void updateContentPanel() {
        // Clear the existing content panel
        contentPanel.removeAll();
        
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.NORTH;
        
        List<Component> combinedList = new ArrayList<>(List.of(contentPanel.getComponents()));
        
        
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
        
        for (Object obj : combinedList) {
        	contentPanel.add((Component) obj, constraints);
        	constraints.gridy++;
        	contentPanel.add(Box.createVerticalStrut(10), constraints);
        	constraints.gridy++;
        }

        // Add a separator (you can customize this as needed)
        contentPanel.add(new JSeparator(SwingConstants.HORIZONTAL));

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
