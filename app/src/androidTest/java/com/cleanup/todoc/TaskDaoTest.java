package com.cleanup.todoc;

import androidx.test.runner.AndroidJunit4;

import org.junit.runner.RunWith;

@RunWith(AndroidJunit4.class)
public class TaskDaoTest {
    private TodocDatabase database;

    private static long PROJECT_ID = 1L;
    private static long PROJECT_ID_2 = 2L;
    private Project[] PROJECTS = Project.getAllProjects();
    private static Task NEW_TASK_ONE = new Task(PROJECT_ID,"Clean the floor", new Date().getTime());
    private static Task NEW_TASK_TWO = new Task(PROJECT_ID_2,"Clean the toilette", new Date().getTime());
    private static Task NEW_TASK_THREE = new Task(PROJECT_ID,"Clean the window ", new Date().getTime());

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
        for(Project project : PROJECTS) {
            this.database.projectDao().insert(project);
        }
        List<Project> projects = LiveDataTestUtil.getValue(this.database.projectDao().getAllProjects());

        assertEquals(3, projects.size());
        assertTrue(projects.get(0).getName().equals(PROJECTS[0].getName()) && projects.get(0).getId() == PROJECTS[0].getId());
        assertTrue(projects.get(1).getName().equals(PROJECTS[1].getName()) && projects.get(1).getId() == PROJECTS[1].getId());
        assertTrue(projects.get(2).getName().equals(PROJECTS[2].getName()) && projects.get(2).getId() == PROJECTS[2].getId());
    }

    @Test
    public void getTasksWhenNoTaskInserted() throws InterruptedException {
        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getAllTasks());
        assertTrue(tasks.isEmpty());
    }

    @Test
    public void insertAndGetTasks() throws InterruptedException {
        for(Project project : PROJECTS) {
            this.database.projectDao().insert(project);
        }

        this.database.taskDao().insertTask(NEW_TASK_ONE);
        this.database.taskDao().insertTask(NEW_TASK_TWO);
        this.database.taskDao().insertTask(NEW_TASK_THREE);

        List<Task> tasks = LiveDataTestUtil.getValue(this.database.mTaskDao().getAllTasks());
        assertEquals(3, tasks.size());
    }

    @Test
    public void insertAndDeleteTask() throws InterruptedException {
        for(Project project : PROJECTS) {
            this.database.projectDao().insertProject(project);
        }

        this.database.taskDao().insert(NEW_TASK_ONE);
        Task taskAdded = LiveDataTestUtil.getValue(this.database.taskDao().getAllTasks()).get(0);
        this.database.taskDao().deletTask(taskAdded);

        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getAllTasks());
        assertTrue(tasks.isEmpty());
    }
}
