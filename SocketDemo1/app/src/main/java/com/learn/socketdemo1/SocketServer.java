package com.learn.socketdemo1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
    public static void main(String[] args) throws IOException {
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
        String info = br.readLine();

        //循环读取客户端信息
        while (info!=null){
            System.out.println("收到客户端发来信息：" + info);
        }
        socket.shutdownInput();  //关闭输入流
        socket.close();    //关闭Socket
    }
}
