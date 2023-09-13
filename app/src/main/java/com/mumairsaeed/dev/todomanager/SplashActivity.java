package com.mumairsaeed.dev.todomanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.mumairsaeed.dev.todomanager.Constants.SharePerferenceManager;

public class SplashActivity extends AppCompatActivity {

    String CHECK_PASSWORD = "checkPassword";
    String PASSWORD = "password";

    SharePerferenceManager perferenceManager;

    private static final long SPLASH_DELAY_MS = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        perferenceManager = new SharePerferenceManager(this);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                String result = perferenceManager.getData(CHECK_PASSWORD, "0");
                String pass = perferenceManager.getData(PASSWORD, "0");

                if(result.equals("0") || pass.equals("0")){
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                }else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }


                finish();
            }
        }, SPLASH_DELAY_MS);



    }
}