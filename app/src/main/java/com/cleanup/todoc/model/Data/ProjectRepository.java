package com.cleanup.todoc.model.Data;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.List;

public class ProjectRepository {
    private ProjectDao mProjectDao;
    private LiveData<List<Project>> allProjects;

    public ProjectRepository(ProjectDao projectDao) {
        this.mProjectDao= projectDao;


    }
    public LiveData<List<Project>> getAllProjects() {
        return  this.mProjectDao.getAllProjects();
    }



    public void insertProject(Project project) {
        TodocDatabase.databaseWriteExecutor.execute(() -> {
            mProjectDao.insertProject(project);
        });
    }




}
