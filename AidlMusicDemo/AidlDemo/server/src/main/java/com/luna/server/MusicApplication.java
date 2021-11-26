package com.luna.server;

import android.app.Application;
import android.content.Intent;

import com.luna.proxy.utils.MusicLog;

public class MusicApplication extends Application {
    private final String TAG = "MiscApplication";
    @Override
    public void onCreate() {
        super.onCreate();
        MusicLog.e(TAG,"MiscApplication  onCreate");
        startService(new Intent(this,MusicService.class));
    }
}
