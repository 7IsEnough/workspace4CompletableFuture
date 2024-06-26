package com.clearlove._06_completablefuture_interaction;

import com.clearlove.utils.CommonUtils;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

/**
 * @author promise
 * @date 2024/6/5 - 14:46
 */
public class RunAfterEitherDemo01 {

  public static void main(String[] args) {
    CompletableFuture<Integer> future1 =
        CompletableFuture.supplyAsync(
            () -> {
              int x = new Random().nextInt(3);
              CommonUtils.sleepSecond(x);
              CommonUtils.printThreadLog("任务1耗时：" + x + " 秒");
              return x;
            });

    // 异步任务2
    CompletableFuture<Integer> future2 =
        CompletableFuture.supplyAsync(
            () -> {
              int y = new Random().nextInt(3);
              CommonUtils.sleepSecond(y);
              CommonUtils.printThreadLog("任务2耗时：" + y + " 秒");
              return y;
            });

    future1.runAfterEither(
        future2,
        () -> {
          CommonUtils.printThreadLog("有一个异步任务完成");
        });

    CommonUtils.sleepSecond(4);

    /**
     * thenApply thenAccept thenRun 对上一个异步任务的结果进行操作（转换、消费使用等）
     *
     * applyToEither acceptEither runAfterEither 对两个异步任务先到的结果进行操作（转换，消费使用）
     */
  }
}
