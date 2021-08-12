package com.example.sqwdemo.ThreadRunnable;

/**
 * Thread 和 Runnable 的区别
 * 参考：https://blog.csdn.net/zhaojianting/article/details/97664370
 *
 * @author shenqingwen
 * @date 2021/8/12
 */
public class ThreadRunnableTest extends Thread {

    static class MyThread extends Thread {
        private int ticket = 5;

        public void run() {
            for (int i = 0; i < 10; i++) {
                synchronized (this) {
                    if (this.ticket > 0) {
                        try {
                            Thread.sleep(100);
                            System.out.println(Thread.currentThread().getName() + "卖票---->" + (this.ticket--));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    static class MyThread2 implements Runnable {
        private int ticket = 5;

        public void run() {
            do {
                System.out.println(Thread.currentThread().getName() + "--Runnable ticket = " + ticket--);
            } while (ticket > 0);
        }
    }

    public static void main(String[] arg) {

        //创建了两个MyThread对象，每个对象都有自己的ticket成员变量，会进行各自卖票
        //如果把ticket定义为static类型，就离正确结果有近了一步（因为是多线程同时访问一个变量会有同步问题，加上锁才是最终正确的代码）
//        new MyThread().start();
//        new MyThread().start();

        //只创建了一个Runnable对象，两个Thread对象共享资源ticket，肯定每张票只卖一次（但也会有多线程同步问题，同样需要加锁）
//        MyThread2 t2 = new MyThread2();
//        new Thread(t2, "线程A").start();
//        new Thread(t2, "线程B").start();

        //使用Thread方式正确卖票的例子,只创建了一个Thread对象,效果和Runnable一样。synchronized这个关键字是必须的，否则会出现同步问题
        MyThread t1 = new MyThread();
        new Thread(t1, "线程1").start();
        new Thread(t1, "线程2").start();
    }

}

