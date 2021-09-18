package com.learn.produceanr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 点击后，阻塞主线程，然后进行用户操作
     *
     * 连续点击空白处多次（mix2 上需4次），10s 左右有 ANR 的log，再过 10s 左右弹框
     * 或点击返回键（mix2 上只需1次），10s 左右会有 ANR 的 log，再过 10s 左右弹框
     */
    public void produceANR(View view) {
        sleepTest();
    }


    /**     Service启动超时
     * 点击后，阻塞主线程，再启动一个 Service
     *
     * 20s 左右会有 ANR 的log，再过 10s 左右有 ANR 的弹框
     */
    public void produceANRByService_1(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(3000);
                Intent intent = new Intent(MainActivity.this, MyService.class);
                startService(intent);
            }
        }).start();

        sleepTest();
    }

    /**    Service处理超时
     * 点击后，启动一个 Service，在 Service 内阻塞主线程
     *
     * 20s 左右会有 ANR 的log，再过 10s 左右有 ANR 的弹框
     */
    public void produceANRByService_2(View view) {
        Intent intent = new Intent(MainActivity.this, MyService.class);
        startService(intent);
    }

    /**   Receiver接收超时
     * 点击后，阻塞主线程，注册并发送一个自定义 BroadCast
     *
     * 发送广播后，60s 左右有 ANR 的 log，再过 10s 左右弹框（自定义广播默认是后台广播）
     */
    public void produceANRByOwnBroadCast(View view) {
        IntentFilter filter = new IntentFilter();
        filter.addAction("anr_test");
        registerReceiver(registerReceiver, filter);

        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(2000);
                Intent intent = new Intent("anr_test");
                intent.putExtra("info","huaadsfasf");
                sendBroadcast(intent);
            }
        }).start();

        sleepTest();
    }

    /**
     * 点击后，阻塞主线程，注册一个系统 BroadCast （如 ACTION_TIME_TICK）
     *
     * 系统发送广播后，10s 左右有 ANR 的 log，再过 10s 左右弹框
     */
    public void produceANRBySystemBroadCast(View view) {
        IntentFilter filter = new IntentFilter();
        filter.addAction("anr_test");
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(registerReceiver, filter);
    }


    /**    Receiver处理超时
     * 点击后，注册一个 BroadCast （如 ACTION_TIME_TICK），在广播的处理内阻塞主线程
     *
     * 收到广播后，10s 左右有 ANR 的 log，再过 10s 左右弹框
     */
    public void produceANRByBroadCastHandle(View view) {
        IntentFilter filter = new IntentFilter();
        filter.addAction("anr_test");
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(registerReceiver, filter);
    }

    MyReceiver registerReceiver = new MyReceiver();
    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            sleepTest();
            Toast.makeText(MainActivity.this,"收到广播!",Toast.LENGTH_SHORT).show();
        }
    }


    public void sleepTest() {
        SystemClock.sleep(100000);
    }
}