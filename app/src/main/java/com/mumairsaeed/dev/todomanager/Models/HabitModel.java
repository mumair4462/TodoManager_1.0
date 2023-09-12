package com.mumairsaeed.dev.todomanager.Models;

public class HabitModel {
    int id,streak, status, habitID;
    String title, desc, date;

    public HabitModel(int id,int habitID,String title, String desc, String date, int streak, int status) {
        this.id = id;
        this.habitID = habitID;
        this.streak = streak;
        this.status = status;
        this.title = title;
        this.desc = desc;
        this.date = date;
    }

    public void setHabitID(int habitID) {
        this.habitID = habitID;
    }

    public int getHabitID() {
        return habitID;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStreak(int streak) {
        this.streak = streak;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public int getStreak() {
        return streak;
    }

    public int getStatus() {
        return status;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getDate() {
        return date;
    }
}
