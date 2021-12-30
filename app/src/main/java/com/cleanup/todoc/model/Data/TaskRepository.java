package com.cleanup.todoc.model.Data;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.model.Task;

import java.util.List;

public class TaskRepository {
    private TaskDao mTaskDao;
    private LiveData<List<Task>> allTasks;

    public TaskRepository(TaskDao taskDao) {
        this.mTaskDao = taskDao;


    }

    public LiveData<List<Task>> getAllTasks() {
        return this.mTaskDao.getAllTasks();

    }

    public void insertTask(Task task) {
        TodocDatabase.databaseWriteExecutor.execute(() -> {
            mTaskDao.insertTask(task);
        });
    }

    public void updateTask(Task task) {
        TodocDatabase.databaseWriteExecutor.execute(() -> {
            mTaskDao.insertTask(task);
        });

    }

    public void deleteTask(Task task) {
        TodocDatabase.databaseWriteExecutor.execute(() -> {
            mTaskDao.deleteTask(task);
        });


    }

    public void deleteAllTasks(LiveData<List<Task>> allTasks) {
        mTaskDao.deleteAllTasks();


    }
}
