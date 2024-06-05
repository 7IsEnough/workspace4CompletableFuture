package com.clearlove._08_parallelstream_completablefuture;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author promise
 * @date 2024/6/5 - 18:14
 */
public class CompletableFutureDemo02 {

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

    int N_CPU = Runtime.getRuntime().availableProcessors();
    ExecutorService executorService =
        Executors.newFixedThreadPool(Math.min(tasks.size(), N_CPU * 2));

    long start = System.currentTimeMillis();

    List<CompletableFuture<Integer>> futures =
        tasks.stream()
            .map(
                task -> {
                  return CompletableFuture.supplyAsync(
                      () -> {
                        return task.doWork();
                      },
                      executorService);
                })
            .collect(Collectors.toList());

    List<Integer> results =
        futures.stream().map(CompletableFuture::join).collect(Collectors.toList());

    long end = System.currentTimeMillis();

    double costTime = (end - start) / 1000.0;
    System.out.printf("processed %d tasks %.2f second", tasks.size(), costTime);
    // processed 10 tasks 1.03 second
    executorService.shutdown();

    /** 总结： CompletableFuture可以控制更多的线程数量，而ParallelStream不能 */
  }
}
