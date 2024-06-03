package com.clearlove._05_completablefuture_exception;

import com.clearlove.utils.CommonUtils;
import java.util.concurrent.CompletableFuture;

/**
 * @author promise
 * @date 2024/6/4 - 0:44
 */
public class ExceptionallyDemo01 {

  public static void main(String[] args) {
    // 异常如何在回调链中传播

    CompletableFuture<String> future =
        CompletableFuture.supplyAsync(() -> "result1")
            .thenApply(result -> {
              int i = 1 / 0;
              return result + " result2";
            })
            .thenApply(result -> result + " result3")
            .exceptionally(ex -> {
              System.out.println("出现异常：" + ex.getMessage());
              return "Unknown";
            });

  }
}
