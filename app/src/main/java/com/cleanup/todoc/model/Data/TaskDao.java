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
    void deleteTask(LiveData<List<Task>> task);

    @Query("DELETE FROM task_table")
    void deleteAllTasks();

    @Query("SELECT * FROM task_table ORDER BY `name_task`")
    LiveData<List<Task>> getAllTasksByName();

    @Query("SELECT * FROM task_table ORDER BY `project_id`")
    LiveData<List<Task>> getAllTasksByProject(long projectId);

    @Query("SELECT * FROM task_table ORDER BY 'TimeStamp'")
    LiveData<List<Task>> getAllTasksByDate();

    @Query("SELECT * FROM task_table")
    LiveData<List<Task>> getAllTasks();




}
