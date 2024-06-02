package com.clearlove._03_completablefuture_callback;

import com.clearlove.utils.CommonUtils;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author promise
 * @date 2024/6/3 - 0:43
 */
public class ThenAcceptDemo01 {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    // 需求：异步读取 filter_words.txt 文件中的内容，读取完成后，把内容转换成数组(敏感词数组)，然后打印敏感词数组

    CommonUtils.printThreadLog("main start");

    CompletableFuture.supplyAsync(
            () -> CommonUtils.readFile("filter_words.txt")).thenApply((content) -> content.split(","))
        .thenAccept((strArray) -> CommonUtils.printThreadLog("filterWords " + Arrays.toString(strArray)));

    CommonUtils.printThreadLog("main continue");
    CommonUtils.sleepSecond(4);
    CommonUtils.printThreadLog("main end");

    /**
     * thenAccept 可以对异步任务的结果进行消费使用
     * 返回一个不带结果的CompletableFuture对象
     */

  }

}
