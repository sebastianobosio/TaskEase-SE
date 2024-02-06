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

import com.mycompany.taskmanager.model.Task;
import com.mycompany.taskmanager.model.Task.TaskStatus;

public class SQLiteTaskDAO implements TaskDAO{

	private Connection connection;
	
	public SQLiteTaskDAO() {
        try {
            // Establish a connection to the SQLite database
            connection = DriverManager.getConnection(SQLiteDB.DB_URL);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any database-related exceptions here
        }
    }
	
	@Override
	public int saveTask(Task task) {
		String sql = "INSERT INTO tasks (name, description, due_date, due_time, status) VALUES (?, ?, ?, ?, ?)";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
            // Set values for prepared statement parameters
            statement.setString(1, task.getTitle());
            statement.setString(2, task.getDescription());
            statement.setString(3, task.getFormattedDueDate());
            statement.setString(4, task.getFormattedDueTime());
            statement.setString(5, task.getStatus());

            // Execute the SQL INSERT query
            statement.executeUpdate();
            System.out.println("Task inserted successfully.");
            
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
	public List<Task> getAllTasks() {
		List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                LocalDate dueDate = LocalDate.parse(resultSet.getString("due_date"), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                LocalTime dueTime = LocalTime.parse(resultSet.getString("due_time"), DateTimeFormatter.ofPattern("HH:mm"));
                String statusAsString = resultSet.getString("status");
                System.out.println(statusAsString);
                TaskStatus status = null;
        		if (statusAsString.equals("Completed")) {
        			status = TaskStatus.COMPLETED;
        	    } else if (statusAsString.equals("Not Started")) {
        	    	System.out.println("i'm here");
        	    	status = TaskStatus.NOTSTARTED;
        	    } else if (statusAsString.equals("In Progress")) {
        	    	status = TaskStatus.ONGOING;
        	    }
                tasks.add(new Task(id, name, description, dueDate, dueTime, status));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tasks;
	}
	
	 public void updateTask(Task task) {
	        String sql = "UPDATE tasks SET name=?, description=?, due_date=?, due_time=?, status=? WHERE id=?";

	        try (PreparedStatement statement = connection.prepareStatement(sql)) {
	            statement.setString(1, task.getTitle());
	            statement.setString(2, task.getDescription());
	            statement.setString(3, task.getFormattedDueDate());
	            statement.setString(4, task.getFormattedDueTime());
	            statement.setString(5, task.getStatus());
	            statement.setInt(6, task.getId()); // Set the id parameter for the WHERE clause

	            int rowsUpdated = statement.executeUpdate();
	            if (rowsUpdated > 0) {
	                System.out.println("Task updated successfully.");
	            } else {
	                System.out.println("No task found with id: " + task.getId());
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            // Handle any database-related exceptions here
	        }
	 }
	 
	 // return a task given an ID as input
	 @Override
	 public Task getTaskById(int taskId) {
	        String sql = "SELECT * FROM tasks WHERE id = ?";
	        Task task = null;

	        try (PreparedStatement statement = connection.prepareStatement(sql)) {
	            statement.setInt(1, taskId);

	            try (ResultSet resultSet = statement.executeQuery()) {
	                if (resultSet.next()) {
	                	String name = resultSet.getString("name");
	                    String description = resultSet.getString("description");
	                    LocalDate dueDate = LocalDate.parse(resultSet.getString("due_date"), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
	                    LocalTime dueTime = LocalTime.parse(resultSet.getString("due_time"), DateTimeFormatter.ofPattern("HH:mm"));
	                    String statusAsString = resultSet.getString("status");
	                    System.out.println(statusAsString);
	                    TaskStatus status = null;
	            		if (statusAsString.equals("Completed")) {
	            			status = TaskStatus.COMPLETED;
	            	    } else if (statusAsString.equals("Not Started")) {
	            	    	System.out.println("i'm here");
	            	    	status = TaskStatus.NOTSTARTED;
	            	    } else if (statusAsString.equals("In Progress")) {
	            	    	status = TaskStatus.ONGOING;
	            	    }
	                    // Create a Task object with retrieved data
	                    task = new Task(taskId, name, description, dueDate, dueTime, status);
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            // Handle any database-related exceptions here
	        }

	        return task;
	 }
	 
	 @Override
	 public void deleteTask(int taskId) {
		 	String sql = "DELETE FROM tasks WHERE id = ?";

	        try (PreparedStatement statement = connection.prepareStatement(sql)) {
	            statement.setInt(1, taskId);

	            int rowsDeleted = statement.executeUpdate();
	            if (rowsDeleted > 0) {
	                System.out.println("Task with ID " + taskId + " deleted successfully.");
	            } else {
	                System.out.println("No task found with ID " + taskId + ".");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            // Handle any database-related exceptions here
	        }
	 }
}
