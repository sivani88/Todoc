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

    public ProjectRepository(Application application) {
        TodocDatabase db = Room.databaseBuilder(application.getApplicationContext(), TodocDatabase.class, "Task_Database").build();
        ProjectDao mDao = db.mProjectDao();
        List<Project> projects = (List<Project>) mDao.getProject();


    }



    public void insertProject(Project project) {
        TodocDatabase.databaseWriteExecutor.execute(() -> {
            mProjectDao.insertProject(project);
        });
    }




}
