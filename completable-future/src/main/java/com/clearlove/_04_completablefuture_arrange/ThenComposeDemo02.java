package com.clearlove._04_completablefuture_arrange;

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
public class ThenComposeDemo02 {


  public static void main(String[] args) throws ExecutionException, InterruptedException {
    // 需求：异步读取 filter_words.txt 文件中的内容，读取完成后，把内容转换成数组(敏感词数组)，异步任务返回敏感词数组

    CommonUtils.printThreadLog("main start");

    ExecutorService executorService = Executors.newFixedThreadPool(4);

    CompletableFuture<String[]> filterWordsFuture = CompletableFuture.supplyAsync(
            () -> CommonUtils.readFile("filter_words.txt"))
        .thenComposeAsync((content) -> CompletableFuture.supplyAsync(() -> content.split(",")), executorService);

    CommonUtils.printThreadLog("main continue");
    String[] filterWords = filterWordsFuture.get();
    CommonUtils.printThreadLog("filterWords " + Arrays.toString(filterWords));
    CommonUtils.printThreadLog("main end");

    /**
     * thenApply
     * 对异步任务的结果进一步应用 Function 转换，得到的结果是一个简单的值
     *
     * thenCompose
     * 对异步任务的结果进一步应用 Function 转换，得到的结果是一个CompletableFuture对象
     *
     * 如果想编排两个依赖关系的异步任务（CompletableFuture对象），使用 thenCompose 方法最好
     */

  }

}
