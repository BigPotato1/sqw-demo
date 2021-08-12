package com.example.sqwdemo.SocketTest;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

/**
 * @author shenqingwen
 * @date 2021/8/10
 */
public class TCPServer {
    public static void main(String[] args) throws IOException {
        //打开服务器制定端口,等待客户端连接
        ServerSocket server = new ServerSocket(65512);
        //获得与服务器相连的套接字对象     套接字:绑定ip地址和端口号的网络对象
        Socket socket = server.accept();
        //查看该地址文件夹是否存在,如果不存在,创建一个
        File file = new File("G:\\sockettest\\upload");
        if (!file.exists()) {
            boolean b = file.mkdirs();
            System.out.println(b);
        }
        //套接字的字节输入流,读取客户端传过来的数据
        InputStream in = socket.getInputStream();

        //随机文件名
        String name = System.currentTimeMillis() + "" + new Random().nextInt(9999);
        //File.separator表示分隔符,windows是\,linux是/
        FileOutputStream fos = new FileOutputStream(file + File.separator + name + ".jpg");
        byte[] data = new byte[1024];
        int len = 0;

        //如果客户端没有关闭socket输出流,这里的read方法会一直读取,对于socket流没有流末尾之说,不可能返回-1
        while ((len = in.read(data)) != -1)
            fos.write(data, 0, len);
        data = "上传成功!".getBytes();      //字符串转化为字节数组
        OutputStream out = socket.getOutputStream();     //创建字节输出流
        out.write(data);                //写入上传成功,反馈给客户端
        server.close();
        fos.close();
    }
}
