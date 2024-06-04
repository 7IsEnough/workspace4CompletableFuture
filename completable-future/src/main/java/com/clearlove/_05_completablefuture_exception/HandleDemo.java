package com.clearlove._05_completablefuture_exception;

import com.clearlove.utils.CommonUtils;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author promise
 * @date 2024/6/4 - 16:58
 */
public class HandleDemo {

  public static void main(String[] args) throws ExecutionException, InterruptedException {

    CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
//      int r = 1 / 0;
      return "result1";
    }).handle((result, ex) -> {
      if (ex != null) {
        // 上一步异常的处理
        CommonUtils.printThreadLog("处理上一步异常：" + ex.getMessage());
        return "UnKnown";
      }
      return result;
    });

    System.out.println("future.get() = " + future.get());
  }

  /**
   * 异步任务下不管是否发生异常，handle方法都会执行
   * handle核心作用在于对上一步异步任务进行现场修复
   */
}
