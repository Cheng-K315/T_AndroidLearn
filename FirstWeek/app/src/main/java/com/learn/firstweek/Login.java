package com.learn.firstweek;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    private static final String Tag = "11111111 梧桐";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button third =(Button)findViewById(R.id.btn_third);
        third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,ThirdActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(Tag,"登录onStart!"+this.getTaskId());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(Tag,"登录onResume!"+this.getTaskId());
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(Tag,"登录onPause!"+this.getTaskId());
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(Tag,"登录onStop!"+this.getTaskId());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(Tag,"登录onDestroy!"+this.getTaskId());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(Tag,"登录onRestart!"+this.getTaskId());
    }
}