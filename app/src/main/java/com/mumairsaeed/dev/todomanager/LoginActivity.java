package com.mumairsaeed.dev.todomanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.mumairsaeed.dev.todomanager.Constants.SharePerferenceManager;

public class LoginActivity extends AppCompatActivity {


    String PASSWORD = "password";

    AppCompatEditText inputBox;
    AppCompatButton btnLogin;

    SharePerferenceManager perferenceManager;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        perferenceManager = new SharePerferenceManager(this);
        inputBox = findViewById(R.id.inputBox);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String pass = perferenceManager.getData(PASSWORD, "");
                if(inputBox.getText().toString().trim().length() !=0 ){

                    if(pass.equals(inputBox.getText().toString().trim())){

                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();

                    }else {
                        Toast.makeText(LoginActivity.this, "Enter valid password", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(LoginActivity.this, "Please enter password", Toast.LENGTH_SHORT).show();
                }


            }
        });






    }
}