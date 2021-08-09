package com.learn.alarmtest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity{

    private AlarmManager aManager;
    private Button btn_setTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        aManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        btn_setTime = (Button)findViewById(R.id.btn_setTime);
        btn_setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar currentTime = Calendar.getInstance();
                Log.d("MainActivity","初始时间："+currentTime.get(Calendar.HOUR_OF_DAY)+"时"+currentTime.get(Calendar.MINUTE)+"分");
                Date time = new Date();
//                currentTime.setTimeInMillis(System.currentTimeMillis());
                Log.d("MainActivity",""+time);
                //创建一个TimePickerDialog实例，并把它显示出来
                new TimePickerDialog(MainActivity.this, 0, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Intent intent = new Intent(MainActivity.this,AlarmActivity.class);
                        PendingIntent pi = PendingIntent.getActivity(MainActivity.this,0,intent,0);
                        Calendar c = Calendar.getInstance();
                        //根据用户选择时间来设置Calender对象
                        c.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        c.set(Calendar.MINUTE,minute);
                        Log.d("MainActivity","闹钟时间："+c.get(Calendar.HOUR_OF_DAY)+"时"+c.get(Calendar.MINUTE)+"分"+c.getTimeInMillis());

                        //设置AlarmManager将在Calendar对应的时间启动指定组件
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){  //6.0及以上
                            aManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pi);
                        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){  //4.4及以上
                            aManager.setExact(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pi);
                        }else {
                            aManager.set(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pi);
                        }
                        //显示闹钟设置成功的提示信息
                        Toast.makeText(MainActivity.this,"闹钟设置成功！",Toast.LENGTH_SHORT).show();
                    }
                },currentTime.get(Calendar.HOUR_OF_DAY),
                        currentTime.get(Calendar.MINUTE),true).show();
            }
        });
    }
}