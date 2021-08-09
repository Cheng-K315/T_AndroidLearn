package com.learn.asynctask;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    private TextView show;
    private Button download;
    private ProgressBar pb_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        show = (TextView)findViewById(R.id.tv_show);
        pb_bar = (ProgressBar)findViewById(R.id.pb_bar);
        download = (Button)findViewById(R.id.btn_download);

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownTask downTask = new DownTask(show,pb_bar);
                try {
                    downTask.execute(new URL("https://fkjava.org/2019/10/01/xml3/"));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });


    }
}