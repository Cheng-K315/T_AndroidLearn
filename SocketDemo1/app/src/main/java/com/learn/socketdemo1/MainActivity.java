package com.learn.socketdemo1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button accept = (Button)findViewById(R.id.btn_accept);
        accept.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    acceptServer();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void acceptServer() throws IOException {
        //创建客户端Socket,指定服务器端的地址和端口
        Socket socket = new Socket("172.16.2.54",12345);

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