package com.mumairsaeed.dev.todomanager.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.mumairsaeed.dev.todomanager.Constants.Utils;
import com.mumairsaeed.dev.todomanager.Database.TodoDBHandler;
import com.mumairsaeed.dev.todomanager.HomeFragments.UpdateHabitFragment;
import com.mumairsaeed.dev.todomanager.Models.HabitSettingModel;
import com.mumairsaeed.dev.todomanager.R;

import java.util.ArrayList;

public class TaskBuilderAdapRecycleView extends RecyclerView.Adapter<TaskBuilderAdapRecycleView.ViewHolder> {

    Context context;
    ArrayList<HabitSettingModel> arrayList;

    public TaskBuilderAdapRecycleView(Context context, ArrayList<HabitSettingModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public TaskBuilderAdapRecycleView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_task_builder_list, parent, false);
        return new TaskBuilderAdapRecycleView.ViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull TaskBuilderAdapRecycleView.ViewHolder holder,  int position) {
        HabitSettingModel model = arrayList.get(position);

        holder.taskTitle.setText(model.getTitle());
        holder.taskDate.setText("Active Since: " + model.getActiveDate());
        if(model.getIsActive() == 1){
            holder.isActive.setChecked(true);
        }else {
            holder.isActive.setChecked(false);
        }


        holder.isActive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                HabitSettingModel model = arrayList.get(position);
                if(b){
                    model.setIsActive(1);
                }else {
                    model.setIsActive(0);
                }

                TodoDBHandler todoDBHandler = new TodoDBHandler(context);
                todoDBHandler.updateSettingHabit(model);
                todoDBHandler.updateHabitByHabitID(model);
            }
        });

        holder.btnTaskEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ArrayList<String> task = new ArrayList<>();
                ArrayList<String> days = new ArrayList<>();

                HabitSettingModel model = arrayList.get(position);

                task.add(String.valueOf(model.getId()));
                task.add(model.getTitle());
                task.add(model.getDesc());


                Utils utilities = new Utils(context);

                ArrayList<String> list = utilities.convertStringOfArrayToArray(model.getDowArray());

                for(int i=0;i<list.size();i++){
                    days.add(list.get(i));
                }


                utilities.loadMainFragment(UpdateHabitFragment.newInstance(task,days, context), utilities.HOME_FRAGMENT_FRAME, false, true);

            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView taskTitle, taskDate;
        SwitchMaterial isActive;

        AppCompatImageView btnTaskEdit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            taskTitle = itemView.findViewById(R.id.taskTitle);
            taskDate = itemView.findViewById(R.id.taskDate);
            isActive = itemView.findViewById(R.id.material_switch);
            btnTaskEdit = itemView.findViewById(R.id.btnEdit);

        }
    }
}
