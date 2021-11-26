package com.luna.server;

import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;

import com.luna.proxy.IMusicCalllback;
import com.luna.proxy.IMusictInterface;
import com.luna.proxy.bean.MusicBean;
import com.luna.proxy.utils.MusicLog;

import java.util.ArrayList;

public class MusicImpl extends IMusictInterface.Stub{

    private static MusicImpl mMusicImpl;
    private float duration;
    private int progress;
    private String album;
    private static final int STATUS_STOP = 0;
    private static final int STATUS_PLAY = 1;
    private int status = STATUS_STOP;

    private ArrayList<IMusicCalllback> mMusicCallbackList = new ArrayList<IMusicCalllback>();
    private Handler mHandler = new Handler(Looper.getMainLooper());

    private static final String TAG = MusicImpl.class.getSimpleName();
    public synchronized static MusicImpl getInstance(){
        if(mMusicImpl == null){
            mMusicImpl = new MusicImpl();
        }
        return mMusicImpl;
    }

    private MusicImpl(){
        init();
    }
    private void init(){
        MusicLog.i(TAG,"init");
        duration = 4.5f;
        progress = 0;
        album="后来";
    }


    @Override
    public synchronized MusicBean getMusicBean() throws RemoteException {
        MusicLog.e(TAG,"getMusicBean ,from" + Binder.getCallingPid());
        MusicBean bean = new MusicBean(duration,progress,album);
        return bean;
    }

    @Override
    public synchronized void setProgress(int progress) throws RemoteException {
        MusicLog.e(TAG,"setProgress:" + progress +",from:" + Binder.getCallingPid());
        this.progress = progress;
    }

    @Override
    public  void registerCallback(IMusicCalllback callback) throws RemoteException {
        MusicLog.e(TAG,"registerCallback,from:" + Binder.getCallingPid() );
        try{
            if (callback == null) {
                throw new NullPointerException("aCallback is null in registerCallBack");
            }
            synchronized(mMusicCallbackList) {
                IBinder binder = callback.asBinder();
                int size = mMusicCallbackList.size();
                for (int i = 0; i < size; i++) {
                    IMusicCalllback test = mMusicCallbackList.get(i);
                    if (binder.equals(test.asBinder())) {
                        // listener already added
                        return ;
                    }
                }
                mMusicCallbackList.add(callback);
            }
        }catch (Exception e){

        }
    }

    @Override
    public void unRegisterCallback(IMusicCalllback callback) throws RemoteException {
        MusicLog.e(TAG,"unRegisterCallback" );
        try {
            if (callback == null) {
                throw new NullPointerException("aCallback is null in unregisterCallBack");
            }
            synchronized(mMusicCallbackList) {
                IMusicCalllback listener = null;
                int size = mMusicCallbackList.size();
                for (int i = 0; i < size && listener == null; i++) {
                    IMusicCalllback test = mMusicCallbackList.get(i);
                    if (callback.asBinder().equals(test.asBinder())) {
                        listener = test;
                        break;
                    }
                }

                if (listener != null) {
                    mMusicCallbackList.remove(callback);
                }
            }
        }catch (Exception e){}
    }

    @Override
    public synchronized void play() throws RemoteException {
        if(status != STATUS_PLAY){
            MusicLog.i(TAG,"play");
            status = STATUS_PLAY;
            progress = 0;
            mHandler.removeCallbacks(playTask);
            mHandler.post(playTask);
        }
    }

    @Override
    public synchronized void stop() throws RemoteException {
        if(status != STATUS_STOP){
            MusicLog.i(TAG,"stop");
            status = STATUS_STOP;
            mHandler.removeCallbacks(playTask);
        }

    }

    private Runnable playTask = new Runnable() {
        @Override
        public void run() {
            progress = progress + 2;
            if(progress>100) progress = 100;
            notifyProgress(progress);
            if(progress < 100){
                mHandler.postDelayed(this,200);
                return;
            }
            status = STATUS_STOP;
        }
    };

    private void notifyProgress(int progress){
        try{
            synchronized(mMusicCallbackList) {
                int size = mMusicCallbackList.size();
                for (int i = 0; i < size; i++) {
                    MusicLog.e(TAG,"notifyProgress:" + progress );
                     mMusicCallbackList.get(i).onProgessChange(progress);
                }
            }
        }catch (Exception e){

        }
    }
}
