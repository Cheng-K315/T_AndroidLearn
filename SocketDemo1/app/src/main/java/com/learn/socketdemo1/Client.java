package com.learn.socketdemo1;

import android.renderscript.ScriptGroup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
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

        //获取输入流，读取服务端反馈回来的信息
        InputStream is = socket.getInputStream();
        InputStreamReader isr = new InputStreamReader(is,"UTF-8");
        BufferedReader br= new BufferedReader(isr);
        String serverInfo = br.readLine();
        while (serverInfo != null){
            System.out.println("客户端收到服务端反馈回来的信息：" + serverInfo);
            break;
        }

        socket.shutdownInput();//关闭输入流
        socket.close();
    }
}
