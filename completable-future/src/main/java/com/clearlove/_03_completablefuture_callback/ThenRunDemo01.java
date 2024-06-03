package com.clearlove._03_completablefuture_callback;

import com.clearlove.utils.CommonUtils;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author promise
 * @date 2024/6/3 - 0:43
 */
public class ThenRunDemo01 {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    // 需求：仅仅只想知道敏感词汇的文件是否读取完成

    CommonUtils.printThreadLog("main start");

    CompletableFuture.supplyAsync(
            () -> CommonUtils.readFile("filter_words.txt"))
        .thenRun(() -> CommonUtils.printThreadLog("文件读取完成"));

    CommonUtils.printThreadLog("main continue");
    CommonUtils.sleepSecond(4);
    CommonUtils.printThreadLog("main end");

    /**
     * thenRun 当异步任务完成后，只想得到一个完成的通知，不使用上一步异步任务的结果
     * 通常放在链式操作的末端
     */

  }

}
