package com.learn.learndemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class DefaultLevelReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("DefaultLevelReceiver","default有序广播收到消息!");
        Bundle resultExtras = getResultExtras(true);
        CharSequence content = resultExtras.getCharSequence("content").toString();
        Log.d("content","-->"+content);
        String info = resultExtras.getString("info");
        Toast.makeText(context,"default有序广播收到消息："+info,Toast.LENGTH_LONG).show();
    }
}