package com.example.sqwdemo.CompletableFuture;

import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author shenqingwen
 * @date 2021/8/12
 */
@Component
public class CompletableTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = new CompletableFuture<>();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("CompletableFuture 可以监控这个任务的执行");
//                future.complete("任务返回结果");
//            }
//        }).start();
        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }

            System.out.println("CompletableFuture 可以监控这个任务的执行");
            future.complete("任务返回结果");
        }).start();

        CompletableFuture<Void> future2 = CompletableFuture.runAsync(CompletableTest::print);

        System.out.println(future.get());
        System.out.println(future2.get());
        System.out.println("任务即将完成");
    }

    public static void print() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        System.out.println("测试二输出");
    }
}
