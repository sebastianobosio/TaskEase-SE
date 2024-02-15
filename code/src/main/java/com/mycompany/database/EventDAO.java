package com.mycompany.database;

import java.util.List;

import com.mycompany.taskmanager.model.Event;

public interface EventDAO {
	int saveEvent(Event event);
    List<Event> getAllEvents();
    void updateEvent(Event event);
    Event getEventById(int eventId);
    void deleteEvent(int eventId);
}
