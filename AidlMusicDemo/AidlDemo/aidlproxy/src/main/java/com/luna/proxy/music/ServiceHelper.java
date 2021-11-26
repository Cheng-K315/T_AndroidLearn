package com.luna.proxy.music;

import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.util.Log;

import java.lang.reflect.Method;

public abstract class ServiceHelper<T extends IInterface> {

    private final String TAG = "MiscServiceHelper";
    protected volatile T mService;

    protected abstract void dealServiceDied();//服务died后通知客户端
    protected abstract T linkToService(IBinder binder);//服务连接后客户端处理
    public abstract boolean assertService();

    protected final T getService(String serviceName) {
        Object obj = new Object();
        Method method;
        IBinder binder = null;
        T tempService = null;
        try {
            Log.e(TAG, "getService: " + serviceName);
            method = Class.forName("android.os.ServiceManager").getMethod("getService",String.class);
            binder = (IBinder) method.invoke(obj, serviceName);
            //ServiceManager.getService(serviceName);
        } catch (Exception e) {
            e.printStackTrace();
            binder = null;
        }
        if(binder == null) return null;
        try {
            binder.linkToDeath(new IBinder.DeathRecipient() {
                @Override
                public void binderDied() {
                    dealServiceDied();
                }
            },0);
            tempService = linkToService(binder);
        } catch (RemoteException e) {
            e.printStackTrace();
            dealServiceDied();
        }
        return tempService;

    }

}
