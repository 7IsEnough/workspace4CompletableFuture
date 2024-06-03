package com.clearlove._04_completablefuture_arrange;

import com.clearlove.utils.CommonUtils;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author promise
 * @date 2024/6/3 - 23:58
 */
public class AnyOfDemo01 {

  public static void main(String[] args) throws ExecutionException, InterruptedException {

    // anyOf
    CompletableFuture<String> future1 =
        CompletableFuture.supplyAsync(
            () -> {
              CommonUtils.sleepSecond(2);
              return "Future1的结果";
            });

    CompletableFuture<String> future2 =
        CompletableFuture.supplyAsync(
            () -> {
              CommonUtils.sleepSecond(1);
              return "Future2的结果";
            });

    CompletableFuture<String> future3 =
        CompletableFuture.supplyAsync(
            () -> {
              CommonUtils.sleepSecond(3);
              return "Future3的结果";
            });

    CompletableFuture<Object> anyOfFuture = CompletableFuture.anyOf(future1, future2, future3);

    System.out.println("result = " + anyOfFuture.get());
  }
}
