package com.clearlove._02_completablefuture_create;

import com.clearlove.utils.CommonUtils;
import java.util.concurrent.CompletableFuture;

/**
 * @author promise
 * @date 2024/6/1 - 23:14
 */
public class RunAsyncDemo01 {

  public static void main(String[] args) {

    // 线程的创建与启动
//    new Thread(() -> {
//      CommonUtils.printThreadLog("读取文件开始");
//      CommonUtils.sleepSecond(3);
//      CommonUtils.printThreadLog("读取文件结束");
//    }).start();

    /**
     * 开启一个异步任务读取文件
     * 底层：通过开启线程的方式实现的
     */
//    CompletableFuture.runAsync(() -> {
//      CommonUtils.printThreadLog("读取文件开始");
//      CommonUtils.sleepSecond(3);
//      CommonUtils.printThreadLog("读取文件结束");
//    });

    // runAsync 创建异步任务
    CommonUtils.printThreadLog("main start");

    CompletableFuture.runAsync(() -> {
      CommonUtils.printThreadLog("读取文件开始");
      CommonUtils.sleepSecond(3);
      CommonUtils.printThreadLog("读取文件结束");
    });

    CommonUtils.printThreadLog("here are not blocked, main continue");
    // 此处休眠是为了等待CompletableFuture背后的线程池执行完成
    CommonUtils.sleepSecond(4);
    CommonUtils.printThreadLog("main end");


  }

}
