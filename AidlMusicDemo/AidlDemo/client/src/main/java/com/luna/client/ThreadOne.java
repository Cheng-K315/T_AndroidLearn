package com.luna.client;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;


public class ThreadOne extends Thread{
    private static final String TAG = "ThreadOne";
    int mTid = -1;
    Looper mLooper;
    public ThreadOneHandler mThreadOneHandler;
    public Handler mHandler;

    public static  final int MSG_WHAT_ONE = 1;
    public static final int MSG_WHAT_TWO = 2;


    @Override
    public void run() {
        //TODO 1 创建Looper
        mTid = Process.myTid();
        Log.d(TAG,"current tid:"+mTid+",run");
        Looper.prepare();
        synchronized (this) {
            mLooper = Looper.myLooper();
            notifyAll();
        }
        //TODO 2 启动消息循环
        Looper.loop();
        Log.d(TAG,"current tid:"+mTid+",over");
        mTid = -1;

        //TODO 3 创建该线程的Handler
        mHandler = new Handler(Looper.myLooper());
        mThreadOneHandler = new ThreadOneHandler();
    }



     class  ThreadOneHandler extends Handler{
        @Override
        public void handleMessage( Message msg) {
            switch (msg.what){
                case MSG_WHAT_ONE:
                    Log.d(TAG,"handle msg one");
                    break;
                case MSG_WHAT_TWO:
                    Log.d(TAG,"handle msg two");
                    break;
                default:
                    break;
            }
        }
     }
}
