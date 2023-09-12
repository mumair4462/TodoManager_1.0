package com.mumairsaeed.dev.todomanager.Constants;

        import android.annotation.SuppressLint;
        import android.app.Dialog;
        import android.content.Context;
        import android.util.Log;
        import android.view.Gravity;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Toast;

        import androidx.appcompat.widget.AppCompatImageView;
        import androidx.appcompat.widget.AppCompatTextView;
        import androidx.fragment.app.Fragment;
        import androidx.fragment.app.FragmentActivity;
        import androidx.fragment.app.FragmentManager;
        import androidx.fragment.app.FragmentTransaction;

        import com.mumairsaeed.dev.todomanager.MainActivity;
        import com.mumairsaeed.dev.todomanager.R;

        import java.time.LocalDate;
        import java.time.format.DateTimeFormatter;
        import java.util.ArrayList;
        import java.util.Calendar;

public class Utils {
    Context context;

    public String TOAST_SUCCESS_TITLE = "Habit Created Successfully";
    public String TOAST_FAILURE_TITLE = "Habit Not Created";
    public String TOAST_SUCCESS_MSG = "We hope this habit will change your life.";
    public String TOAST_FAILURE_MSG = "Something want wrong!";
    public int HOME_FRAGMENT_FRAME = R.id.frameLayoutHome;

    public Utils(Context context) {
        this.context = context;
    }

    public void loadMainFragment(Fragment fragment, int layout, boolean isAddNew, boolean addBackStack){
//        FragmentManager fm = getSupportFragmentManager();
        FragmentManager fm = ((FragmentActivity)context).getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if(isAddNew)
            ft.add(layout, fragment);
        else
            ft.replace(layout, fragment);

        if(addBackStack)
            ft.addToBackStack(null);


        ft.commit();
    }

    public ArrayList<String> convertStringOfArrayToArray(String st){

        ArrayList<String> list = new ArrayList<>();
        String[] ary = st.split(",");

        for(int i=0; i<ary.length;i++){
            String item = ary[i].trim();

            if(item.length() == 3){
                if(i==0){
                    list.add(item.substring(1,3));
                } else {
                    list.add(item.substring(0,2));
                }
            } else if (item.length() == 4) {
                list.add(item.substring(1,3));
            }else {
                list.add(item);
            }
        }


        return list;

    }

    @SuppressLint("NewApi")
    public LocalDate getCurrentDate(){
        return LocalDate.now();
    }

    @SuppressLint("NewApi")
    public String calendarDateFormat(LocalDate date){
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        return date.format(df);
    }

    @SuppressLint("NewApi")
    public String databaseDateFormat(LocalDate date){
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return date.format(df);
    }

    public Toast createToast(String title, String msg, Boolean isSuccess, ViewGroup viewGroup){


        int icon;
        if(isSuccess.equals(true)){
            icon = R.drawable.ic_check_success;
        }else {
            icon = R.drawable.ic_cancel_failure;
        }


        Toast toast = new Toast(context);

        View view = ((FragmentActivity)context).getLayoutInflater().inflate(R.layout.custom_toast_layout, viewGroup);
        toast.setView(view);
        AppCompatImageView toastIcon = view.findViewById(R.id.toastIcon);
        AppCompatTextView toastTitle = view.findViewById(R.id.toastTitle);
        AppCompatTextView toastMsg = view.findViewById(R.id.toastDesc);

        toastTitle.setText(title);
        toastMsg.setText(msg);
        toastIcon.setBackgroundResource(icon);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP,0,0);



        return toast;
    }

    public void sleepAndGoBack(int seconds){
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onBackPress();
            }
        },seconds*1000);
    }

    public void onBackPress() {

        ((MainActivity)context).onBackPressed();

    }

    public Dialog createCustomDialog(String title, String subTitle, String msg, Integer icon){

        final Dialog dialog = new Dialog(context);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_alert_layout);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        AppCompatTextView alertTitle, alertSubtitle, alertMsg;
        AppCompatImageView alertIcon;

        alertTitle = dialog.findViewById(R.id.alertTitle);
        alertSubtitle = dialog.findViewById(R.id.alertSubTitle);
        alertMsg = dialog.findViewById(R.id.alertMsg);
        alertIcon = dialog.findViewById(R.id.alertIcon);

        alertTitle.setText(title);
        alertSubtitle.setText(subTitle);
        alertMsg.setText(msg);
        alertIcon.setImageResource(icon);

        return dialog;
    }

    public boolean isDayExist(String day, String dow){

        int index = dow.indexOf(day);

        if (index != -1) {
            return  true;
        } else {
            return  false;
        }

    }

    public String getWeekDay(){

        // Get the current weekday
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

       // Convert the day of week value to a human-readable string
        String weekday;
        switch (dayOfWeek) {
            case Calendar.SUNDAY:
                weekday = "su";
                break;
            case Calendar.MONDAY:
                weekday = "mo";
                break;
            case Calendar.TUESDAY:
                weekday = "tu";
                break;
            case Calendar.WEDNESDAY:
                weekday = "we";
                break;
            case Calendar.THURSDAY:
                weekday = "th";
                break;
            case Calendar.FRIDAY:
                weekday = "fr";
                break;
            case Calendar.SATURDAY:
                weekday = "sa";
                break;
            default:
                weekday = "null";
                break;
        }

        return weekday;

    }

    public String getFullWeekDay(){

        // Get the current weekday
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        // Convert the day of week value to a human-readable string
        String weekday;
        switch (dayOfWeek) {
            case Calendar.SUNDAY:
                weekday = "Sunday";
                break;
            case Calendar.MONDAY:
                weekday = "Monday";
                break;
            case Calendar.TUESDAY:
                weekday = "Tuesday";
                break;
            case Calendar.WEDNESDAY:
                weekday = "Wednesday";
                break;
            case Calendar.THURSDAY:
                weekday = "Thursday";
                break;
            case Calendar.FRIDAY:
                weekday = "Friday";
                break;
            case Calendar.SATURDAY:
                weekday = "Saturday";
                break;
            default:
                weekday = "null";
                break;
        }

        return weekday;

    }
}

