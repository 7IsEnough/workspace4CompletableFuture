package com.clearlove._05_completablefuture_exception;

import com.clearlove.utils.CommonUtils;
import java.util.concurrent.CompletableFuture;

/**
 * @author promise
 * @date 2024/6/4 - 0:44
 */
public class ExceptionChainDemo01 {

  public static void main(String[] args) {
    // 异常如何在回调链中传播

    CompletableFuture<Void> future =
        CompletableFuture.supplyAsync(() -> "result1")
            .thenApply(result -> {
              int i = 1 / 0;
              return result + " result2";
            })
            .thenApply(result -> result + " result3")
            .thenAccept(CommonUtils::printThreadLog);

    /**
     * 如果回调链中出现任何异常，回调链不再向下执行，立即转入异常处理
     */
  }
}
