package com.learn.asynctask;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class DownTask extends AsyncTask<URL,Integer,String > {

    private TextView show;
    private ProgressBar pb_bar;
    int isRead = 0;

    public DownTask(TextView show,ProgressBar pb_bar) {
        super();
        this.show = show;
        this.pb_bar = pb_bar;
    }

    @Override
    protected void onPreExecute() {
        pb_bar.setVisibility(ProgressBar.INVISIBLE);
        //设置进度条当前值
        pb_bar.setProgress(0);
        //设置进度条的最大值
        pb_bar.setMax(100);
    }

    @Override
    protected String doInBackground(URL... params) {
        StringBuilder sb = new StringBuilder();
        try{
            URLConnection conn = params[0].openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
            String line = null;
            while ((line = br.readLine()) != null){
                sb.append(line + "\n");
                isRead++;
                publishProgress(isRead);
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        //更新进度
        show.setText("已经读取了"+values[0]+"行");
        pb_bar.setProgress(values[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        //返回HTML页面的内容
        show.setText(s);
        pb_bar.setVisibility(ProgressBar.VISIBLE);
    }
}
