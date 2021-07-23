package com.learn.socketdemo1;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer extends Service {

    private static final String TAG = "SocketServer";
    MyBinder binder = new MyBinder();
    public class MyBinder extends Binder{
        public void serverSocket() throws IOException {
            //创建一个服务器端Socket，即ServerSocket,指定绑定的端口，并监听此端口
            ServerSocket serverSocket = new ServerSocket(12345);
            InetAddress address = InetAddress.getLocalHost();
            String ip = address.getHostAddress();
            Socket socket = null;

            //调用accept()等待客户端连接
            System.out.println("服务端已准备就绪，等待客户端发送请求！服务端IP地址为：" + ip);
            socket = serverSocket.accept();

            //连接后获取输入流，读取客户端信息
            InputStream is = null;
            InputStreamReader isr = null;
            BufferedReader br =null;
            OutputStream os = null;
            PrintWriter pw = null;

            is = socket.getInputStream();  //获取输入流
            isr = new InputStreamReader(is,"UTF-8");
            br = new BufferedReader(isr);
            String clientInfo = br.readLine();

            //循环读取客户端信息
            while (clientInfo!=null){
                System.out.println("收到客户端发来信息：" + clientInfo);
                break;
            }
            socket.shutdownInput();  //关闭输入流

            //获取输出流，反馈给客户端信息
            os = socket.getOutputStream();
            pw = new PrintWriter(os);
            pw.write("服务器端"+ip + "已收到消息！");
            pw.flush();

            socket.shutdownOutput();  //关闭输出流
            socket.close();    //关闭Socket
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG,"onBind被调用");
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG,"onUnbind被调用！");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG,"onDestroy被调用！");
        super.onDestroy();
    }
}
