package com.learn.staticbroadcastreceiver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String msg = intent.getStringExtra("mag");
        Log.d("MainActivity","微信收到消息!");
        Toast.makeText(getApplicationContext(),"微信已收到"+msg,Toast.LENGTH_LONG).show();
    }
}