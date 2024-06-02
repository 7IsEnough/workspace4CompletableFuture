package com.clearlove._02_completablefuture_create;

import com.clearlove.utils.CommonUtils;
import java.util.concurrent.CompletableFuture;

/**
 * @author promise
 * @date 2024/6/1 - 23:14
 */
public class RunAsyncDemo02 {

  public static void main(String[] args) {

    // 读取news.txt文件的新闻稿，并打印输出
    CommonUtils.printThreadLog("main start");


    CompletableFuture.runAsync(() -> {
      CommonUtils.printThreadLog("读取文件");
      String content = CommonUtils.readFile("news.txt");
      System.out.println(content);
    });



    CommonUtils.printThreadLog("here are not blocked, main continue");
    CommonUtils.sleepSecond(4);
    CommonUtils.printThreadLog("main end");

    /**
     * 单核CPU，异步任务是并发执行。多核CPU，异步任务是并行执行。
     * CPU硬件会把异步任务合理的分配给CPU上的核运行
     */



  }

}
