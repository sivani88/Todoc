package com.cleanup.todoc.model;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Comparator;


@Entity(foreignKeys = @ForeignKey(entity = Project.class, parentColumns = "project_id", childColumns = "project_id"))
public class Task {

    @PrimaryKey(autoGenerate = true)
    private long id;


    @ColumnInfo(name = "project_id")
    private long projectId;

    @ColumnInfo(name = "name_task")
    @NonNull
    private String name;


    /*The timestamp when the task has been created
     */
    @ColumnInfo(name = "TimeStamp")
    private long creationTimestamp;

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }


    public Task(Long projectId, @NonNull String name, long creationTimestamp) {
        this.setProjectId(projectId);
        this.setName(name);
        this.setCreationTimestamp(creationTimestamp);
    }

    public long getCreationTimestamp() {
        return creationTimestamp;
    }

    public long getId() {
        return id;
    }



    private void setProjectId(Long projectId) {
        this.projectId = projectId;
    }


    public Project getProject() {
        return Project.getProjectById(projectId);
    }


    @NonNull
    public String getName() {
        return name;
    }


    private void setName(@NonNull String name) {
        this.name = name;
    }

    private void setCreationTimestamp(long creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public void setId(long id) {
        this.id = id;
    }

    /**
     * Comparator to sort task from A to Z
     */
    public static class TaskAZComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) {
            return left.name.compareTo(right.name);
        }
    }

    /**
     * Comparator to sort task from Z to A
     */
    public static class TaskZAComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) {
            return right.name.compareTo(left.name);
        }
    }

    /**
     * Comparator to sort task from last created to first created
     */
    public static class TaskRecentComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) {
            return (int) (right.creationTimestamp - left.creationTimestamp);
        }
    }

    /**
     * Comparator to sort task from first created to last created
     */
    public static class TaskOldComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) {
            return (int) (left.creationTimestamp - right.creationTimestamp);
        }
    }
}
