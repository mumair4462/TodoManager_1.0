package com.mumairsaeed.dev.todomanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.SwitchCompat;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.mumairsaeed.dev.todomanager.Constants.SharePerferenceManager;

public class SettingActivity extends AppCompatActivity {

    Boolean isSetPassword = false;

    String CHECK_PASSWORD = "checkPassword";
    String PASSWORD = "password";
    String IS_SOUND = "sound";

    LinearLayoutCompat paswordContainer, NewPasswordCont, VerifyPasswordCont, UpdatePasswordCont, DefaultPasswordCont;
    SwitchCompat mySwitch;

    SharePerferenceManager perferenceManager;

    AppCompatTextView soundLabel;

    AppCompatEditText newPassInput, oldPassInput, updatePassInput;
    AppCompatButton btnSaveNewPassword, btnChangePassword, btnVerifyPassword, btnUpdatePass;

    AppCompatCheckBox myCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        init();

        loadGUI();

        // Set an OnCheckedChangeListener for the SwitchCompat
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    perferenceManager.saveData(CHECK_PASSWORD,"1");
                }else {
                    perferenceManager.saveData(CHECK_PASSWORD,"0");
                }
                loadGUI();
            }
        });

        btnSaveNewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(newPassInput.getText().toString().trim().length() != 0){
                    perferenceManager.saveData(PASSWORD, newPassInput.getText().toString().trim());
                    loadGUI();
                }else {
                    Toast.makeText(SettingActivity.this, "Please enter password", Toast.LENGTH_SHORT).show();
                }



            }
        });

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setVisibility(VerifyPasswordCont);

            }
        });

        btnVerifyPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String pass = perferenceManager.getData(PASSWORD,"0");
                if(pass.equals(oldPassInput.getText().toString().trim())){
                    setVisibility(UpdatePasswordCont);
                }else {
                    Toast.makeText(SettingActivity.this, "Password not correct", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnUpdatePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(updatePassInput.getText().toString().trim().length() != 0){
                    perferenceManager.saveData(PASSWORD, updatePassInput.getText().toString().trim());
                    Toast.makeText(SettingActivity.this, "Password Updated Successfully", Toast.LENGTH_SHORT).show();
                    loadGUI();
                }else {
                    Toast.makeText(SettingActivity.this, "Please enter password", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // Set an OnClickListener to perform an operation when it's clicked
        myCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform your operation here
                if (myCheckBox.isChecked()) {
                    perferenceManager.saveData(IS_SOUND, "1");
                } else {
                    perferenceManager.saveData(IS_SOUND, "0");
                }
                loadGUI();
            }
        });

    }

    public void init(){
        paswordContainer = findViewById(R.id.pasword_container);
        mySwitch = findViewById(R.id.mySwitch);
        perferenceManager = new SharePerferenceManager(this);
        NewPasswordCont = findViewById(R.id.NewPasswordCont);
        VerifyPasswordCont = findViewById(R.id.VerifyPasswordCont);
        UpdatePasswordCont = findViewById(R.id.UpdatePasswordCont);
        DefaultPasswordCont = findViewById(R.id.DefaultPasswordCont);
        newPassInput = findViewById(R.id.newPassInput);
        btnSaveNewPassword = findViewById(R.id.saveNewPassword);
        btnChangePassword = findViewById(R.id.btnChangePassword);
        oldPassInput = findViewById(R.id.oldPasswordInput);
        btnVerifyPassword = findViewById(R.id.btnVerifyPassword);
        updatePassInput = findViewById(R.id.updatePassInput);
        btnUpdatePass = findViewById(R.id.btnUpdatePass);
        soundLabel = findViewById(R.id.soundLabel);
        myCheckBox = findViewById(R.id.myCheckBox);
    }

    public void  loadGUI(){
        String result = perferenceManager.getData(CHECK_PASSWORD, "0");
        if(result.equals("1")){
            isSetPassword = true;
            mySwitch.setChecked(true);
        }else {
            isSetPassword = false;
            mySwitch.setChecked(false);
        }
        if(isSetPassword){
            paswordContainer.setVisibility(View.VISIBLE);
        }else {
            paswordContainer.setVisibility(View.GONE);
        }

        String password = perferenceManager.getData(PASSWORD, "0");

        if(password.equals("0")){
            setVisibility(NewPasswordCont);



        }else {
            AppCompatEditText defaultInput = findViewById(R.id.defaultInput);
            setVisibility(DefaultPasswordCont);
            defaultInput.setText(password);
        }


        oldPassInput.setText("");
        newPassInput.setText("");
        updatePassInput.setText("");


        // sound working
        String sound = perferenceManager.getData(IS_SOUND, "0");
        if(sound.equals("0")){
            myCheckBox.setChecked(false);
            soundLabel.setText("Disable");
        }else {
            myCheckBox.setChecked(true);
            soundLabel.setText("Enable");
        }

    }

    public void setVisibility(LinearLayoutCompat content){
        NewPasswordCont.setVisibility(View.GONE);
        VerifyPasswordCont.setVisibility(View.GONE);
        UpdatePasswordCont.setVisibility(View.GONE);
        DefaultPasswordCont.setVisibility(View.GONE);
        content.setVisibility(View.VISIBLE);
    }

    public void cancel(View view) {
        loadGUI();
    }

    public void backPress(View view) {
        onBackPressed();
    }
}