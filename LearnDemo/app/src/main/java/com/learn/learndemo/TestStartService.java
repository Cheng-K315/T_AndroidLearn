package com.learn.learndemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class TestStartService extends Service {

    private static final String TAG = "TestStartService";
    private int count;
    private boolean quit;

    //定义onBind方法所返回的对象
    private MyBinder binder = new MyBinder();
    public class MyBinder extends Binder{
        public int getCount(){
            return count;
        }
    }

    //必须要实现的方法,绑定service时回调该方法
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG,"onBind方法被调用");
        return binder;
    }

    //Service被创建时回调该方法
    @Override
    public void onCreate() {
        Log.d(TAG,"onCreate方法被调用");
        super.onCreate();

        //创建一个线程动态地修改count的值
        new Thread()
        {
            public void run()
            {
                while(!quit)
                {
                    try
                    {
                        Thread.sleep(1000);
                    }catch(InterruptedException e){e.printStackTrace();}
                    count++;
                }
            };
        }.start();
    }

    //Service被启动时回调
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG,"onStartCommand方法被调用");
        return super.onStartCommand(intent, flags, startId);
    }

    //Service解除绑定时回调
    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG,"onUnbind方法被调用");
        return true;
    }

    //Service被关前回调
    @Override
    public void onDestroy() {
        Log.d(TAG,"onDestroy方法被调用");
        this.quit = true;
        super.onDestroy();
    }

    //Service重新启动时回调
    @Override
    public void onRebind(Intent intent) {
        Log.d(TAG,"onRebind方法被调用");
        super.onRebind(intent);
    }
}