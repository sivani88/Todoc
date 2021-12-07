package com.cleanup.todoc.model.Data;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.cleanup.todoc.model.Task;

import java.util.List;

public class TaskRepository {
    private TaskDao mTaskDao;
    private LiveData<List<Task>> allTasks;

    public TaskRepository(Application application) {
        TodocDatabase db = Room.databaseBuilder(application.getApplicationContext(), TodocDatabase.class, "Task_Database").build();
        TaskDao taskDao = db.mTaskDao();
        List<Task> tasks = (List<Task>) taskDao.getAllTasks();


    }

    LiveData<List<Task>> getAllTasks() {
        return allTasks;

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

    public void deleteTask(LiveData<List<Task>> task) {
        TodocDatabase.databaseWriteExecutor.execute(() -> {
            mTaskDao.deleteTask(task);
        });


    }

    public void deleteAllTasks(LiveData<List<Task>> allTasks) {
        mTaskDao.deleteAllTasks();

        //TODO peut etre faire task trier par nom et date
    }
}
