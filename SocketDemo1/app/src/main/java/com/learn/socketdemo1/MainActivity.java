package com.learn.socketdemo1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private SocketServer.MyBinder myBinder;

    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG,"----Service Connected----");
            myBinder = (SocketServer.MyBinder)service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG,"-----Service Disconnected----");
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button server = (Button)findViewById(R.id.btn_server);
        Button accept = (Button)findViewById(R.id.btn_accept);
        accept.setOnClickListener(this);
        server.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_accept:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Log.d("MainActivity","--->Socket通信");
                            acceptServer();
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
            case R.id.btn_server:
                Intent intent = new Intent(MainActivity.this,SocketServer.class);
                bindService(intent,conn, Service.BIND_AUTO_CREATE);
                try {
                    myBinder.serverSocket();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }

    }

    private void acceptServer() throws IOException {
        //创建客户端Socket,指定服务器端的地址和端口
        Socket socket = new Socket("10.1.23.108",12345);

        //获取输出流,向服务器端发送信息
        OutputStream os = socket.getOutputStream();  //字节输出流

        //将输出流包装为打印流
        PrintWriter printWriter = new PrintWriter(os);

        //获取客户端的IP地址
        InetAddress address = InetAddress.getLocalHost();
        String ip = address.getHostAddress();
        printWriter.write("客户端：" + ip + "接入服务器");
        printWriter.flush();
        socket.shutdownOutput(); //关闭输出流
        socket.close();
    }
}