 package com.learn.learndemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";
    private Button mBtn_secondActivity;
    private Button mBtn_thirdActivity;
    private Button mBtn_mainActivity;
    private Button mBtn_fourthActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate! and task id is "+this.getTaskId());
        setContentView(R.layout.activity_main);
        initView();
        setListeners();
    }

    //控件初始化
    private void initView() {
        mBtn_secondActivity = (Button)findViewById(R.id.btn_secondActivity);
        mBtn_thirdActivity = (Button)findViewById(R.id.btn_thirdActivity);
        mBtn_mainActivity = (Button)findViewById(R.id.btn_mainActivity);
        mBtn_fourthActivity = (Button)findViewById(R.id.btn_fourthActivity);
    }

    private class OnClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent = null;
            switch (v.getId()){
                case R.id.btn_mainActivity :
                    intent = new Intent(MainActivity.this,MainActivity.class);
                    break;
                case R.id.btn_secondActivity :
                    intent = new Intent("android.intent.action.SECOND_ACTIVITY");
                    break;
                case R.id.btn_thirdActivity :
                    intent = new Intent(MainActivity.this,ThirdActivity.class);
                    break;
                case R.id.btn_fourthActivity :
                    intent = new Intent(MainActivity.this,FourthActivity.class);
                    break;
            }
            startActivity(intent);
        }
    }

    //设置监听事件
    private void setListeners(){
        OnClick onClick = new OnClick();
        mBtn_secondActivity.setOnClickListener(onClick);
        mBtn_thirdActivity.setOnClickListener(onClick);
        mBtn_mainActivity.setOnClickListener(onClick);
        mBtn_fourthActivity.setOnClickListener(onClick);
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