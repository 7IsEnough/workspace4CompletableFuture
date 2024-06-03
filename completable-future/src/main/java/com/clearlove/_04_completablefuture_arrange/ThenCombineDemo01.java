package com.clearlove._04_completablefuture_arrange;

import com.clearlove.utils.CommonUtils;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author promise
 * @date 2024/6/3 - 17:31
 */
public class ThenCombineDemo01 {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    // 需求：替换新闻稿 news.txt 中敏感词汇，把敏感词替换成*，敏感词存储在 filter_words.txt 中
    CommonUtils.printThreadLog("main start");

    // step1:读取敏感词汇 --> thread1
    CompletableFuture<String[]> readFilterWordsFuture = CompletableFuture.supplyAsync(
        () -> CommonUtils.readFile("filter_words.txt").split(","));

    // step2:读取新闻稿 --> thread2
    CompletableFuture<String> readNewsFuture = CompletableFuture.supplyAsync(
        () -> CommonUtils.readFile("news.txt"));

    // step3:替换操作 --> thread3
    ExecutorService executorService = Executors.newFixedThreadPool(4);
    CompletableFuture<String> combineFuture = readFilterWordsFuture.thenCombineAsync(
        readNewsFuture, (filterWords, content) -> {
          CommonUtils.printThreadLog("替换操作");
          for (String word : filterWords) {
            if (content.contains(word)) {
              content = content.replace(word, "**");
            }
          }
          return content;
        }, executorService);

    CommonUtils.printThreadLog("main continue");
    String content = combineFuture.get();
    CommonUtils.printThreadLog("content " + content);
    CommonUtils.printThreadLog("main end");

    /**
     * thenCombine 用于合并2个没有依赖关系的异步任务
     */

  }

}
