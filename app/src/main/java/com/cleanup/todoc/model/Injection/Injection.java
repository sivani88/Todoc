package com.cleanup.todoc.model.Injection;

import android.content.Context;

import com.cleanup.todoc.model.Data.ProjectRepository;
import com.cleanup.todoc.model.Data.TaskRepository;
import com.cleanup.todoc.model.Data.TodocDatabase;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Injection {
    public static TaskRepository provideTaskDataBase(Context context) {
        TodocDatabase database = TodocDatabase.getDatabase(context);
        return new TaskRepository(database.mTaskDao());
    }
    public static ProjectRepository provideProjectDataBase(Context context) {
        TodocDatabase database = TodocDatabase.getDatabase(context);
        return  new ProjectRepository(database.mProjectDao());
    }
    public static Executor provideExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    public static TodocFactory provideTodocFactory(Context context) {
        TaskRepository  mTaskDataRepository = provideTaskDataBase(context);
        ProjectRepository mProjectDataRepository= provideProjectDataBase(context);
        Executor executor = provideExecutor();
        return new TodocFactory(mTaskDataRepository, mProjectDataRepository, executor);
    }
}
