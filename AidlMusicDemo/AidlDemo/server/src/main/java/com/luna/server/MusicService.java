package com.luna.server;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;


import com.luna.proxy.constants.MusicConstants;
import com.luna.proxy.utils.MusicLog;

import java.lang.reflect.Method;


public class MusicService extends Service {

    private static final String TAG = MusicService.class.getSimpleName();
    private volatile boolean isRunning = false;
    public static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        if (!isRunning) {
            init();
            isRunning = true;
        }

        MusicLog.e(TAG, "MiscService  onCreate finish!!");
    }

    private void init() {
        mContext = this;
        Method method;
        Object obj = new Object();
        try {
            Class<?> cServiceManager = Class.forName("android.os.ServiceManager");
            Method mGetService = cServiceManager.getMethod("getService",String.class);
            method = cServiceManager.getMethod("addService",String.class, IBinder.class);
            method.invoke(obj, MusicConstants.MUSIC_SERVICE_NAME,MusicImpl.getInstance());
        } catch (Exception e) {
            MusicLog.e(TAG, "init error");
            e.printStackTrace();
        }
        //ServiceManager.addService(MusicConstants.MUSIC_SERVICE_NAME, MiscAudioImpl.getInstance());
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
