package com.learn.filetestdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Map;

public class SharedPreferencesActivity extends AppCompatActivity {

    private EditText et_name;
    private EditText et_password;
    private Button btn_login;
    private Context context;
    SharedHelper sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preferences);

        context = getApplicationContext();
        sh = new SharedHelper(context);
        bindViews();

    }

    private void bindViews() {
        et_name = (EditText)findViewById(R.id.et_name);
        et_password = (EditText)findViewById(R.id.et_password);
        btn_login = (Button)findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = et_name.getText().toString();
                String password = et_password.getText().toString();
                sh.save(username,password);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Map<String,String> date = sh.read();
        et_name.setText(date.get("username"));
        et_password.setText(date.get("password"));
    }
}