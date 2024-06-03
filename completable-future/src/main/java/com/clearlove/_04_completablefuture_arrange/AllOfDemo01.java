package com.clearlove._04_completablefuture_arrange;

import com.clearlove.utils.CommonUtils;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * @author promise
 * @date 2024/6/3 - 22:11
 */
public class AllOfDemo01 {

  public static CompletableFuture<String> readFileFuture(String fileName) {
    return CompletableFuture.supplyAsync(() -> CommonUtils.readFile(fileName));
  }

  public static void main(String[] args) {

    // 需求：统计news1.txt，news2.txt，news3.txt 文件中包含CompletableFuture关键字的文件的个数

    // step1:创建List集合存储文件名
    List<String> fileNameList = Arrays.asList("news1.txt","news2.txt","news3.txt");

    // step2:根据文件名调用readFileFuture创建多个CompletableFuture,并存入List集合中
    List<CompletableFuture<String>> readFileFutureList = fileNameList.stream().map(
        AllOfDemo01::readFileFuture).collect(Collectors.toList());

    // step3:把List集合转换成数组待用，以便传入allOf方法中
    CompletableFuture[] readFileFutureArr = readFileFutureList.toArray(
        new CompletableFuture[0]);

    // step4:使用allOf方法合并多个异步任务
    CompletableFuture<Void> allOfFuture = CompletableFuture.allOf(readFileFutureArr);

    // step5:当多个异步任务都完成后，使用回调操作文件结果
    CompletableFuture<Long> countFuture = allOfFuture.thenApply(v -> readFileFutureList.stream().map(
        CompletableFuture::join).filter(content -> content.contains("CompletableFuture")).count());

    // step6:主线程打印输出文件个数
    System.out.println("count = " + countFuture.join());

    /**
     * allOf  特别适合合并多个异步任务，当所有异步任务都完成时可以进一步操作
     */
  }

}
