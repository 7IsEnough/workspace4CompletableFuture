package com.clearlove._09_compare_price;

/**
 * @author promise
 * @date 2024/6/5 - 23:24
 */
public class ComparePriceDemo {

  public static void main(String[] args){
    ComparePriceService service = new ComparePriceService();

    long start = System.currentTimeMillis();
    PriceResult priceResult = service.getCheapestPlatformPrice("iPhone14");
    long end = System.currentTimeMillis();

    double costTime = (end - start) / 1000.0;
    System.out.printf("cost %.2f second\n", costTime);

    System.out.println("priceResult = " + priceResult);
  }
}
