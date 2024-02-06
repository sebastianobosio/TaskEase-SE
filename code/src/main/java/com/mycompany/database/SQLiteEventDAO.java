package com.mycompany.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.mycompany.taskmanager.model.Event;

public class SQLiteEventDAO implements EventDAO {
private Connection connection;
	
	public SQLiteEventDAO() {
        try {
            // Establish a connection to the SQLite database
            connection = DriverManager.getConnection(SQLiteDB.DB_URL);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any database-related exceptions here
        }
    }

	@Override
	public int saveEvent(Event event) {
		String sql = "INSERT INTO events (name, description, location, start_date, start_time, end_date, end_time) VALUES (?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
		    // Set values for prepared statement parameters
		    statement.setString(1, event.getTitle());
		    statement.setString(2, event.getDescription());
		    statement.setString(3, event.getLocation());
		    statement.setString(4, event.getFormattedStartDate());
		    statement.setString(5, event.getFormattedStartTime());
		    statement.setString(6, event.getFormattedEndDate());
		    statement.setString(7, event.getFormattedEndTime());
		
		    // Execute the SQL INSERT query
		    statement.executeUpdate();
		    System.out.println("Event inserted successfully.");
		    
		    // Retrieve the generated ID
		    ResultSet generatedKeys = statement.getGeneratedKeys();
		    if (generatedKeys.next()) {
		        return generatedKeys.getInt(1); // Return the generated ID
		    } else {
		        throw new SQLException("Failed to retrieve the auto-generated ID.");
		    }
		} catch (SQLException e) {
			System.out.println("i'm here");
		    e.printStackTrace();
		    // Handle any database-related exceptions here
		    return -1; // Return a default value to indicate failure
		    }
	}

	@Override
	public List<Event> getAllEvents() {
		List<Event> events = new ArrayList<>();
        String sql = "SELECT * FROM events";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                String location = resultSet.getString("location");
                LocalDate startDate = LocalDate.parse(resultSet.getString("start_date"), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                LocalTime startTime = LocalTime.parse(resultSet.getString("start_time"), DateTimeFormatter.ofPattern("HH:mm"));
                LocalDate endDate = LocalDate.parse(resultSet.getString("end_date"), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                LocalTime endTime = LocalTime.parse(resultSet.getString("end_time"), DateTimeFormatter.ofPattern("HH:mm"));
                
                events.add(new Event(id, name, description, location,  startDate, startTime, endDate, endTime));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return events;
	}

	@Override
	public void updateEvent(Event event) {
		String sql = "UPDATE events SET name=?, description=?, location=?, start_date=?, start_time=?, end_date=?, end_time=? WHERE id=?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
        	statement.setString(1, event.getTitle());
            statement.setString(2, event.getDescription());
            statement.setString(3, event.getLocation());
            statement.setString(4, event.getFormattedStartDate());
            statement.setString(5, event.getFormattedStartTime());
            statement.setString(6, event.getFormattedEndDate());
            statement.setString(7, event.getFormattedEndTime());
            statement.setInt(8, event.getId()); // Set the id parameter for the WHERE clause

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Event updated successfully.");
            } else {
                System.out.println("No event found with id: " + event.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any database-related exceptions here
        }
	}
	
	@Override
	public Event getEventById(int eventId) {
		String sql = "SELECT * FROM events WHERE id = ?";
        Event event = null;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, eventId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                	String name = resultSet.getString("name");
                    String description = resultSet.getString("description");
                    String location = resultSet.getString("location");
                    LocalDate startDate = LocalDate.parse(resultSet.getString("start_date"), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                    LocalTime startTime = LocalTime.parse(resultSet.getString("start_time"), DateTimeFormatter.ofPattern("HH:mm"));
                    LocalDate endDate = LocalDate.parse(resultSet.getString("end_date"), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                    LocalTime endTime = LocalTime.parse(resultSet.getString("end_time"), DateTimeFormatter.ofPattern("HH:mm"));
                    
                    // Create a Event object with retrieved data
                    event = new Event(eventId, name, description, location,  startDate, startTime, endDate, endTime);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any database-related exceptions here
        }

        return event;
	}
	
	@Override
	public void deleteEvent(int eventId) {
		String sql = "DELETE FROM events WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, eventId);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Task with ID " + eventId + " deleted successfully.");
            } else {
                System.out.println("No task found with ID " + eventId + ".");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any database-related exceptions here
        }
	}
}
