package com.mumairsaeed.dev.todomanager.HomeFragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.mumairsaeed.dev.todomanager.Database.TodoDBHandler;
import com.mumairsaeed.dev.todomanager.Models.HabitModel;
import com.mumairsaeed.dev.todomanager.R;

import java.util.ArrayList;

public class StatisticsFragment extends Fragment {

    Context context;
    TableLayout tableLayout;

    TodoDBHandler dbHandler;

    ArrayList<Integer> idsArray;

    AppCompatTextView longestStreak, shortestStreak, successRate, failureRate;
    public StatisticsFragment(Context context) {
        // Required empty public constructor
        this.context = context;
    }



    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_statistics, container, false);

        dbHandler = new TodoDBHandler(context);
        tableLayout =  view.findViewById(R.id.tlGridTable);
        longestStreak = view.findViewById(R.id.longestStreak);
        shortestStreak = view.findViewById(R.id.shortestStreak);
        successRate = view.findViewById(R.id.successRate);
        failureRate = view.findViewById(R.id.failureRate);


        // Table added
        idsArray = dbHandler.getAllHabitSettingIDs();

        if(!idsArray.isEmpty()){

            for (int i=0; i<idsArray.size(); i++){
                int id = idsArray.get(i);
                ArrayList<HabitModel> habitArray = dbHandler.getHabitByHabitID(id);
                ArrayList<String> result = processingWithHabit(habitArray);
                if(!result.isEmpty()){
                    addTableRow(result.get(0),result.get(1), result.get(2), result.get(3), result.get(4));
                }
            }

        }

//      Others
        ArrayList<String> maxStreak = dbHandler.getMaxStreak();
        ArrayList<String> minStreak = dbHandler.getMinStreak();
        ArrayList<String> successFailureRate = dbHandler.calculateSuccessFailureRate();

        longestStreak.setText(maxStreak.get(2)+" - " + maxStreak.get(1));
        shortestStreak.setText(minStreak.get(2)+" - " + minStreak.get(1));
        successRate.setText(successFailureRate.get(0));
        failureRate .setText(successFailureRate.get(1));


        return view;
    }

    ArrayList<String> processingWithHabit(ArrayList<HabitModel> habitArray){

        ArrayList<String> resultArray = new ArrayList<>();


        if(!habitArray.isEmpty()){



            int success=0, failure=0, maxStreak = 0;

            for (int i=0; i < habitArray.size(); i++){
                HabitModel model = habitArray.get(i);
                if(model.getStatus() == 1){
                    success++;
                }else {
                    failure++;
                }

                if(model.getStreak() > maxStreak){
                    maxStreak = model.getStreak();
                }
            }

            resultArray.add(habitArray.get(0).getTitle());
            resultArray.add(String.valueOf(habitArray.size()));
            resultArray.add(String.valueOf(success));
            resultArray.add(String.valueOf(failure));
            resultArray.add(String.valueOf(maxStreak));

        }

        return resultArray;

    }

    void addTableRow(String title, String days, String success, String failure, String streak){

        try {

            // get table row
            TableRow tr = (TableRow) getLayoutInflater().inflate(R.layout.table_row, null);

            // finds views
            AppCompatTextView taskTitle, taskDays, taskSuccess, taskFailure, taskStreak;
            taskTitle = tr.findViewById(R.id.txtTitle);
            taskDays = tr.findViewById(R.id.txtTotalDays);
            taskSuccess = tr.findViewById(R.id.txtSuccessTask);
            taskFailure = tr.findViewById(R.id.txtFailureTask);
            taskStreak = tr.findViewById(R.id.txtMaxStreak);


            // add row data
            taskTitle.setText(title);
            taskDays.setText(days);
            taskFailure.setText(failure);
            taskSuccess.setText(success);
            taskStreak.setText(streak);

            tableLayout.addView(tr);


        }catch (Exception e){
            Log.d("error", e.toString());
            e.printStackTrace();
        }
    }
}