package com.clearlove._01_future_vs_completablefuture;

import com.clearlove.utils.CommonUtils;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author promise
 * @date 2024/6/1 - 22:36
 */
public class FutureDemo {

  public static void main(String[] args) throws ExecutionException, InterruptedException {

    ExecutorService executorService = Executors.newFixedThreadPool(5);

    // step1:读取敏感词汇 --> thread1
    Future<String[]> filterWordFuture = executorService.submit(() -> {
      String str = CommonUtils.readFile("filter_words.txt");
      String[] filterWords = str.split(",");
      return filterWords;
    });

    // step2:读取新闻稿 --> thread2
    Future<String> newsFuture = executorService.submit(() -> CommonUtils.readFile("news.txt"));

    // step3:替换操作 --> thread3
    Future<String> replaceFuture = executorService.submit(() -> {
      String[] words = filterWordFuture.get();
      String news = newsFuture.get();
      for (String word : words) {
        if (news.contains(word)) {
          news = news.replace(word, "**");
        }
      }

      return news;


    });

    // step4:打印输出替换后的新闻稿 --> main
    String replaceNews = replaceFuture.get();
    System.out.println("replaceNews: " + replaceNews);

  }

}
