package com.luna.client;

import androidx.appcompat.app.AppCompatActivity;


import android.app.Activity;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.luna.proxy.IMusicCalllback;
import com.luna.proxy.bean.MusicBean;
import com.luna.proxy.music.MusicManager;
import com.luna.proxy.utils.MusicLog;

public class MainActivity extends Activity implements View.OnClickListener{

    private static final String TAG = MainActivity.class.getSimpleName();
    private MusicManager mMusicManager;
    private TextView mAlbumTv;
    private Button mPlayBtn;
    private ProgressBar mProgressBar;
    private static final int MSG_UPDATE_PROGRESS = 100;
    private Handler mHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage( Message msg) {
            switch (msg.what){
                case MSG_UPDATE_PROGRESS:
                    mProgressBar.setProgress((int)msg.obj);
                    break;
            }
        }
    };

    private IMusicCalllback mIMusicCalllback =  new IMusicCalllback.Stub(){
        @Override
        public void onProgessChange(int progress) throws RemoteException {
            MusicLog.i(TAG,"onProgessChange :" + progress + ",from:" + Binder.getCallingPid());
            //TODO 1 Message创建方式 1
            Message.obtain(mHandler,MSG_UPDATE_PROGRESS,progress).sendToTarget();

        }
    };

    private Runnable mUpdateTask = new Runnable() {
        @Override
        public void run() {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN|View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY|View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        setContentView(R.layout.activity_main);

        mMusicManager = MusicManager.getInstance();
        mAlbumTv = findViewById(R.id.album_tv);
        mPlayBtn = findViewById(R.id.play_status);
        mProgressBar = findViewById(R.id.progressbar);
        mPlayBtn.setOnClickListener(this);
        MusicLog.i(TAG,"onCreate");
        init();
    }

    private void init(){
        if(mMusicManager != null){
            try{
                MusicBean bean = mMusicManager.getMusicBean();
                if(bean != null){
                    mAlbumTv.setText(bean.getAlbum());
                    mProgressBar.setProgress(bean.getProgress());
                    MusicLog.i(TAG,"init,get bean success , album:" + bean.getAlbum()+",progress:" + bean.getProgress());
                }else {
                    MusicLog.e(TAG,"init get music bean is null");
                }
                mMusicManager.registerCallback(mIMusicCalllback);
                MusicLog.i(TAG,"init ,register callback success");
            }catch (Exception e){
                MusicLog.e(TAG,"init error");
            }
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if(mMusicManager != null){
            try {
                mMusicManager.unRegisterCallback(mIMusicCalllback);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.play_status:
                try {
                    if("播放".equals(mPlayBtn.getText())){
                        if(mMusicManager != null){
                            MusicLog.i(TAG,"play");
                            mMusicManager.play();
                            mPlayBtn.setText("暂停");
                        }
                    }else{
                        MusicLog.i(TAG,"stop");
                        mMusicManager.stop();
                        mPlayBtn.setText("播放");
                    }
                }catch (Exception e){}

                break;
        }
    }
}