package com.mycompany.taskmanager.model;

import java.awt.Component;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;

import com.mycompany.taskmanager.view.EventView;
import com.mycompany.taskmanager.view.TaskView;

public class ComponentComparators {
	private ComponentComparators() {}

	// Comparator for tasks, i cannot use it directly because combinedList is made of <Component>.
    private static class TaskComparator implements Comparator<TaskView> {
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
    private static class EventComparator implements Comparator<EventView> {
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
    public static class CombinedComparator implements Comparator<Component> {
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
}

