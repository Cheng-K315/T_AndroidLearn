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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private SocketServer.MyBinder myBinder;
    private Boolean isBind = false;

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
        Button accept = (Button)findViewById(R.id.btn_client);
        Button bind = (Button)findViewById(R.id.btn_bind);
        Button unbind = (Button)findViewById(R.id.btn_unbind);
        Button udpServer = (Button)findViewById(R.id.btn_udpServer);
        Button udpClient = (Button)findViewById(R.id.btn_udpClient);

        accept.setOnClickListener(this);
        server.setOnClickListener(this);
        bind.setOnClickListener(this);
        unbind.setOnClickListener(this);
        udpServer.setOnClickListener(this);
        udpClient.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_bind:
                Intent intent = new Intent(MainActivity.this, SocketServer.class);
                isBind = bindService(intent, conn, Service.BIND_AUTO_CREATE);
                Log.d(TAG, "isBind--->" + isBind);
                break;
            case R.id.btn_unbind:
                if (isBind) {
                    Log.d(TAG, "isBind--->" + isBind);
                    unbindService(conn);
                    isBind = false;
                }
                break;
            case R.id.btn_client:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Log.d("MainActivity", "--->Socket通信");
                            acceptServer();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
            case R.id.btn_server:
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            myBinder.serverSocket();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                break;
            case R.id.btn_udpServer:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            myBinder.udpServer();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
            case R.id.btn_udpClient:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Log.d("MainActivity","UDPClient");
                            udpClient();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
        }
    }

    private void acceptServer() throws IOException {
        //创建客户端Socket,指定服务器端的地址和端口
        Socket socket = new Socket("127.0.0.1",12345);

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

        //获取输入流，读取服务端反馈回来的信息
        BufferedReader bf = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
        String serverInfo = null;
        while ((serverInfo = bf.readLine()) != null){
            System.out.println("客户端--->收到服务端反馈回来的信息：" + serverInfo);
        }
//        socket.shutdownInput();//关闭输入流
        socket.close();
    }

    private void udpClient() throws IOException{
        /**
         * 向服务器端发送数据
         */
        //定义服务器的地址、端口号、数据
        InetAddress address = InetAddress.getByName("localhost");
        System.out.println(address);
        int port = 12345;
        byte[] date = "用户名：admin；密码：123".getBytes();

        //创建数据报，包含发送的数据信息
        DatagramPacket packet = new DatagramPacket(date,date.length,address,port);

        //创建DataSocket对象
        DatagramSocket socket = new DatagramSocket();

        //向服务器端发送数据报
        socket.send(packet);

        /**
         * 接收服务器端响应的数据信息
         */
        //创建数据报，用于接收服务器端响应的数据
        byte[] date1 = new byte[1024];
        DatagramPacket packet1 = new DatagramPacket(date1,date1.length);

        //接收服务器响应的数据
        socket.receive(packet1);

        //读取数据
        String returnInfo = new String(date1,0,date1.length);
        System.out.println("客户端--->收到服务端响应的数据：" + returnInfo);

        //关闭资源
        socket.close();
    }

    //如果界面被销毁了，服务也随之解绑停止
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isBind){
            unbindService(conn);
            isBind = false;
        }
    }
}