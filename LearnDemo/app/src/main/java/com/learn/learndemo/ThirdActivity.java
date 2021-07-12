package com.learn.learndemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class ThirdActivity extends BaseActivity {

    private static final String TAG = "ThirdActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate! and task id is " + this.getTaskId());
        setContentView(R.layout.activity_third);
    }

    //Activity生命周期
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"onStart! and task id is "+this.getTaskId());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"onResume! and task id is "+this.getTaskId());
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"onPause! and task id is "+this.getTaskId());
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"onStop! and task id is "+this.getTaskId());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy! and task id is "+this.getTaskId());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG,"onRestart! and task id is "+this.getTaskId());
    }
}