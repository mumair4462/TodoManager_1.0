package com.mumairsaeed.dev.todomanager.Constants;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePerferenceManager {
    private SharedPreferences sharedPreferences;

    public SharePerferenceManager(Context context) {
        this.sharedPreferences = context.getSharedPreferences( "MyPer", Context.MODE_PRIVATE);
    }
    // Method to store a string in SharedPreferences
    public void saveData(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }
    // Method to retrieve a string from SharedPreferences
    public String getData(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    public boolean isExist(String key, String defaultValue) {
        return Boolean.parseBoolean(sharedPreferences.getString(key, defaultValue));
    }


}
