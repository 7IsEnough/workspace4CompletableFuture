package com.clearlove._09_compare_price;

import com.clearlove.utils.CommonUtils;
import java.util.Comparator;
import java.util.stream.Stream;

/**
 * @author promise
 * @date 2024/6/5 - 22:54
 */
public class ComparePriceService {

  // 方案一：串行方式操作商品比价
  public PriceResult getCheapestPlatformPrice(String productName) {
    PriceResult priceResult;
    int discount;
    // 获取淘宝平台的价格和优惠
    priceResult = HttpRequest.getTaoBaoPrice(productName);
    discount = HttpRequest.getTaoBaoDiscount(productName);
    PriceResult taoBaoPriceResult = computeRealPrice(priceResult, discount);

    // 获取京东平台的价格和优惠
    priceResult = HttpRequest.getJingDongPrice(productName);
    discount = HttpRequest.getJingDongDiscount(productName);
    PriceResult jingDongPriceResult = computeRealPrice(priceResult, discount);

    // 获取拼多多平台的价格和优惠
    priceResult = HttpRequest.getPDDPrice(productName);
    discount = HttpRequest.getPDDDiscount(productName);
    PriceResult PDDPriceResult = computeRealPrice(priceResult, discount);

    // 计算最优的平台和价格
    return Stream.of(taoBaoPriceResult, jingDongPriceResult, PDDPriceResult)
        .min(Comparator.comparing(PriceResult::getRealPrice))
        .get();
  }

  public PriceResult computeRealPrice(PriceResult priceResult, int discount) {
    priceResult.setRealPrice(priceResult.getPrice() - discount);
    priceResult.setDiscount(discount);
    CommonUtils.printThreadLog(
        priceResult.getPlatform() + "最终价格计算完成：" + priceResult.getRealPrice());
    return priceResult;
  }
}
