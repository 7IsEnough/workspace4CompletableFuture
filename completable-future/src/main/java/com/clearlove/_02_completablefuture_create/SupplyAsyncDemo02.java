package com.clearlove._02_completablefuture_create;

import com.clearlove.utils.CommonUtils;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author promise
 * @date 2024/6/2 - 21:12
 */
public class SupplyAsyncDemo02 {

  public static void main(String[] args) throws ExecutionException, InterruptedException {

    // 指定线程池，开启异步任务读取 news.txt 文件中的新闻稿，返回文件中内容并在主线程打印输出
    CommonUtils.printThreadLog("main start");

    ExecutorService executorService = Executors.newFixedThreadPool(4);

    CompletableFuture<String> newsFuture = CompletableFuture.supplyAsync(
        () -> CommonUtils.readFile("news.txt"), executorService);

    CommonUtils.printThreadLog("here are not blocked, main continue");
    String news = newsFuture.get();
    System.out.println("news: " + news);
    // 关闭线程池
    executorService.shutdown();
    CommonUtils.printThreadLog("main end");
  }

}
