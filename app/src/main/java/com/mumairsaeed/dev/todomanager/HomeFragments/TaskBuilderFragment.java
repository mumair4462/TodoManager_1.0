package com.mumairsaeed.dev.todomanager.HomeFragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mumairsaeed.dev.todomanager.Adapters.TaskBuilderAdapRecycleView;
import com.mumairsaeed.dev.todomanager.Constants.Utils;
import com.mumairsaeed.dev.todomanager.Database.TodoDBHandler;
import com.mumairsaeed.dev.todomanager.Models.HabitSettingModel;
import com.mumairsaeed.dev.todomanager.R;

import java.util.ArrayList;


public class TaskBuilderFragment extends Fragment {

    RecyclerView taskList;
    Context context;

    TodoDBHandler todoDBHandler;

    Utils utilities;

    FloatingActionButton addNewTask;

    ArrayList<HabitSettingModel> arrayList = new ArrayList<>();

    public TaskBuilderFragment(Context context) {
        this.context = context;
        // Required empty public constructor
    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_task_builder, container, false);

        AppCompatTextView emptyMsg = view.findViewById(R.id.txtEmpty);
        LinearLayoutCompat mainLayout = view.findViewById(R.id.layoutMain);

        addNewTask = view.findViewById(R.id.btnNewTask1);
        utilities = new Utils(context);
        taskList = view.findViewById(R.id.taskBuilderList);
        taskList.setLayoutManager(new LinearLayoutManager(context));


        todoDBHandler = new TodoDBHandler(context);
        arrayList = todoDBHandler.getAllSettingHabit();


        if(arrayList.isEmpty()){
            emptyMsg.setVisibility(View.VISIBLE);
            mainLayout.setVisibility(View.GONE);
        }else {
            emptyMsg.setVisibility(View.GONE);
            mainLayout.setVisibility(View.VISIBLE);
        }

        // set recycle view
        TaskBuilderAdapRecycleView adapRecycleView = new TaskBuilderAdapRecycleView(context, arrayList);
        taskList.setAdapter(adapRecycleView);

        addNewTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utilities.loadMainFragment(new AddNewTaskFragment(context), utilities.HOME_FRAGMENT_FRAME, false, true);
            }
        });


        return view;
    }

}