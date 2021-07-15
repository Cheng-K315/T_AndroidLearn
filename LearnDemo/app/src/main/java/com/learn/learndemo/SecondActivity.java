package com.learn.learndemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends BaseActivity {

    private static final String TAG = "SecondActivity";
    private Button mBtn_thirdActivity;
    private Button mBtn_sendBroadcast;
    private TextView mTv_battery;
    private EditText mEt_content;
    private Button mBtn_sendOrderBroadcast;
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
    }

    public void sendOrderBroadcast(View view) {
        mBtn_sendOrderBroadcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent();
                intent.setAction("com.learn.learndemo.ORDERED_RECEIVER");
                intent.setPackage("com.learn.learndemo");
                String info = mEt_content.getText().toString();
                Bundle bundle = new Bundle();
                bundle.putCharSequence("content","我是广播发送的内容");
                bundle.putString("info",info);
                sendOrderedBroadcast(intent,null,null,null, Activity.RESULT_OK,null,bundle);
                Toast.makeText(getApplicationContext(),"有序广播发送成功！",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void sendBroadcast(View view) {
        mBtn_sendBroadcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                String info = mEt_content.getText().toString();
                intent.setAction("com.learn.staticbroadcastreceiver.STATIC_RECEIVER");
                intent.setPackage("com.learn.staticbroadcastreceiver");
                intent.putExtra("info",info);
                sendBroadcast(intent);
                Toast.makeText(getApplicationContext(),"QQ发送消息成功!",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initView() {
        mBtn_thirdActivity = (Button)findViewById(R.id.btn_thirdActivity);
        mBtn_sendBroadcast = (Button)findViewById(R.id.btn_sendBroadcast);
        mTv_battery = (TextView)findViewById(R.id.tv_battery);
        mEt_content = (EditText)findViewById(R.id.et_sendInfo);
        mBtn_sendOrderBroadcast = (Button)findViewById(R.id.btn_sendOrderBroadcast);
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

    private class BatteryReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);
            int maxLevel = intent.getIntExtra(BatteryManager.EXTRA_SCALE,0);
            float percent = (level*1.0f/maxLevel)*100;
            if (intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)){
                if (mTv_battery!=null){
                    Log.d(TAG,"电量发生变化，当前电量："+percent+"%");
                    mTv_battery.setText("剩余电量："+percent+"%");
                    if (percent<15){
                        Log.d(TAG,"电量已低于15%");
                        Toast.makeText(context,"电量已低于15%，请尽快充电!",Toast.LENGTH_LONG).show();
                    }
                }
            }else if (intent.getAction().equals(Intent.ACTION_POWER_CONNECTED)){
                Log.d(TAG,"数据线已连接!");
                Toast.makeText(context,"数据线已连接!",Toast.LENGTH_LONG).show();
            }else if (intent.getAction().equals(Intent.ACTION_POWER_DISCONNECTED)){
                Log.d(TAG,"数据线已断开!");
                Toast.makeText(context,"数据线已断开!",Toast.LENGTH_LONG).show();
            }
        }
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