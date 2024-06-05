package com.clearlove._02_completablefuture_create;

import com.clearlove.utils.CommonUtils;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author promise
 * @date 2024/6/2 - 21:12
 */
public class SupplyAsyncDemo01 {

  public static void main(String[] args) throws ExecutionException, InterruptedException {

    // 开启异步任务读取 news.txt 文件中的新闻稿，返回文件中内容并在主线程打印输出
    CommonUtils.printThreadLog("main start");

    CompletableFuture<String> newsFuture = CompletableFuture.supplyAsync(
        () -> {
          CommonUtils.printThreadLog("task run");
        return CommonUtils.readFile("news.txt");
        });

    CommonUtils.printThreadLog("here are not blocked, main continue");
    String news = newsFuture.get();
    System.out.println("news: " + news);
    CommonUtils.printThreadLog("main end");
  }

}
