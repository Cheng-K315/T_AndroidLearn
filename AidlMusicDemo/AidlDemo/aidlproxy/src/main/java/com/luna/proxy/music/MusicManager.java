package com.luna.proxy.music;

import android.os.IBinder;
import android.os.RemoteException;

import com.luna.proxy.IMusicCalllback;
import com.luna.proxy.IMusictInterface;
import com.luna.proxy.bean.MusicBean;
import com.luna.proxy.constants.MusicConstants;

public class MusicManager extends ServiceHelper<IMusictInterface> implements IMusictInterface{
    private static MusicManager mMusicManager;
    private MusicManager(){

    }

    public static synchronized MusicManager getInstance(){
        if(mMusicManager == null){
            mMusicManager = new MusicManager();
        }
        if(mMusicManager.mService == null){
            mMusicManager.getService(MusicConstants.MUSIC_SERVICE_NAME);
        }
        return mMusicManager;
    }

    @Override
    protected void dealServiceDied() {
        mService = null;
    }

    @Override
    protected IMusictInterface linkToService(IBinder binder) {
        try {
            mService = IMusictInterface.Stub.asInterface(binder);
        }catch (Exception e){
            e.printStackTrace();
            mService = null;
        }
        return mService;
    }

    @Override
    public boolean assertService() {
        if(mService == null){
            mService = getService(MusicConstants.MUSIC_SERVICE_NAME);
        }
        return (mService == null);
    }


    @Override
    public MusicBean getMusicBean() throws RemoteException {
        if(assertService()) return null;
        return  mService.getMusicBean();
    }

    @Override
    public void setProgress(int progress) throws RemoteException {
        if(assertService()) return ;
        mService.setProgress(progress);
    }

    @Override
    public void registerCallback(IMusicCalllback callback) throws RemoteException {
        if(assertService()) return ;
        mService.registerCallback(callback);
    }

    @Override
    public void unRegisterCallback(IMusicCalllback callback) throws RemoteException {
        if(assertService()) return ;
        mService.unRegisterCallback(callback);
    }

    @Override
    public void play() throws RemoteException {
        if(assertService()) return ;
        mService.play();
    }

    @Override
    public void stop() throws RemoteException {
        if(assertService()) return ;
        mService.stop();
    }

    @Override
    public IBinder asBinder() {
        return null;
    }
}
