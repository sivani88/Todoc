package com.cleanup.todoc.model.Data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.cleanup.todoc.model.Project;

import java.util.List;
@Dao
public interface ProjectDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProject(Project project);

    @Query(value = "SELECT * FROM project_table WHERE project_id = :projectId ")
   LiveData<Project> getProject(long projectId);
    //TODO a verifier


    @Query("SELECT * FROM project_table")
    LiveData<List<Project>> getProject();


}
