package com.clearlove._03_completablefuture_callback;

import com.clearlove.utils.CommonUtils;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author promise
 * @date 2024/6/3 - 0:43
 */
public class ThenApplyDemo01 {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    // 需求：异步读取 filter_words.txt 文件中的内容，读取完成后，把内容转换成数组(敏感词数组)，异步任务返回敏感词数组

    CommonUtils.printThreadLog("main start");

    CompletableFuture<String> readFileFuture = CompletableFuture.supplyAsync(() -> CommonUtils.readFile("filter_words.txt"));

    CompletableFuture<String[]> filterWordsFuture = readFileFuture.thenApply((content) -> {
      String[] filterWords = content.split(",");
      return filterWords;
    });

    CommonUtils.printThreadLog("main continue");
    String[] filterWords = filterWordsFuture.get();
    CommonUtils.printThreadLog("filterWords " + Arrays.toString(filterWords));
    CommonUtils.printThreadLog("main end");

    /**
     * thenApply 可以对异步任务的结果进一步应用 Function 转换
     * 转换后的结果可以在主线程获取，也可以进行下一步的转换
     */


  }

}
