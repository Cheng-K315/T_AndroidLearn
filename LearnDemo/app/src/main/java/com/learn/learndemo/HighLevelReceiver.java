package com.learn.learndemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class HighLevelReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("HighLevelReceiver","high有序广播收到消息!");
        /**
         * 终止有序广播往下传送
         */
//        abortBroadcast();
        /**
         * 修改广播内容
         */
        Bundle resultExtras = getResultExtras(true);
        CharSequence content = resultExtras.getCharSequence("content").toString();
        Log.d("content","-->"+content);
        String info = resultExtras.getString("info");
        Toast.makeText(context,"high有序广播收到消息："+info,Toast.LENGTH_LONG).show();
        resultExtras.putCharSequence("content","我是被修改过的广播内容");
        setResultExtras(resultExtras);
    }
}