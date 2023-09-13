package com.mumairsaeed.dev.todomanager.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.mumairsaeed.dev.todomanager.Constants.SharePerferenceManager;
import com.mumairsaeed.dev.todomanager.Database.TodoDBHandler;
import com.mumairsaeed.dev.todomanager.Models.HabitModel;
import com.mumairsaeed.dev.todomanager.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class TodoAdapRecycleView extends RecyclerView.Adapter<TodoAdapRecycleView.ViewHolder> {

    Context context;
    ArrayList<HabitModel> arrayList;

    Integer tern;

    private MyFragmentListener listener;

    String IS_SOUND = "sound";

    SharePerferenceManager perferenceManager;



    public TodoAdapRecycleView(Context context, ArrayList<HabitModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        perferenceManager = new SharePerferenceManager(context);
    }


    @NonNull
    @Override
    public TodoAdapRecycleView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_todo_list, parent, false);
        return new TodoAdapRecycleView.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoAdapRecycleView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        HabitModel model = arrayList.get(position);

        holder.taskTitle.setText(model.getTitle());
        holder.taskDesc.setText(model.getDesc());
        holder.taskStreak.setText("Streak: " + model.getStreak() + " Day");


        if(model.getStatus() == 1){
            holder.isCompleted.setImageResource(R.drawable.success_bg);
        } else if (model.getStatus() == 0) {
            holder.isCompleted.setImageResource(R.drawable.failure_bg);
        }else {
            holder.isCompleted.setImageResource(R.drawable.default_circle_bg);
        }

        holder.isCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String isSound = perferenceManager.getData(IS_SOUND, "0");

                if(!isSound.equals(("0"))){
                    final MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.click_sound);
                    mediaPlayer.start();
                }

                HabitModel model = arrayList.get(position);

                int tern = model.getStatus();

                if(tern == -1 || tern == 0){
                    holder.isCompleted.setImageResource(R.drawable.success_bg);
                    model.setStatus(1);
                    model.setStreak(model.getStreak() + 1);

                }else {
                    holder.isCompleted.setImageResource(R.drawable.failure_bg);
                    model.setStatus(0);
                    model.setStreak(0);
                }
                holder.taskStreak.setText("Streak: " + model.getStreak() + " Day");
                TodoDBHandler dbHandler = new TodoDBHandler(context);
                dbHandler.updateHabit(model);

                if (listener != null) {
                    listener.onItemClick(position);
                }

            }
        });

    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public interface MyFragmentListener {
        void onItemClick(int position);
    }


    public void setOnItemClickListener(MyFragmentListener listener) {
        this.listener = listener;
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView taskTitle, taskDesc, taskStreak;

        CircleImageView isCompleted;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            taskTitle = itemView.findViewById(R.id.taskTitle);
            taskDesc = itemView.findViewById(R.id.taskDesc);
            taskStreak = itemView.findViewById(R.id.taskStreak);
            isCompleted = itemView.findViewById(R.id.btnStatus);

        }
    }
}
