package com.clearlove._05_completablefuture_exception;

import com.clearlove.utils.CommonUtils;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author promise
 * @date 2024/6/4 - 16:58
 */
public class HandleDemo02 {

  public static void main(String[] args) throws ExecutionException, InterruptedException {

    // 需求：对回调链中的一次异常进行慨复处理
    CompletableFuture<String> future =
        CompletableFuture.supplyAsync(
                () -> {
                  int r = 1 / 0;
                  return "result1";
                })
            .handle(
                (result, ex) -> {
                  if (ex != null) {
                    System.out.println("出现异常：" + ex.getMessage());
                    return "UnKnown1";
                  }
                  return result;
                })
            .thenApply(
                result -> {
                  String str = null;
                  int len = str.length();
                  return result + " result2";
                })
            .handle(
                (result, ex) -> {
                  if (ex != null) {
                    System.out.println("出现异常：" + ex.getMessage());
                    return "UnKnown2";
                  }
                  return result;
                })
            .thenApply(
                result -> {
                  return result + " result3";
                });
    System.out.println("future.get() = " + future.get());
  }
}
