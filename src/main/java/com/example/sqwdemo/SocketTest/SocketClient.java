package com.example.sqwdemo.SocketTest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author shenqingwen
 * @date 2021/8/13
 */
public class SocketClient {
    public static void main(String[] args) {
        try {
            // 向本机的4700端口发出客户请求
            Socket socket = new Socket("127.0.0.1", 4700);

            // 由系统标准输入设备构造BufferedReader对象
            BufferedReader sin = new BufferedReader(new InputStreamReader(System.in));

            // 由Socket对象得到输出流，并构造PrintWriter对象
            PrintWriter os = new PrintWriter(socket.getOutputStream());

            // 由Socket对象得到输入流，并构造相应的BufferedReader对象
            BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            System.out.println("Client------------------------------");

            String readline;

            // 从系统标准输入读入一字符串
            readline = sin.readLine();

            // 若从标准输入读入的字符串为 "bye"则停止循环
            while (!readline.equals("bye")) {

                // 将从系统标准输入读入的字符串输出到Server
                os.println(readline);

                // 刷新输出流，使Server马上收到该字符串
                os.flush();

                // 从Server读入一字符串，并打印到标准输出上
                System.out.println("Server:" + is.readLine());

                // 从系统标准输入读入一字符串
                readline = sin.readLine();

            } // 继续循环

            os.close(); // 关闭Socket输出流

            is.close(); // 关闭Socket输入流

            socket.close(); //关闭Socket

        } catch (Exception e) {
            System.out.println("Error" + e); //出错，则打印出错信息
        }
    }
}
