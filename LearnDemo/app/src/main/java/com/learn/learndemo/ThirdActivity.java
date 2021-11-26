package com.learn.learndemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ThirdActivity extends BaseActivity {

    private static final String TAG = "ThirdActivity";
    private Button mBtn_start;
    private Button mBtn_stop;
    private Button mBtn_bind;
    private Button mBtn_unbind;
    private Button mBtn_status;
    private Button insert;
    private Button search;
    private Button delete;
    private Button update;
    TestStartService.MyBinder myBinder;
    private boolean isBound = false;//定义一个值判断ServiceConnection是否为空

    private ServiceConnection connection = new ServiceConnection() {
        //Activity与Service连接成功时回调该方法
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "-----Service Connected------");
            myBinder = (TestStartService.MyBinder) service;
        }

        //Activity与Service断开连接时回调该方法
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "-----Service DisConnected------");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate! and task id is " + this.getTaskId());
        setContentView(R.layout.activity_third);
        initView();
        setLisener();
    }

    private void initView() {
        mBtn_start = (Button)findViewById(R.id.btn_startService);
        mBtn_stop = (Button)findViewById(R.id.btn_stopService);
        mBtn_bind = (Button)findViewById(R.id.btn_bindService);
        mBtn_unbind = (Button)findViewById(R.id.btn_cancelService);
        mBtn_status = (Button)findViewById(R.id.btn_statusService);
        insert = findViewById(R.id.btn_firstInsert);
        delete = findViewById(R.id.btn_firstDelete);
        update = findViewById(R.id.btn_firstUpdate);
        search = findViewById(R.id.btn_firstSearch);
    }

    private void setLisener(){
        OnClick onClick = new OnClick();
        mBtn_start.setOnClickListener(onClick);
        mBtn_stop.setOnClickListener(onClick);
        mBtn_bind.setOnClickListener(onClick);
        mBtn_unbind.setOnClickListener(onClick);
        mBtn_status.setOnClickListener(onClick);
        insert.setOnClickListener(onClick);
        delete.setOnClickListener(onClick);
        update.setOnClickListener(onClick);
        search.setOnClickListener(onClick);
    }

    private class OnClick implements View.OnClickListener{
        private Uri uri = Uri.parse("content://com.learn.learndemo.firstprovider");
        Wacher wacher = new Wacher(new Handler());

        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setAction("com.learn.learndemo.myservice");
            intent.setPackage("com.learn.learndemo");
            switch (v.getId()){
                //启动service
                case R.id.btn_startService :
//                    intent = new Intent(ThirdActivity.this,TestStartService.class);
                    startService(intent);
                    break;
                    //停止service
                case R.id.btn_stopService :
                    stopService(intent);
                    break;
                    //绑定service
                case R.id.btn_bindService :
                    isBound = bindService(intent,connection, Service.BIND_AUTO_CREATE);
                    break;
                    //解除service绑定
                case R.id.btn_cancelService :
                    if(isBound){
                        unbindService(connection);
                        isBound = false;
                    }
                    break;
                case R.id.btn_statusService :
                    Toast.makeText(getApplicationContext(),"Service的count值为："+myBinder.getCount(),Toast.LENGTH_LONG).show();
                    break;
                case R.id.btn_firstInsert :
                    ContentValues values = new ContentValues();
                    values.put("name","android");
                    //调用ContentResolver的insert()方法
                    //实际返回的是该Uri对应的ContentProvider的insert()的返回值
                    Uri newUri = getContentResolver().insert(uri,values);
                    Toast.makeText(ThirdActivity.this,"远程ContentProvider新插入纪录的Uri为：" + newUri,Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btn_firstDelete :
                    int num = getContentResolver().delete(uri,"delete_where",null);
                    Toast.makeText(ThirdActivity.this,"远程ContentProvider删除记录数为：" + num,Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btn_firstUpdate :
                    ContentValues values1 = new ContentValues();
                    values1.put("name","android");
                    int num1 = getContentResolver().update(uri,values1,"update_where",null);
                    Toast.makeText(ThirdActivity.this,"远程ContentProvider更新记录数为："+num1,Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btn_firstSearch :
                    Cursor cursor = getContentResolver().query(uri,null,"query_where",null,null);
                    Toast.makeText(ThirdActivity.this,"远程ContentProvider返回的Cursor为：",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    //Activity生命周期
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"onStart! and task id is "+this.getTaskId());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"onResume! and task id is "+this.getTaskId());
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"onPause! and task id is "+this.getTaskId());
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"onStop! and task id is "+this.getTaskId());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //如果界面被销毁了，服务也随之解绑停止
        if(isBound){
            unbindService(connection);
            isBound = false;
        }
        Log.d(TAG,"onDestroy! and task id is "+this.getTaskId());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG,"onRestart! and task id is "+this.getTaskId());
    }

    class Wacher extends ContentObserver {
        /**
         * Creates a content observer.
         *
         * @param handler The handler to run {@link #onChange} on, or null if none.
         */
        public Wacher(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
        }
    }
}