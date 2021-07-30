package com.learn.alarmtest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;

public class AlarmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        //加载指定音乐，并为之创建MediaPlayer对象
        MediaPlayer player = MediaPlayer.create(this,R.raw.ring);
        player.setLooping(true);
        //播放音乐
        player.start();

        new AlertDialog.Builder(AlarmActivity.this).setTitle("闹钟")
                .setMessage("闹钟响了!")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //停止音乐
                        player.stop();

                        //结束该Activity
                        AlarmActivity.this.finish();
                    }
                }).show();
    }
}