package com.example.sqwdemo.CompletableFuture;

import java.util.concurrent.CompletableFuture;

/**
 * CompletableFuture特性
 * 参考：https://www.liaoxuefeng.com/wiki/1252599548343744/1306581182447650#0
 * https://www.cnblogs.com/cjsblog/p/9267163.html
 *
 * @author shenqingwen
 * @date 2021/8/12
 */
public class CompletableFutureMain {
    public static void main(String[] args) throws Exception {
        // 创建异步执行任务:
        CompletableFuture<Double> cf = CompletableFuture.supplyAsync(CompletableFutureMain::fetchPrice);
//        CompletableFuture<Double> cf = CompletableFuture.supplyAsync(new Supplier<>() {
//            public Double get() {
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException("延时中断");
//                }
//                if (Math.random() < 0.3) {
//                    throw new RuntimeException("fetch price failed!");
//                }
//                return 5 + Math.random() * 20;
//            }
//        });

        // 如果执行成功:
        cf.thenAccept((result) -> {
            System.out.println("price: " + result);
        });

        // 如果执行异常:
        cf.exceptionally((e) -> {
            e.printStackTrace();
            return null;
        });

        // 主线程不要立刻结束，否则CompletableFuture默认使用的线程池会立刻关闭:
        Thread.sleep(200);
    }

    static Double fetchPrice() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException("延时中断");
        }
        if (Math.random() < 0.3) {
            throw new RuntimeException("fetch price failed!");
        }
        return 5 + Math.random() * 20;
    }
}
