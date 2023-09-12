package com.mumairsaeed.dev.todomanager.Models;

public class HabitSettingModel {
    int id, isActive;
    String title, desc, activeDate, dowArray;

    public HabitSettingModel(int id,String title, String desc,String dowArray, int isActive, String activeDate) {
        this.id = id;
        this.isActive = isActive;
        this.title = title;
        this.desc = desc;
        this.activeDate = activeDate;
        this.dowArray = dowArray;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setActiveDate(String activeDate) {
        this.activeDate = activeDate;
    }

    public void setDowArray(String dowArray) {
        this.dowArray = dowArray;
    }

    public int getId() {
        return id;
    }

    public int getIsActive() {
        return isActive;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getActiveDate() {
        return activeDate;
    }

    public String getDowArray() {
        return dowArray;
    }
}
