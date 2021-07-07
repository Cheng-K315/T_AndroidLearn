package com.learn.myactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("MyActivity","TaskID:"+getTaskId());
        Intent intent = new Intent("android.intent.action.MYACTION");
        startActivity(intent);
    }
}