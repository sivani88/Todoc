package com.cleanup.todoc.model.Data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.cleanup.todoc.model.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert
    void insertTask(Task task);

    @Update
    void UpdateTask(Task task);

    @Delete
    void deleteTask(Task task);

    @Query("DELETE FROM Task")
    void deleteAllTasks();

    @Query("SELECT * FROM Task ORDER BY `name_task`")
    LiveData<List<Task>> getAllTasksByName();

    @Query("SELECT * FROM Task ORDER BY 'project_id'")
    LiveData<List<Task>> getAllTasksByProject();

    @Query("SELECT * FROM Task ORDER BY 'TimeStamp'")
    LiveData<List<Task>> getAllTasksByDate();

    @Query("SELECT * FROM Task")
    LiveData<List<Task>> getAllTasks();




}
