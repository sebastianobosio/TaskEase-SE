package com.mycompany.taskmanager.view;

import com.mycompany.taskmanager.model.Event;

import javax.swing.*;
import java.awt.*;

public class EventView extends JPanel {
    private JLabel eventTitleLabel;
    private JLabel eventDescriptionLabel;
    private JLabel eventDateLabel;
    private JLabel eventTimeLabel;
    private JLabel eventLocationLabel;

    public EventView(Event event) {
        // Initialize components
        eventTitleLabel = new JLabel(event.getTitle());
        eventDescriptionLabel = new JLabel(event.getDescription());
        eventDateLabel = new JLabel("Date-Time: " + event.getStartDate() + " to " + event.getEndDate());
        eventTimeLabel = new JLabel("Date-Time: " + event.getStartTime() + " to " + event.getEndTime());
        eventLocationLabel = new JLabel("Location: " + event.getLocation());

        // Set up layout
        setLayout(new BorderLayout());
        add(eventTitleLabel, BorderLayout.NORTH);
        add(eventDescriptionLabel, BorderLayout.CENTER);
        add(eventDateLabel, BorderLayout.SOUTH);
        add(eventTimeLabel, BorderLayout.SOUTH);
        add(eventLocationLabel, BorderLayout.SOUTH);
        
        setPreferredSize(new Dimension(300, 100));
    }
}