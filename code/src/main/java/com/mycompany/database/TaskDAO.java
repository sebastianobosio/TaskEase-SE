package com.mycompany.database;

import java.util.List;

import com.mycompany.taskmanager.model.Task;

public interface TaskDAO {
	int saveTask(Task task);
    List<Task> getAllTasks();
    void updateTask(Task task);
    Task getTaskById(int taskId);
    void deleteTask(int taskId);
}
