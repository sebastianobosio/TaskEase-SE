package com.mycompany.taskmanager.model;

import java.awt.Color;
import java.awt.Component;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    	
    	// Add and sort elements based on the filter
        switch (filterBy) {
            case TASKS:
                combinedList.addAll(taskViews);
                // Sort only tasks
                Collections.sort(combinedList, new CombinedComparator()); // it will only use the TaskComparator
                break;
            case EVENTS:
                combinedList.addAll(eventViews);
                // Sort only events
                Collections.sort(combinedList, new CombinedComparator());
                break;
            case BOTH:
                combinedList.addAll(taskViews);
                combinedList.addAll(eventViews);
                // Sort both tasks and events together
                Collections.sort(combinedList, new CombinedComparator());
                break;
        }

        return combinedList;
    	
    }
    
    // Comparator for tasks, i cannot use it directly because combinedList is made of <Component>.
    private class TaskComparator implements Comparator<TaskView> {
        @Override
        public int compare(TaskView taskView1, TaskView taskView2) {
            int dateComparison = taskView1.getTask().getDueDate().compareTo(taskView2.getTask().getDueDate());
            if (dateComparison != 0) {
                return dateComparison;
            }
            return taskView1.getTask().getDueTime().compareTo(taskView2.getTask().getDueTime());
        }
    }
    
    // Comparator for events
    private class EventComparator implements Comparator<EventView> {
        @Override
        public int compare(EventView eventView1, EventView eventView2) {
            int dateComparison = eventView1.getEvent().getStartDate().compareTo(eventView2.getEvent().getStartDate());
            if (dateComparison != 0) {
                return dateComparison;
            }
            return eventView1.getEvent().getStartTime().compareTo(eventView2.getEvent().getStartTime());
        }
    }

    // Comparator for combined list (tasks and events)
    private class CombinedComparator implements Comparator<Component> {
        @Override
        public int compare(Component o1, Component o2) {
        	// If both are TaskViews, use TaskComparator
            if (o1 instanceof TaskView && o2 instanceof TaskView) {
                return new TaskComparator().compare((TaskView) o1, (TaskView) o2);
            }

            // If both are EventViews, use EventComparator
            if (o1 instanceof EventView && o2 instanceof EventView) {
                return new EventComparator().compare((EventView) o1, (EventView) o2);
            }

            // If one is TaskView and the other is EventView, compare based on start date/time
            LocalDate date1 = null;
            LocalTime time1 = null;
            LocalDate date2 = null;
            LocalTime time2 = null;

            if (o1 instanceof TaskView) {
                date1 = ((TaskView) o1).getTask().getDueDate();
                time1 = ((TaskView) o1).getTask().getDueTime();
            } else if (o1 instanceof EventView) {
                date1 = ((EventView) o1).getEvent().getStartDate();
                time1 = ((EventView) o1).getEvent().getStartTime();
            }

            if (o2 instanceof TaskView) {
                date2 = ((TaskView) o2).getTask().getDueDate();
                time2 = ((TaskView) o2).getTask().getDueTime();
            } else if (o2 instanceof EventView) {
                date2 = ((EventView) o2).getEvent().getStartDate();
                time2 = ((EventView) o2).getEvent().getStartTime();
            }

            // Compare dates first, then times if dates are equal
            int dateComparison = date1.compareTo(date2);
            if (dateComparison != 0) {
                return dateComparison;
            }
            return time1.compareTo(time2);
        }
    }        

	public List<TaskView> getTaskViews() {
		return taskViews;
	}

	public List<EventView> getEventViews() {
		return eventViews;
	}
	
	public void setFilterBy(ViewOption filter) {
		filterBy = filter;
		System.out.println("porco dio");
	}
    
}
