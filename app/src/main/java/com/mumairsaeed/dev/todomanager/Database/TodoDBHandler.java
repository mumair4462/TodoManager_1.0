package com.mumairsaeed.dev.todomanager.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.mumairsaeed.dev.todomanager.Constants.Utils;
import com.mumairsaeed.dev.todomanager.Models.HabitModel;
import com.mumairsaeed.dev.todomanager.Models.HabitSettingModel;

import java.util.ArrayList;

public class TodoDBHandler extends SQLiteOpenHelper {


    Context context;

    // Database Information
    private static final String DB_NAME = "TodoManager_Database";
    private static final int DB_VERSION = 1;

    // create habit table
    private static final String HABIT_TABLE  = "habit_table";
    private static final String HABIT_SETTING_TABLE  = "habit_setting_table";

    private static final String HABIT_KEY_ID = "id";
    private static final String HABIT_KEY_HABIT_ID = "habitID";
    private static final String HABIT_KEY_TITLE = "title";
    private static final String HABIT_KEY_DESC = "desc";
    private static final String HABIT_KEY_DOW_ARRAY = "dayOfWeak";
    private static final String HABIT_KEY_ACTIVE_DATE = "activeDate";
    private static final String HABIT_KEY_STREAK = "streak";
    private static final String HABIT_KEY_TERN = "tern";
    private static final String HABIT_KEY_IS_COMPLETED = "isComplete";
    private static final String HABIT_KEY_STATUS = "status";
    private static final String HABIT_KEY_IS_ACTIVE = "isActive";

    private static final String HABIT_KEY_TASK_DATE = "taskDate";



