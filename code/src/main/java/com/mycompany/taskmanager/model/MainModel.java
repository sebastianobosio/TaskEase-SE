package com.mycompany.taskmanager.model;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.mycompany.taskmanager.controller.EventController;
import com.mycompany.taskmanager.controller.TaskController;
import com.mycompany.taskmanager.view.EventView;
import com.mycompany.taskmanager.view.MainView;
import com.mycompany.taskmanager.view.TaskView;
import com.mycompany.taskmanager.view.ViewOption;

public class MainModel {
	private List<TaskView> taskViews;
	private List<EventView> eventViews;
	private List<TaskController> taskControllers;
	private List<EventController> eventControllers;
	private List<Task> completedTasks;
	private List<Event> completedEvents;
	private ViewOption filterBy = ViewOption.BOTH; // set Both as default the same as in the mainView
	
	public MainModel() {
		this.taskViews = new ArrayList<>();
		this.eventViews = new ArrayList<>();
		this.completedTasks = new ArrayList<>();
		this.completedEvents = new ArrayList<>();
    }
	
	public void createTaskViews(List<Task> taskList, MainView mainView) {
    	for (Task task: taskList) {
    		addTaskView(task, mainView);
    	}
    }
    
    public void addTaskView(Task task, MainView mainView) {
    	// check on dueDate, dueTime and status before creating the taskView.
    	if (task.getStatus().equals("Completed")) {
    		// if task is completed add it to the list of completed tasks, but not display it.
    		completedTasks.add(task);
    		return;
    	} else if (task.isTaskOverdue()) {
    		// if task dueTime and dueDate is passed than display it, but on the top and in red
    		// it's displayed on top thanks to the comparator.
    		TaskView taskView = new TaskView(task);
    		taskView.getTaskTitle().setForeground(Color.red);
    		TaskController taskController = new TaskController(taskView, mainView);
    		//taskControllers.add(taskController);
    		taskViews.add(taskView);
    		return;
    	} else {
    		// if task dueDate is in the future than simply display it
    		TaskView taskView = new TaskView(task);
    		TaskController taskController = new TaskController(taskView, mainView);
    		//taskControllers.add(taskController);
    		taskViews.add(taskView);
    	}
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
    
    public void createEventViews(List<Event> eventList, MainView mainView) {
    	for (Event event: eventList) {
    		addEventView(event, mainView);
    	}
    }
    
    public void addEventView(Event event, MainView mainView) {
    	// check on endDate and endTime creating the eventView.
    	if (event.isEventOverdue()) {
    		// if event is overdue add it to the list of completed events, but not display it.
    		completedEvents.add(event);
    		return;
    	} else {
    		// if not just display it
    		EventView eventView = new EventView(event);
    		EventController eventController = new EventController(eventView, mainView);
    		//eventControllers.add(eventController);
    		eventViews.add(eventView);
    	}
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
    
    public List<Component> fetchData() {
    	List<Component> combinedList = new ArrayList<>();
    	
    	// Add elements based on the filter
        switch (filterBy) {
            case TASKS:
                combinedList.addAll(taskViews);
                break;
            case EVENTS:
                combinedList.addAll(eventViews);
                break;
            case BOTH:
                combinedList.addAll(taskViews);
                combinedList.addAll(eventViews);
                break;
        }
        // the combinedComparator use the TaskComparator and EventComparator if the two component 
        // are both tasks or events. It use the more complex combined comparator if they are mixed.
        Collections.sort(combinedList, new ComponentComparators.CombinedComparator());
        
        return combinedList;
    	
    }
    
    

	public List<TaskView> getTaskViews() {
		return taskViews;
	}

	public List<EventView> getEventViews() {
		return eventViews;
	}
	
	public void setFilterBy(ViewOption filter) {
		filterBy = filter;
	}
    
}
