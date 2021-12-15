package com.cleanup.todoc.model.Data;

import android.content.Context;
import android.content.res.Resources;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Task.class, Project.class}, version = 1)
public abstract class TodocDatabase extends RoomDatabase {

    public abstract ProjectDao mProjectDao();
    public  abstract TaskDao mTaskDao();
    public static TodocDatabase mInstance;
    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static TodocDatabase getDatabase(final Context context){
        if (mInstance == null){
            synchronized (TodocDatabase.class) {
                if(mInstance == null) {
                    mInstance= Room.databaseBuilder(context.getApplicationContext(),
                            TodocDatabase.class, "Task_Database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }return mInstance;
    }
    private static  RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                TaskDao dao = mInstance.mTaskDao();
                ProjectDao mProjectDao = mInstance.mProjectDao();
                Project[] projects = Project.getAllProjects();
                for (Project project : projects) {
                    mProjectDao.insertProject(project);
                }

                dao.deleteAllTasks();
                dao.getAllTasksByDate();
                dao.getAllTasksByName();
                //TODO a verifier

                Task task = new Task(1L, "Netoyage des vitres", 11/11/2021);
                task = new Task(2L, "Entretient des pelouses", 28/11/2021);
                dao.insertTask(task);


            });
        }
    };
}
