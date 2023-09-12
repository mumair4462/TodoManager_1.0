package com.mumairsaeed.dev.todomanager.HomeFragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mumairsaeed.dev.todomanager.Constants.Utils;
import com.mumairsaeed.dev.todomanager.Database.TodoDBHandler;
import com.mumairsaeed.dev.todomanager.MainActivity;
import com.mumairsaeed.dev.todomanager.Models.HabitSettingModel;
import com.mumairsaeed.dev.todomanager.R;

import java.util.ArrayList;

public class AddNewTaskFragment extends Fragment{

    Context context;

    ViewGroup ToastViewGroup;
    TodoDBHandler todoDatabase;

    AppCompatEditText habitTitle, habitDesc;

    AppCompatButton btnCencal, btnCreate;

    AppCompatTextView btnBackPress;
    Utils utilities;

    AppCompatTextView sunday, monday, tuesday, wednesday, thursday, friday, saturday;

    ArrayList<String> selectedDays = new ArrayList<>();


    public AddNewTaskFragment(Context context) {
        // Required empty public constructor
        this.context = context;
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_new_task, container, false);

        // find all ids
        ToastViewGroup = view.findViewById(R.id.toastContainer);
        todoDatabase = new TodoDBHandler(context);
        utilities = new Utils(context);
        monday = view.findViewById(R.id.txtMonday);
        tuesday = view.findViewById(R.id.txtTuesday);
        wednesday = view.findViewById(R.id.txtWednesday);
        thursday = view.findViewById(R.id.txtThursday);
        friday = view.findViewById(R.id.txtFriday);
        sunday = view.findViewById(R.id.txtSunday);
        saturday = view.findViewById(R.id.txtSaturday);
        btnCreate = view.findViewById(R.id.btnCreate);
        btnCencal = view.findViewById(R.id.btnCancel);
        habitTitle = view.findViewById(R.id.edtTitle);
        habitDesc = view.findViewById(R.id.edtDesc);


        btnBackPress = view.findViewById(R.id.btnBackPress);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewTask();
            }
        });

        btnBackPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utilities.onBackPress();
            }
        });

        btnCencal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utilities.onBackPress();
            }
        });

        monday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectFrequencyDay(monday);
            }
        });
        tuesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectFrequencyDay(tuesday);
            }
        });
        wednesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectFrequencyDay(wednesday);
            }
        });
        thursday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectFrequencyDay(thursday);
            }
        });
        friday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectFrequencyDay(friday);
            }
        });
        sunday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectFrequencyDay(sunday);
            }
        });
        saturday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectFrequencyDay(saturday);
            }
        });

        return view;
    }




    public void selectFrequencyDay(AppCompatTextView view){

        String day = view.getTag().toString();

        if(selectedDays.indexOf(day) == -1){

            view.setBackgroundResource(R.drawable.circle_selected);
            view.setTextColor(Color.WHITE);
            selectedDays.add(day);
        }else {
            view.setBackgroundResource(R.drawable.circle_no_selected);
            view.setTextColor(Color.GRAY);
            selectedDays.remove(day);
        }




    }


    // here new add new task code
    private void createNewTask() {


        String title = habitTitle.getText().toString().trim();
        String desc = habitDesc.getText().toString().trim();

        if(title.length() == 0 || desc.length() == 0 || selectedDays.isEmpty()){

            Toast toast = utilities.createToast("Filled Required Fields","All field are filled to create habit.",false,ToastViewGroup);
            toast.show();

        }else {

            todoDatabase.insertNewHabit(title,desc, selectedDays.toString());

            int lastID = todoDatabase.getLastSettingHabit();

            if(lastID != 0){
                HabitSettingModel model = todoDatabase.getSettingHabitByID(lastID);
                todoDatabase.insertSingleHabit(model);
            }

            Toast toast = utilities.createToast(utilities.TOAST_SUCCESS_TITLE,utilities.TOAST_SUCCESS_MSG,true,ToastViewGroup);
            toast.show();
            utilities.sleepAndGoBack(2);
        }

    }




}