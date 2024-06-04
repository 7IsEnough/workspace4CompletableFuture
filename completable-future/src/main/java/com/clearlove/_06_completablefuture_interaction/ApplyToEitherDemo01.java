package com.clearlove._06_completablefuture_interaction;

import com.clearlove.utils.CommonUtils;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author promise
 * @date 2024/6/4 - 17:24
 */
public class ApplyToEitherDemo01 {
  public static void main(String[] args) throws ExecutionException, InterruptedException {
    // 异步任务交互

    // 异步任务1
    CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
      int x = new Random().nextInt(3);
      CommonUtils.sleepSecond(x);
      CommonUtils.printThreadLog("任务1耗时：" + x + " 秒");
      return x;
    });

    // 异步任务2
    CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
      int y = new Random().nextInt(3);
      CommonUtils.sleepSecond(y);
      CommonUtils.printThreadLog("任务2耗时：" + y + " 秒");
      return y;
    });

    CompletableFuture<Integer> resultFuture = future1.applyToEither(future2,
        (result) -> {
          CommonUtils.printThreadLog("最先到达的结果：" + result);
          return result;
        });

    CommonUtils.sleepSecond(3);

    System.out.println("resultFuture.get() = " + resultFuture.get());

    /**
     * applyToEither  两个异步任务，哪个先执行完，就使用哪个结果（先到先用）
     */
  }
}
