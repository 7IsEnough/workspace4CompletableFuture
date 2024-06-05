package com.clearlove._09_compare_price;

import java.util.Arrays;
import java.util.List;

/**
 * @author promise
 * @date 2024/6/5 - 23:24
 */
public class ComparePriceDemo {

  public static void main(String[] args) {
    ComparePriceService service = new ComparePriceService();

    // 方案一测试
    //    long start = System.currentTimeMillis();
    //    PriceResult priceResult = service.getCheapestPlatformPrice("iPhone14");
    //    long end = System.currentTimeMillis();
    //
    //    double costTime = (end - start) / 1000.0;
    //    System.out.printf("cost %.2f second\n", costTime);
    //
    //    System.out.println("priceResult = " + priceResult);

    // 方案二测试
    //    long start = System.currentTimeMillis();
    //    PriceResult priceResult = service.getCheapestPlatformPrice2("iPhone14");
    //    long end = System.currentTimeMillis();
    //
    //    double costTime = (end - start) / 1000.0;
    //    System.out.printf("cost %.2f second\n", costTime);
    //
    //    System.out.println("priceResult = " + priceResult);

    // 方案三测试
//    long start = System.currentTimeMillis();
//    PriceResult priceResult = service.getCheapestPlatformPrice3("iPhone14");
//    long end = System.currentTimeMillis();
//
//    double costTime = (end - start) / 1000.0;
//    System.out.printf("cost %.2f second\n", costTime);
//
//    System.out.println("priceResult = " + priceResult);

    /**
     * 方案一：串行方式操作商品此价 costTime:6.09
     * 方案二：Future+线程池提高了任务处理的并行性 costTime:2.09
     * 方案三：使用CompletableFuture.进一步增强并行 costTime:1.10
     */


    // 测试在一个平台比较同款产品不同色系的价格
    List<String> products = Arrays.asList("iPhone14黑色", "iPhone14白色", "iphone14玫瑰红");
    PriceResult priceResult = service.batchComparePrice(products);
    System.out.println("priceResult = " + priceResult);
  }
}
