package com.mumairsaeed.dev.todomanager.Models;

public class TaskModel {
    int id, status;
    String taskTitle, taskDesc;

    public TaskModel(int id, String taskTitle, String taskDesc, int status) {
        this.id = id;
        this.status = status;
        this.taskTitle = taskTitle;
        this.taskDesc = taskDesc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }
    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }
    public int getStatus() {
        return status;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public String getTaskDesc() {
        return taskDesc;
    }
}
