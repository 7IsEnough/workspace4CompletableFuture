package com.clearlove._03_completablefuture_callback;

import com.clearlove.utils.CommonUtils;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author promise
 * @date 2024/6/3 - 0:43
 */
public class ThenApplyAsyncDemo01 {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    // 需求：异步读取 filter_words.txt 文件中的内容，读取完成后，把内容转换成数组(敏感词数组)，主线程获取异步任务结果并打印敏感词数组

    CommonUtils.printThreadLog("main start");

    ExecutorService executorService = Executors.newFixedThreadPool(4);

    CompletableFuture<String[]> filterWordsFuture = CompletableFuture.supplyAsync(
        () -> CommonUtils.readFile("filter_words.txt")).thenApplyAsync((content) -> {
      String[] filterWords = content.split(",");
      return filterWords;
    }, executorService);

    CommonUtils.printThreadLog("main continue");
    String[] filterWords = filterWordsFuture.get();
    CommonUtils.printThreadLog("filterWords " + Arrays.toString(filterWords));
    executorService.shutdown();
    CommonUtils.printThreadLog("main end");

    /**
     * 一般而言，CommonPool为了提高性能
     * thenApply 中回调任务和supplyAsync中的异步任务使用的是同一个线程
     * 特殊情况：如果supplyAsync中的任务是立即返回结果（不是耗时的任务），thenApply回调任务会在主线程执行
     */


  }

}
