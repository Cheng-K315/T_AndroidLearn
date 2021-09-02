package com.learn.httptest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetData {
    public static byte[] getImage(String path) throws Exception {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        //设置连接超时为5秒
        conn.setConnectTimeout(5000);
        //设置请求类型为Get类型
        conn.setRequestMethod("GET");
        //判断请求URL是否成功
        if (conn.getResponseCode()!=200){
            throw new RuntimeException("请求url失败！");
        }
        InputStream inputStream = conn.getInputStream();
        ByteArrayOutputStream bis = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inputStream.read(buffer)) != -1){
            bis.write(buffer,0,len);
        }
        inputStream.close();
        return bis.toByteArray();
    }

    //获取网页的html源代码
    public static String getHtml(String path) throws Exception{
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("GET");
        if (conn.getResponseCode()!=200){
            throw new RuntimeException("请求url失败！");
        }
        InputStream is = conn.getInputStream();
        ByteArrayOutputStream bis = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = is.read(buffer)) != -1){
            bis.write(buffer,0,len);
        }
        byte[] bytes = bis.toByteArray();
        String html = new String(bytes,"UTF-8");
        is.close();
        return html;
    }
}
