package com.mumairsaeed.dev.todomanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.google.android.material.circularreveal.CircularRevealFrameLayout;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.mumairsaeed.dev.todomanager.Constants.Utils;
import com.mumairsaeed.dev.todomanager.MainFragments.HomeFragment;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    CircularRevealFrameLayout frameLayout;
    Toolbar toolbar;
    NavigationView navigationView;

    int MAIN_FRAME_LAYOUT;

    Utils constants;

    TabLayout tabLayout;
    ViewPager viewPager;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        init();
        constants = new Utils(this);
        MAIN_FRAME_LAYOUT = R.id.main_frame;

        constants.loadMainFragment(new HomeFragment(this), MAIN_FRAME_LAYOUT, true, false);

    }
}