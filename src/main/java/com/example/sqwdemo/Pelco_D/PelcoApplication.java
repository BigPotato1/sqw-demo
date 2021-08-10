package com.example.sqwdemo.Pelco_D;

/**
 * PELCO-D控制协议 java socket 传输16进制的编码
 * 参考：https://blog.csdn.net/alafqq/article/details/84808419
 *
 * @author shenqingwen
 * @date 2021/8/10
 */

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class PelcoApplication {

    public static void main(String[] args) {
        Socket s;
        try {
            s = new Socket("192.168.1.57", 4196);

            DataOutputStream out = new DataOutputStream(s.getOutputStream());

            byte[] b = getCommandByDegrees(100);
            out.write(b);
            out.flush();
            out.close();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //算法
    //假设转99度；就是99*100 转成 16进制-->得到2个数字
    //入参：度数；返回:命令 云台协议
    public static byte[] getCommandByDegrees(int du) {
        byte[] b = new byte[10];
        b[0] = (byte) 0xff;
        b[1] = (byte) 0x01;
        b[2] = (byte) 0x00;
        b[3] = (byte) 0x4b;
        b[4] = (byte) 0x00;//0x17
        b[5] = (byte) 0x00;//0xDB
        b[6] = (byte) 0x9e;
        //byte[] b = BitConverter.GetBytes(
        //   0xba5eba11 );

        String str = Integer.toHexString(du * 100);
        System.out.println(du);
        System.out.println(Integer.toHexString(du));
        int s4 = Integer.parseInt(str.substring(0, 2));
        int s5 = Integer.parseInt(str.substring(2, 4));
        System.out.println();
        b[4] = (byte) s4;
        b[5] = (byte) s5;

        //q前面值相加对256取余数，校驗
        int sum = 0;
        for (int i = 1; i < 6; i++) {
            sum = sum + b[i];
        }
        int y = sum % 256;
        //System.out.println(y+"--"+Integer.valueOf("3E",16));
        int s6 = Integer.parseInt(Integer.toHexString(y));
        b[6] = (byte) s6;
        return b;
    }

}
