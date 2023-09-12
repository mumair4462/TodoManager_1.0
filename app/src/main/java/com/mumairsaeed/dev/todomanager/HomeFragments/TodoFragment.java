package com.mumairsaeed.dev.todomanager.HomeFragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.mumairsaeed.dev.todomanager.Adapters.TodoAdapRecycleView;
import com.mumairsaeed.dev.todomanager.Constants.Utils;
import com.mumairsaeed.dev.todomanager.Database.TodoDBHandler;
import com.mumairsaeed.dev.todomanager.Models.HabitModel;
import com.mumairsaeed.dev.todomanager.Models.HabitSettingModel;
import com.mumairsaeed.dev.todomanager.R;

import java.util.ArrayList;

public class TodoFragment extends Fragment implements TodoAdapRecycleView.MyFragmentListener{

    RecyclerView recyclerView;
    Context context;

    ArrayList<HabitModel> arrayList = new ArrayList<>();

    AppCompatTextView txtTodayDate, todayHabitTask, todayWOD, progressDesc;

    Utils utils;

    TodoDBHandler dbHandler;

    String todayDate;

    ProgressBar progressBar;

    AppCompatTextView errorText;

    ArrayList<HabitSettingModel> todayAllListOfArray;
    public TodoFragment(Context context) {
        // Required empty public constructor
        this.context = context;
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_todo, container, false);

        // find ids
        recyclerView = view.findViewById(R.id.todoList);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        utils = new Utils(context);
        dbHandler = new TodoDBHandler(context);
        txtTodayDate = view.findViewById(R.id.todayDate);
        progressBar = view.findViewById(R.id.porgressBar);
        todayWOD = view.findViewById(R.id.todoWOD);
        todayHabitTask = view.findViewById(R.id.todayHabitTask);
        progressDesc = view.findViewById(R.id.progressDesc);
        errorText = view.findViewById(R.id.txtEmpty);

        loadData();
        updatePorgressBar();

        todayWOD.setText(utils.getFullWeekDay());
        todayHabitTask.setText( arrayList.size() + " Task");

        txtTodayDate.setText(utils.calendarDateFormat(utils.getCurrentDate()));

        TodoAdapRecycleView adapRecycleView = new TodoAdapRecycleView(context, arrayList);
        recyclerView.setAdapter(adapRecycleView);
        adapRecycleView.setOnItemClickListener(this);


        return view;
    }

    public void updatePorgressBar(){

        int success = 0;
        int totalHabits = arrayList.size();
        for(int i=0;i<totalHabits;i++){
            HabitModel model = arrayList.get(i);
            if(model.getStatus() == 1){
                success++;
            }
        }

        int progressValue = 0;
        if(totalHabits != 0){
            progressValue = (success * 100) / totalHabits;
        }


        progressDesc.setText("Daily Progress "+success+"/"+totalHabits+" ("+progressValue+"%)");

        progressBar.setProgress(progressValue);


        if(arrayList.size() == 0){
            recyclerView.setVisibility(View.GONE);
            errorText.setVisibility(View.VISIBLE);
        }else {
            recyclerView.setVisibility(View.VISIBLE);
            errorText.setVisibility(View.GONE);
        }

    }
    private void loadData() {

        // upload today task
        todayDate = utils.databaseDateFormat(utils.getCurrentDate());

        arrayList = dbHandler.getHabitsByDate(todayDate);

        if(arrayList.isEmpty()){
            todayAllListOfArray = dbHandler.getSettingHabitByDay(utils.getWeekDay());
            dbHandler.insertHabit(todayAllListOfArray);
            arrayList = dbHandler.getHabitsByDate(todayDate);
        }

    }

    @Override
    public void onItemClick(int position) {
        updatePorgressBar();
    }
}