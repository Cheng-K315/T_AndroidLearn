package com.learn.learndemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.util.Log;
import android.widget.TextView;

public class BatteryReceiver extends BroadcastReceiver {

    private static final String TAG = "SecondActivity";
    private TextView mTv_dialog;
    DialogActivity dialogActivity;

    @Override
    public void onReceive(Context context, Intent intent) {
        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);
        int maxLevel = intent.getIntExtra(BatteryManager.EXTRA_SCALE,0);
        float percent = (level*1.0f/maxLevel)*100;
        mTv_dialog = dialogActivity.findViewById(R.id.tv_dialog);
        if (intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)){
            if (mTv_dialog!=null){
                Log.d(TAG,"电量发生变化，当前电量："+percent);
                mTv_dialog.setText("剩余电量："+percent);
            }
            if (percent<0.15){
                Log.d(TAG,"电量已低于15%");
                mTv_dialog.setText("电量已低于15%，请尽快充电!");
            }
        }else if (intent.getAction().equals(Intent.ACTION_POWER_CONNECTED)){
            Log.d(TAG,"数据线已连接!");
            mTv_dialog.setText("数据线已连接!");
        }else if (intent.getAction().equals(Intent.ACTION_POWER_DISCONNECTED)){
            Log.d(TAG,"数据线已断开!");
            mTv_dialog.setText("数据线已断开!");
        }
    }
}