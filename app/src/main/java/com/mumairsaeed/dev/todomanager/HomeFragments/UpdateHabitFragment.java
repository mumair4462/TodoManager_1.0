package com.mumairsaeed.dev.todomanager.HomeFragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mumairsaeed.dev.todomanager.Constants.Utils;
import com.mumairsaeed.dev.todomanager.Database.TodoDBHandler;
import com.mumairsaeed.dev.todomanager.Models.HabitSettingModel;
import com.mumairsaeed.dev.todomanager.R;

import java.util.ArrayList;

public class UpdateHabitFragment extends Fragment {


    AppCompatEditText habitTitle, habitDesc;

    AppCompatButton btnDelete, btnUpdate;

    ViewGroup ToastViewGroup;

    HabitSettingModel model;

    AppCompatTextView btnBackPress;
    Utils utilities;
    int habitID;

    TodoDBHandler todoDBHandler;

    AppCompatTextView sunday, monday, tuesday, wednesday, thursday, friday, saturday;

    ArrayList<String> daysOfWeekArray = new ArrayList<>();

    private static final String TASK_INFO_ARRAY = "taskArray";
    private static final String DAY_OF_WEEK_ARRAY = "dowArray";

    // TODO: Rename and change types of parameters
    private ArrayList<String> taskArray = new ArrayList<>();
    private ArrayList<String> daysArray = new ArrayList<>();

    private static Context context;

    public UpdateHabitFragment() {
        // Required empty public constructor

    }


    // TODO: Rename and change types and number of parameters
    public static UpdateHabitFragment newInstance(ArrayList<String> task, ArrayList<String>  days, Context c) {
        UpdateHabitFragment fragment = new UpdateHabitFragment();
        Bundle args = new Bundle();
        args.putStringArrayList(TASK_INFO_ARRAY, task);
        args.putStringArrayList(DAY_OF_WEEK_ARRAY, days);
        fragment.setArguments(args);
        context = c;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            taskArray = getArguments().getStringArrayList(TASK_INFO_ARRAY);
            daysArray = getArguments().getStringArrayList(DAY_OF_WEEK_ARRAY);
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_habit, container, false);

        // find ids
        todoDBHandler = new TodoDBHandler(context);
        ToastViewGroup = view.findViewById(R.id.toastContainer);
        utilities = new Utils(context);
        monday = view.findViewById(R.id.txtMonday);
        tuesday = view.findViewById(R.id.txtTuesday);
        wednesday = view.findViewById(R.id.txtWednesday);
        thursday = view.findViewById(R.id.txtThursday);
        friday = view.findViewById(R.id.txtFriday);
        sunday = view.findViewById(R.id.txtSunday);
        saturday = view.findViewById(R.id.txtSaturday);
        btnUpdate = view.findViewById(R.id.btnUpdate);
        btnDelete = view.findViewById(R.id.btnDelete);
        habitTitle = view.findViewById(R.id.edtTitle);
        habitDesc = view.findViewById(R.id.edtDesc);
        btnBackPress = view.findViewById(R.id.btnBackPress);




        // set task
        habitID = Integer.parseInt(taskArray.get(0));
        habitTitle.setText(taskArray.get(1));
        habitDesc.setText(taskArray.get(2));
        for(int i=0; i<daysArray.size(); i++){
            String day = daysArray.get(i).toLowerCase();

            if(day.equals("su")){
                markDay(sunday);
            }
            else if (day.equals("mo")){
                markDay(monday);
            }
            else if (day.equals("tu")){
                markDay(tuesday);
            }
            else if (day.equals("we")){
                markDay(wednesday);
            }
            else if (day.equals("sa")){
                markDay(saturday);
            }
            else if (day.equals("th")){
                markDay(thursday);
            }
            else if (day.equals("fr")){
                markDay(friday);
            }
        }

        model =  todoDBHandler.getSettingHabitByID(habitID);



        //btn click actions
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateHabit();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteHabit();
            }
        });

        btnBackPress.setOnClickListener(new View.OnClickListener() {
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


    public void markDay(AppCompatTextView view){
        view.setBackgroundResource(R.drawable.circle_selected);
        view.setTextColor(Color.WHITE);
    }

    public void selectFrequencyDay(AppCompatTextView view){

        String day = view.getTag().toString();

        if(daysArray.indexOf(day) == -1){

            view.setBackgroundResource(R.drawable.circle_selected);
            view.setTextColor(Color.WHITE);
            daysArray.add(day);
        }else {
            view.setBackgroundResource(R.drawable.circle_no_selected);
            view.setTextColor(Color.GRAY);
            daysArray.remove(day);
        }


    }


    // delete habit code here
    private void deleteHabit() {

        Dialog dialog = utilities.createCustomDialog("Deleting habit", "Are you sure you want to delete your habit?","All progress will be lost as a result of deleting habit",R.drawable.ic_warning);

        AppCompatButton btnCancel, btnDelete;
        btnCancel = dialog.findViewById(R.id.btnCancel);
        btnDelete = dialog.findViewById(R.id.btnDelete);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(todoDBHandler.deleteSettingHabit(model.getId())){

                    if(todoDBHandler.deleteHabitByHabitID(model.getId())){
                        Toast toast = utilities.createToast("Habit Deleted Successfully","Deleted the habit form the list of habits",true,ToastViewGroup);
                        toast.show();
                    }else {
                        Toast.makeText(context, "Something want wrong", Toast.LENGTH_SHORT).show();
                    }


                    utilities.sleepAndGoBack(2);
                    dialog.dismiss();
                } else{
                    Toast.makeText(context, "Something want wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.show();

    }

    // update habit code here
    private void updateHabit() {
        String title = habitTitle.getText().toString().trim();
        String desc = habitDesc.getText().toString().trim();

        if(title.length() == 0 || desc.length() == 0 || daysArray.isEmpty()){
            Toast toast = utilities.createToast("Filled Required Fields","All field are filled to create habit.",false,ToastViewGroup);
            toast.show();
        }else {

            model.setTitle(title);
            model.setDesc(desc);
            model.setDowArray(daysArray.toString());

            if(todoDBHandler.updateSettingHabit(model)){

                todoDBHandler.updateHabitByHabitID(model);

                Toast toast = utilities.createToast("Habit Updated Successfully",utilities.TOAST_SUCCESS_MSG,true,ToastViewGroup);
                toast.show();
                utilities.sleepAndGoBack(2);
            }else {
                Toast.makeText(context, "Something should be wrong", Toast.LENGTH_SHORT).show();
            }


        }
    }
}