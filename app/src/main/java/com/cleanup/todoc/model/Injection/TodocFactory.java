package com.cleanup.todoc.model.Injection;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cleanup.todoc.model.Data.ProjectRepository;
import com.cleanup.todoc.model.Data.TaskRepository;
import com.cleanup.todoc.ui.TaskViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executor;

public class TodocFactory implements ViewModelProvider.Factory {

    private final TaskRepository mTaskRepository;
    private final ProjectRepository mProjectRepository;
    private final Executor mExecutor;

    public TodocFactory(TaskRepository taskRepository, ProjectRepository projectRepository, Executor executor) {
        mTaskRepository = taskRepository;
        mProjectRepository = projectRepository;
        mExecutor = executor;
    }

    @NotNull
    @Override
    public <T extends ViewModel> T create(@NotNull Class<T> modelClass) {

        if (modelClass.isAssignableFrom(TaskViewModel.class)) {
            return (T) new TaskViewModel(mTaskRepository, mProjectRepository, mExecutor);
        }

        throw new IllegalArgumentException("No viewModelClass");
    }
}
