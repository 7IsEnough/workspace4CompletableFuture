package com.clearlove.utils;

import java.lang.reflect.Member;

/**
 * @author promise
 * @date 2024/6/1 - 22:12
 */
public class CommonUtilDemo {


  public static void main(String[] args) {
    String content = CommonUtils.readFile("news.txt");
    CommonUtils.printThreadLog(content);
  }

}
