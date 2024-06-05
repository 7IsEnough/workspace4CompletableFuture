package com.clearlove._08_parallelstream_completablefuture;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author promise
 * @date 2024/6/5 - 18:14
 */
public class CompletableFutureDemo {

  public static void main(String[] args) {
    // 需求：创建10个MyTask耗时的任务，统计它们执行完的总耗时

    // 方案三：使用CompletableFuture
    IntStream intStream = IntStream.range(0, 10);
    List<MyTask> tasks =
        intStream
            .mapToObj(
                item -> {
                  return new MyTask(1);
                })
            .collect(Collectors.toList());

    long start = System.currentTimeMillis();

    List<CompletableFuture<Integer>> futures =
        tasks.stream()
            .map(
                task -> {
                  return CompletableFuture.supplyAsync(
                      () -> {
                        return task.doWork();
                      });
                })
            .collect(Collectors.toList());


    List<Integer> results = futures.stream().map(CompletableFuture::join)
        .collect(Collectors.toList());

    long end = System.currentTimeMillis();

    double costTime = (end - start) / 1000.0;
    System.out.printf("processed %d tasks %.2f second", tasks.size(), costTime);
    // processed 10 tasks 1.03 second
  }
}
