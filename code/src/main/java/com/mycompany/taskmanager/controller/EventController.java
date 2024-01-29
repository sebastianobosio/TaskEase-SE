package com.mycompany.taskmanager.controller;

import com.mycompany.taskmanager.view.EventView;
import com.mycompany.taskmanager.model.Event;
import com.mycompany.taskmanager.view.DetailedEventView;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

public class EventController {
	private EventView eventView;

    public EventController(EventView eventView) {
        this.eventView = eventView;
        
        eventView.getEventTitle().addMouseListener(new EventViewMouseListener());
    }
    
    private class EventViewMouseListener extends MouseAdapter {
    	public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 1) { // Single-click
                handleEventClick();
            }
    	}		
    }
    
    public void handleEventClick() {
        Event event = eventView.getEvent(); // Retrieve the task from the TaskView
        if (event != null) {
            // Open a new window or dialog to display detailed information about the task
            DetailedEventView detailedEventView = new DetailedEventView(event);
            detailedEventView.setVisible(true);
        }
    }
}
