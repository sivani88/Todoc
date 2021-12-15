package com.cleanup.todoc.ui;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.model.Data.ProjectRepository;
import com.cleanup.todoc.model.Data.TaskRepository;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.List;
import java.util.concurrent.Executor;

public class TaskViewModel extends ViewModel {
    private final TaskRepository mTaskRepository;
    private final ProjectRepository mProjectRepository;
    private final Executor executor;

    @Nullable
    private LiveData<List<Project>> projects;

    public TaskViewModel(TaskRepository taskRepository, ProjectRepository projectRepository, Executor executor) {
        mTaskRepository = taskRepository;
        mProjectRepository = projectRepository;
        this.executor = executor;
    }

    public void init() {
        if (this.projects != null) {
            return;
        }
        this.projects = mProjectRepository.getAllProjects();

    }

    public LiveData<List<Project>> getAllProjects() {
        return this.projects;
    }

    public LiveData<List<Task>> getAllTasks() {
        return mTaskRepository.getAllTasks();
    }

    public void insert(Task task) {
        executor.execute(() -> {
            mTaskRepository.insertTask(task);
        });
    }

    public void delete(Task task) {
        executor.execute(() -> {
            mTaskRepository.deleteTask(task);
        });
    }
}
