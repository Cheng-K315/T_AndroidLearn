package com.learn.learndemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SecondActivity extends BaseActivity {

    private static final String TAG = "SecondActivity";
    private Button mBtn_thirdActivity;
    private Button mBtn_dialog;
    private Button mBtn_sendBroadcast;
    BatteryReceiver batteryReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate! and task id is " + this.getTaskId());
        setContentView(R.layout.activity_second);

        initView();
        registerReceiver();

        mBtn_thirdActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this,ThirdActivity.class);
                startActivity(intent);
            }
        });

        mBtn_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this,DialogActivity.class);
                startActivity(intent);
            }
        });

        sendBroadcast();
    }

    private void sendBroadcast() {
        mBtn_sendBroadcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("com.learn.staticbroadcastreceiver.STATIC_RECEIVER");
                intent.putExtra("msg","QQ发送过来的消息");
                sendBroadcast(intent);
                Toast.makeText(getApplicationContext(),"QQ发送消息成功!",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initView() {
        mBtn_thirdActivity = (Button)findViewById(R.id.btn_thirdActivity);
        mBtn_dialog = (Button)findViewById(R.id.btn_dialog);
        mBtn_sendBroadcast = (Button)findViewById(R.id.btn_sendBroadcast);
    }

    //动态注册广播
    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        intentFilter.addAction(Intent.ACTION_POWER_CONNECTED);
        intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        batteryReceiver = new BatteryReceiver();
        registerReceiver(batteryReceiver,intentFilter);
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

    //取消广播注册，避免内存泄漏
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy! and task id is "+this.getTaskId());
        if(batteryReceiver!=null){
            this.unregisterReceiver(batteryReceiver);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG,"onRestart! and task id is "+this.getTaskId());
    }

}