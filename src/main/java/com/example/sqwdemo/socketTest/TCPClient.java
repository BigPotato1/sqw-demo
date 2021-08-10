package com.example.sqwdemo.socketTest;

import java.io.*;
import java.net.Socket;

/**
 * @author shenqingwen
 * @date 2021/8/10
 */
public class TCPClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 65512);
        OutputStream out = socket.getOutputStream();
        //创建文件对象     如果文件路径写错   Client报找不到文件异常,Server报Connection Reset异常
        File file = new File("G:\\sockettest\\010.jpg");
        //字节输入流,读取本地文件到java程序中
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[1024];              //数组,增强传输效率
        int len;
        while ((len = fis.read(data)) != -1)   //read方法返回数组的有效字符个数
            out.write(data, 0, len);
        socket.shutdownOutput();  //数据传输完毕,关闭socket输出流,避免服务器端read方法阻塞

        InputStream in = socket.getInputStream();      //字节输入流,读取服务器返回的数据
        len = in.read(data);
        System.out.println(new String(data, 0, len));

        socket.close();
        fis.close();
    }
}