    public TodoDBHandler(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String create_habit_table = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT,%s INTEGER, %s TEXT,%s TEXT,%s TEXT,%s INTEGER,%s INTEGER);",
                HABIT_TABLE, HABIT_KEY_ID, HABIT_KEY_HABIT_ID,HABIT_KEY_TITLE, HABIT_KEY_DESC,HABIT_KEY_TASK_DATE,HABIT_KEY_STREAK,HABIT_KEY_STATUS);

        String create_habit_setting_table = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT,%s TEXT,%s TEXT,%s TEXT,%s TEXT,%s INTEGER);",
                HABIT_SETTING_TABLE, HABIT_KEY_ID, HABIT_KEY_TITLE, HABIT_KEY_DESC,HABIT_KEY_DOW_ARRAY, HABIT_KEY_ACTIVE_DATE, HABIT_KEY_IS_ACTIVE);


        db.execSQL(create_habit_table);
        db.execSQL(create_habit_setting_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS " + HABIT_SETTING_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + HABIT_TABLE);
        onCreate(db);
    }


    // Habit CRUD

    public void insertHabit(ArrayList<HabitSettingModel> modelArrayList){

        SQLiteDatabase db = this.getWritableDatabase();

        Utils utilities = new Utils(context);

        String date = utilities.databaseDateFormat(utilities.getCurrentDate());

        for (int i=0; i < modelArrayList.size(); i++){

            HabitSettingModel model = modelArrayList.get(i);

            ContentValues cv = new ContentValues();
            cv.put(HABIT_KEY_HABIT_ID, model.getId());
            cv.put(HABIT_KEY_TITLE, model.getTitle());
            cv.put(HABIT_KEY_DESC, model.getDesc());
            cv.put(HABIT_KEY_TASK_DATE, date);
            cv.put(HABIT_KEY_STREAK, getStreakByHabitID(model.getId()));
            cv.put(HABIT_KEY_STATUS, -1);

            db.insert(HABIT_TABLE,null,cv);

        }

    }

    public void insertSingleHabit(HabitSettingModel model){

        Utils utilities = new Utils(context);

        if(utilities.isDayExist(utilities.getWeekDay(),model.getDowArray())){
            SQLiteDatabase db = this.getWritableDatabase();
            String date = utilities.databaseDateFormat(utilities.getCurrentDate());

            ContentValues cv = new ContentValues();
            cv.put(HABIT_KEY_HABIT_ID, model.getId());
            cv.put(HABIT_KEY_TITLE, model.getTitle());
            cv.put(HABIT_KEY_DESC, model.getDesc());
            cv.put(HABIT_KEY_TASK_DATE, date);
            cv.put(HABIT_KEY_STREAK, getStreakByHabitID(model.getId()));
            cv.put(HABIT_KEY_STATUS, -1);

            db.insert(HABIT_TABLE,null,cv);
        }


    }

    public ArrayList<HabitModel> getHabitsByDate(String date){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + HABIT_TABLE + " WHERE " + HABIT_KEY_TASK_DATE + " = '" + date + "'";

        Cursor cursor = db.rawQuery(query, null);


        ArrayList<HabitModel> arrayList = new ArrayList<>();

        while (cursor.moveToNext()){

            int id = cursor.getInt(0);
            int habitID = cursor.getInt(1);
            String title = cursor.getString(2);
            String desc = cursor.getString(3);
            String taskDate = cursor.getString(4);
            int streak = cursor.getInt(5);
            int status = cursor.getInt(6);

            arrayList.add(new HabitModel(id,habitID,title, desc, taskDate,streak,status));

        }
        return arrayList;
    }


    public Boolean updateHabit(HabitModel model){

        if(model != null){

            try {
                SQLiteDatabase db = this.getWritableDatabase();

                ContentValues cv = new ContentValues();
                cv.put(HABIT_KEY_TITLE, model.getTitle());
                cv.put(HABIT_KEY_DESC, model.getDesc());
                cv.put(HABIT_KEY_STATUS, model.getStatus());
                cv.put(HABIT_KEY_STREAK, model.getStreak());

                db.update(HABIT_TABLE, cv, HABIT_KEY_ID + "= ?", new String[]{String.valueOf(model.getId())});
                return true;
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }

        }else {
            return false;
        }

    }


    public Boolean updateHabitByHabitID(HabitSettingModel model){

        if(model != null){

            try {
                Utils utils = new Utils(context);
                SQLiteDatabase db = this.getWritableDatabase();

                ContentValues cv = new ContentValues();

                cv.put(HABIT_KEY_TITLE, model.getTitle());
                cv.put(HABIT_KEY_DESC, model.getDesc());
                db.update(HABIT_TABLE, cv, HABIT_KEY_HABIT_ID + "= ?", new String[]{String.valueOf(model.getId())});

                if(utils.isDayExist(utils.getWeekDay(),model.getDowArray()) && model.getIsActive() == 1){

                    if(!isHabitIdExist(utils.databaseDateFormat(utils.getCurrentDate()), model.getId())){

                        insertSingleHabit(model);
                    }

                }else {

                    // Assuming you have a valid SQLite database instance named 'db'
                    String query = "DELETE FROM " + HABIT_TABLE + " WHERE " + HABIT_KEY_HABIT_ID + " = " + model.getId() + " AND " + HABIT_KEY_TASK_DATE + " = '" + utils.databaseDateFormat(utils.getCurrentDate()) + "'";
                    db.execSQL(query);

                   }


                return true;
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }

        }else {
            return false;
        }

    }

    public boolean isHabitIdExist(String date, int id){
        try {
            boolean status;
            SQLiteDatabase db = this.getReadableDatabase();
            String query = "SELECT * FROM " + HABIT_TABLE + " WHERE " + HABIT_KEY_TASK_DATE + " = '" + date + "' AND " + HABIT_KEY_HABIT_ID + " = " + id;
            Cursor cursor = db.rawQuery(query,null);

            if (cursor.moveToFirst()) {
                status =  true;
            }else {
                status = false;
            }

            return status;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    public Boolean deleteHabitByHabitID(int id){

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(HABIT_TABLE, HABIT_KEY_HABIT_ID + " = ?", new String[]{String.valueOf(id)});
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }




    // Habit Setting CRUD
    public void insertNewHabit(String title, String desc, String dowArray){

        SQLiteDatabase db = this.getWritableDatabase();

        Utils utilities = new Utils(context);

        ContentValues cv = new ContentValues();
        cv.put(HABIT_KEY_TITLE, title);
        cv.put(HABIT_KEY_DESC, desc);
        cv.put(HABIT_KEY_ACTIVE_DATE, utilities.databaseDateFormat(utilities.getCurrentDate()));
        cv.put(HABIT_KEY_IS_ACTIVE, 1);
        cv.put(HABIT_KEY_DOW_ARRAY, dowArray);

        db.insert(HABIT_SETTING_TABLE,null,cv);
        

    }

    public ArrayList<HabitSettingModel> getAllSettingHabit(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + HABIT_SETTING_TABLE, null);

        ArrayList<HabitSettingModel> arrayList = new ArrayList<>();
        while (cursor.moveToNext()){

            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String desc = cursor.getString(2);
            String dowArray = cursor.getString(3);
            String activeDate = cursor.getString(4);
            int isActive = cursor.getInt(5);

            arrayList.add(new HabitSettingModel(id,title,desc,dowArray,isActive, activeDate));


        }
        return arrayList;
    }

    public HabitSettingModel getSettingHabitByID(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM `"+HABIT_SETTING_TABLE+"` WHERE "+ HABIT_KEY_ID +" = " + id;

        Cursor cursor = db.rawQuery(query, null);

        HabitSettingModel model = null;

        while (cursor.moveToNext()){

            String title = cursor.getString(1);
            String desc = cursor.getString(2);
            String dowArray = cursor.getString(3);
            String activeDate = cursor.getString(4);
            int isActive = cursor.getInt(5);

            model = new HabitSettingModel(id,title,desc,dowArray,isActive, activeDate);


        }
        return model;
    }

    public Boolean deleteSettingHabit(int id){

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(HABIT_SETTING_TABLE, HABIT_KEY_ID + " = ?", new String[]{String.valueOf(id)});
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    public Boolean updateSettingHabit(HabitSettingModel model){

        if(model != null){


            try {
                SQLiteDatabase db = this.getWritableDatabase();

                ContentValues cv = new ContentValues();

                cv.put(HABIT_KEY_TITLE, model.getTitle());
                cv.put(HABIT_KEY_DESC, model.getDesc());
                cv.put(HABIT_KEY_IS_ACTIVE, model.getIsActive());
                cv.put(HABIT_KEY_DOW_ARRAY, model.getDowArray());

                db.update(HABIT_SETTING_TABLE, cv, HABIT_KEY_ID + "= ?", new String[]{String.valueOf(model.getId())});
                return true;
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }

        }else {
            return false;
        }

    }

    public ArrayList<HabitSettingModel> getSettingHabitByDay(String day){

        Utils utils = new Utils(context);

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + HABIT_SETTING_TABLE + " WHERE " + HABIT_KEY_IS_ACTIVE + " = 1", null);

        ArrayList<HabitSettingModel> arrayList = new ArrayList<>();
        while (cursor.moveToNext()){

            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String desc = cursor.getString(2);
            String dowArray = cursor.getString(3);
            String activeDate = cursor.getString(4);
            int isActive = cursor.getInt(5);

            if(utils.isDayExist(day,dowArray)){
                arrayList.add(new HabitSettingModel(id,title,desc,dowArray,isActive, activeDate));
            }

        }

        return arrayList;

    }

    public int getLastSettingHabit(){
        int id = 0;

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT MAX("+ HABIT_KEY_ID +") FROM " + HABIT_SETTING_TABLE;
        Cursor cursor = db.rawQuery(query,null);

        if (cursor.moveToFirst()) {
            id = cursor.getInt(0);

        }
        cursor.close();

        return id;
    }

    public int getStreakByHabitID(int id){


        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT "+ HABIT_KEY_STREAK +" FROM " + HABIT_TABLE + " WHERE " + HABIT_KEY_HABIT_ID + " = " + id;
        Cursor cursor = db.rawQuery(query,null);

        ArrayList<Integer> arrayList = new ArrayList<>();

        while (cursor.moveToNext()){
            int streak = cursor.getInt(0);
            arrayList.add(streak);
        }

        Log.d("my", "getStreakByHabitID: " + arrayList.toString());

        if(arrayList.isEmpty()){
            return 0;
        }else {
            return arrayList.get( (arrayList.size() -1) );
        }
    }


    // statistics
    public ArrayList<Integer> getAllHabitSettingIDs(){

        SQLiteDatabase db = this.getReadableDatabase();

        String query = "select " + HABIT_KEY_ID + " from " + HABIT_SETTING_TABLE +" where "+ HABIT_KEY_IS_ACTIVE + " = 1";
        Cursor cursor = db.rawQuery(query,null);

        ArrayList<Integer> arrayList = new ArrayList<>();

        while (cursor.moveToNext()){
            arrayList.add(cursor.getInt(0));
        }

        return arrayList;
    }

    public ArrayList<HabitModel> getHabitByHabitID(int id){

        ArrayList<HabitModel> arrayList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String query = "select * from " + HABIT_TABLE +" where "+ HABIT_KEY_HABIT_ID + " = " + id;

        Cursor cursor = db.rawQuery(query,null);

        while (cursor.moveToNext()){

            int habitID = cursor.getInt(1);
            String title = cursor.getString(2);
            String desc = cursor.getString(3);
            String taskDate = cursor.getString(4);
            int streak = cursor.getInt(5);
            int status = cursor.getInt(6);

            arrayList.add(new HabitModel(id,habitID,title, desc, taskDate,streak,status));

        }

        return arrayList;
    }

    public ArrayList<String> getMaxStreak(){

        ArrayList<String> streakArray = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select "+ HABIT_KEY_ID +", "+ HABIT_KEY_TITLE +", max("+HABIT_KEY_STREAK+") from " + HABIT_TABLE;
        Cursor cursor = db.rawQuery(query,null);

        if (cursor.moveToFirst()) {
            streakArray.add(String.valueOf(cursor.getInt(0)));
            streakArray.add(cursor.getString(1));
            streakArray.add(String.valueOf(cursor.getInt(2)));
        }
        cursor.close();

        return streakArray;
    }

    public ArrayList<String> getMinStreak(){

        ArrayList<String> streakArray = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select "+ HABIT_KEY_ID +", "+ HABIT_KEY_TITLE +", min("+HABIT_KEY_STREAK+") from " + HABIT_TABLE;
        Cursor cursor = db.rawQuery(query,null);

        if (cursor.moveToFirst()) {
            streakArray.add(String.valueOf(cursor.getInt(0)));
            streakArray.add(cursor.getString(1));
            streakArray.add(String.valueOf(cursor.getInt(2)));
        }
        cursor.close();

        return streakArray;
    }

    public ArrayList<String> calculateSuccessFailureRate(){

        int success = 0, failure = 0;

        ArrayList<HabitModel> arrayList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String query = "select * from " + HABIT_TABLE;

        Cursor cursor = db.rawQuery(query,null);

        while (cursor.moveToNext()){

            int id = cursor.getInt(0);
            int habitID = cursor.getInt(1);
            String title = cursor.getString(2);
            String desc = cursor.getString(3);
            String taskDate = cursor.getString(4);
            int streak = cursor.getInt(5);
            int status = cursor.getInt(6);



            arrayList.add(new HabitModel(id,habitID,title, desc, taskDate,streak,status));

            if(status == 1){
                success++;
            }else {
                failure++;
            }


        }

        cursor.close();


        // calculating

        int totalHabits = arrayList.size();
        int successRate = 0;
        int failureRate = 0;
        if(totalHabits != 0){
            successRate = (success * 100) / totalHabits;
            failureRate = (failure * 100) / totalHabits;
        }


        ArrayList<String> result = new ArrayList<>();
        result.add(String.valueOf(successRate) + "%");
        result.add(String.valueOf(failureRate) + "%");

        return result;

    }

}
