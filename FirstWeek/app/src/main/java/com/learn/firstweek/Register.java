package com.learn.firstweek;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class Register extends AppCompatActivity {

    private static final String Tag = "11111111 梧桐";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(Tag,"注册onStart!");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(Tag,"注册onResume!");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(Tag,"注册onPause!");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(Tag,"注册onStop!");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(Tag,"注册onDestroy!");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(Tag,"注册onRestart!");
    }
}