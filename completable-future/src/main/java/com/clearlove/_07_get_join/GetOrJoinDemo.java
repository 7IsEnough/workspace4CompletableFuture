package com.clearlove._07_get_join;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author promise
 * @date 2024/6/5 - 15:03
 */
public class GetOrJoinDemo {

  public static void main(String[] args) {
    CompletableFuture<String> future =
        CompletableFuture.supplyAsync(
            () -> {
              return "hello";
            });

    try {
      String result = future.get();
      System.out.println("result = " + result);
    } catch (Exception e) {
      e.printStackTrace();
    }

    String result = future.join();
    System.out.println("result = " + result);
  }
}
