package com.clearlove._08_parallelstream_completablefuture;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author promise
 * @date 2024/6/5 - 17:59
 */
public class ParallelStreamDemo {
  public static void main(String[] args) {
    // 需求：创建10个MyTask耗时的任务，统计它们执行完的总耗时

    // 方案二：使用并行流
    IntStream intStream = IntStream.range(0, 10);
    List<MyTask> tasks =
        intStream
            .mapToObj(
                item -> {
                  return new MyTask(1);
                })
            .collect(Collectors.toList());

    long start = System.currentTimeMillis();
    List<Integer> results =
        tasks.parallelStream()
            .map(
                task -> {
                  return task.doWork();
                })
            .collect(Collectors.toList());
    long end = System.currentTimeMillis();

    double costTime = (end - start) / 1000.0;
    System.out.printf("processed %d tasks %.2f second", tasks.size(), costTime);
    // processed 10 tasks 1.03 second
  }
}
