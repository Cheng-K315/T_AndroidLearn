package com.learn.handler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    int image[] = new int[]{
            R.drawable.s_1,R.drawable.s_2,R.drawable.s_3,
            R.drawable.s_4,R.drawable.s_5,R.drawable.s_6,
            R.drawable.s_7,R.drawable.s_8
    };
    int start = 0;
    private ImageView imageView;
    private TextView tv_num;
    EditText et_num;
//    private List<Integer> nums;

    //Handler写在主线程中
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 0x123:
                    imageView.setImageResource(image[start++ % 8]);
                    break;
                case 1:
                    tv_num.setText("" + msg.getData().getIntegerArrayList("nums"));
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView)findViewById(R.id.image);
        tv_num = (TextView)findViewById(R.id.tv_num);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0x123);
            }
        },0,200);
    }

    public class SonThread extends Thread{
        @Override
        public void run() {
            Message message = new Message();
            message.what = 1;
            et_num = (EditText)findViewById(R.id.et_num);
            int upper = Integer.parseInt(et_num.getText().toString());
            ArrayList<Integer> nums = new ArrayList<>();
            outer:
            for (int i = 2; i <= upper; i++) {
                for (int j = 2; j <= Math.sqrt(i); j++) {
                    if (i != 2 && i % j == 0) {
                        continue outer;
                    }
                }
                nums.add(i);
            }
            Bundle bundle = new Bundle();
            bundle.putIntegerArrayList("nums",nums);
            message.setData(bundle);
            handler.sendMessage(message);
        }
    }

    public void Cal(View view) {
        SonThread thread = new SonThread();
        thread.start();
    }
}