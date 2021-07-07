package com.learn.firstweek;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String Tag = "11111111 梧桐";
    private TextView mTv;
    private Button mBtn_register;
    private  Button mBtn_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(Tag,"this is a demo!"+this.getTaskId());

        mTv = (TextView)findViewById(R.id.tv_1);
        mTv.setSelected(true);
        mBtn_register = (Button)findViewById(R.id.btn_register);
        mBtn_login = (Button)findViewById(R.id.btn_login);
        setListeners();
//        mBtn_login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this,Login.class);
//                startActivity(intent);
//            }
//        });
    }
    private class OnClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
         Intent intent = null;
         switch (v.getId()){
             case R.id.btn_register:
                 intent = new Intent(MainActivity.this,MainActivity.class);
                 break;
             case R.id.btn_login:
                 intent = new Intent(MainActivity.this,Login.class);
                 break;
         }
         startActivity(intent);
        }
    }
    private void setListeners(){
        OnClick onClick =new OnClick();
        mBtn_register.setOnClickListener(onClick);
        mBtn_login.setOnClickListener(onClick);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(Tag,"主页面onStart!"+this.getTaskId());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(Tag,"主页面onResume!"+this.getTaskId());
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(Tag,"主页面onPause!"+this.getTaskId());
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(Tag,"主页面onStop!"+this.getTaskId());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(Tag,"主页面onDestroy!"+this.getTaskId());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(Tag,"主页面onRestart!"+this.getTaskId());
    }
}