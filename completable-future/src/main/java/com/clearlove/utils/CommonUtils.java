package com.clearlove.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;

/**
 * @author promise
 * @date 2024/6/1 - 21:53
 */
public class CommonUtils {


  // 读取指定路径的文件
  public static String readFile(String pathToFile) {

    List<String> lines;
    try {
      lines = Files.readAllLines(Paths.get(pathToFile), StandardCharsets.UTF_8);
      return String.join(System.lineSeparator(), lines);
    } catch (IOException e) {
      e.printStackTrace();
      return "";
    }

  }

  // 休眠指定的毫秒数
  public static void sleepMills(long mills) {
    try {
      TimeUnit.MILLISECONDS.sleep(mills);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  // 休眠指定的秒数
  public static void sleepSecond(long seconds) {
    try {
      TimeUnit.SECONDS.sleep(seconds);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  // 打印输出带线程信息的日志
  public static void printThreadLog(String message) {
    // 时间戳 | 线程id | 线程名 | 日志信息
    String result = new StringJoiner(" | ").add(
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()))
        .add(String.format("%2d", Thread.currentThread().getId()))
        .add(Thread.currentThread().getName())
        .add(message)
        .toString();
    System.out.println(result);

  }

  private static String getCurrentTime() {
    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
  }


}
