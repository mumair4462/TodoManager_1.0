package com.mumairsaeed.dev.todomanager.MainFragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.circularreveal.CircularRevealFrameLayout;
import com.mumairsaeed.dev.todomanager.Constants.Utils;
import com.mumairsaeed.dev.todomanager.HomeFragments.StatisticsFragment;
import com.mumairsaeed.dev.todomanager.HomeFragments.TaskBuilderFragment;
import com.mumairsaeed.dev.todomanager.HomeFragments.TodoFragment;
import com.mumairsaeed.dev.todomanager.R;

public class HomeFragment extends Fragment {

    BottomNavigationView bottomNavigation;
    CircularRevealFrameLayout frameLayout;

    Context context;

    Utils constants;

    public HomeFragment(Context context) {
        // Required empty public constructor
        this.context = context;

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // finds ID's of views
        bottomNavigation = view.findViewById(R.id.bottomNavigation);
        frameLayout = view.findViewById(R.id.frameLayoutHome);
        constants = new Utils(context);

        @SuppressLint("ResourceType") ColorStateList colorStateList = getResources().getColorStateList(R.drawable.bottom_nav_icon_color);
        bottomNavigation.setItemIconTintList(colorStateList);

        constants.loadMainFragment(new TodoFragment(context), constants.HOME_FRAGMENT_FRAME, true, false);

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int ids = item.getItemId();

                if(ids == R.id.optBuildTask){
                    constants.loadMainFragment(new TaskBuilderFragment(context), constants.HOME_FRAGMENT_FRAME, false, false);
                }else if (ids == R.id.optTodos){
                    constants.loadMainFragment(new TodoFragment(context), constants.HOME_FRAGMENT_FRAME, false, false);
                }else {
                    constants.loadMainFragment(new StatisticsFragment(context), constants.HOME_FRAGMENT_FRAME, false, false);
                }

                return true;
            }
        });

        return view;
    }
}