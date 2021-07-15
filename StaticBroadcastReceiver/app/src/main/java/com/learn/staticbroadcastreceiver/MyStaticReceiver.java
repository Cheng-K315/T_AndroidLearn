package com.learn.staticbroadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyStaticReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String info = intent.getStringExtra("info");
        Log.d("MainActivity","微信收到消息!"+info);
        Toast.makeText(context,"微信已收到消息:"+info,Toast.LENGTH_LONG).show();
    }
}