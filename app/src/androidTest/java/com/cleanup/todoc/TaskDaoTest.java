package com.cleanup.todoc;


import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.platform.app.InstrumentationRegistry;

import com.cleanup.todoc.model.Data.TodocDatabase;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4ClassRunner.class)
public class TaskDaoTest {
    private TodocDatabase database;

    private static long PROJECT_ID = 1L;
    private static long PROJECT_ID_2 = 2L;
    private static long PROJECT_ID_3 = 3L;
    private Project[] PROJECTS = Project.getAllProjects();
    private static Task NEW_TASK_ONE = new Task(PROJECT_ID, "Clean the floor", new Date().getTime());
    private static Task NEW_TASK_TWO = new Task(PROJECT_ID_2, "Clean the toilette", new Date().getTime());
    private static Task NEW_TASK_THREE = new Task(PROJECT_ID_3, "Clean the window ", new Date().getTime());
    private int ITEMS_COUNT;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();


    @Before
    public void initDatabase() {
        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().getContext(), TodocDatabase.class)
                .allowMainThreadQueries()
                .build();

    }

    @After
    public void closeDatabase() {
        this.database.close();
    }

    @Test
    public void insertAndGetAllProjects() throws InterruptedException {
        int initialSize = LivedataTestUtils.getValue(this.database.mProjectDao().getAllProjects()).size();
        assertEquals(0, initialSize);
        for (Project project : PROJECTS) {
            this.database.mProjectDao().insertProject(project);
            // database vide initialise j'insere les projets  dans la dataBase via le dao
        }
        List<Project> projects = LivedataTestUtils.getValue(this.database.mProjectDao().getAllProjects());
          //je recupere les projets qu'il y a  dans la base de données et je teste qu'il y a les bonnes valeurs
        assertEquals(initialSize + 3, projects.size());
        assertTrue(projects.get(initialSize + 0).getName().equals(PROJECTS[0].getName()) && projects.get(initialSize +0 ).getId() == PROJECTS[0].getId());
        assertTrue(projects.get(initialSize + 1).getName().equals(PROJECTS[1].getName()) && projects.get(initialSize+ 1).getId() == PROJECTS[1].getId());
        assertTrue(projects.get(initialSize +2 ).getName().equals(PROJECTS[2].getName()) && projects.get(initialSize+ 2).getId() == PROJECTS[2].getId());
    }



    @Test
    public void insertAndGetTasks() throws InterruptedException {
        for (Project project : PROJECTS) {
            this.database.mProjectDao().insertProject(project);
        }
        int nbTasks = LivedataTestUtils.getValue(this.database.mTaskDao().getAllTasks()).size();
        this.database.mTaskDao().insertTask(NEW_TASK_ONE);
        this.database.mTaskDao().insertTask(NEW_TASK_TWO);
        this.database.mTaskDao().insertTask(NEW_TASK_THREE);

        List<Task> tasks = LivedataTestUtils.getValue(this.database.mTaskDao().getAllTasks());
        assertEquals(nbTasks + 3, tasks.size());
    }

    @Test
    public void insertAndDeleteTask() throws InterruptedException {
        for (Project project : PROJECTS) {
            this.database.mProjectDao().insertProject(project);
        }
        int nbTasks = LivedataTestUtils.getValue(this.database.mTaskDao().getAllTasks()).size();
        // j ajoute  une task new task one et je vérifie que elle est bien supprimée  de la base de données
        NEW_TASK_ONE.setId(this.database.mTaskDao().insertTask(NEW_TASK_ONE));
        assertEquals(nbTasks+1, LivedataTestUtils.getValue(this.database.mTaskDao().getAllTasks()).size());
        assertEquals(1, this.database.mTaskDao().deleteTask(NEW_TASK_ONE));
        assertEquals(nbTasks, LivedataTestUtils.getValue(this.database.mTaskDao().getAllTasks()).size());
    }
}
