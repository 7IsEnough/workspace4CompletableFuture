package com.clearlove._09_compare_price;

import com.clearlove.utils.CommonUtils;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
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

  // 方案二：使用Future+线程池增强并行
  public PriceResult getCheapestPlatformPrice2(String productName) {
    ExecutorService executorService = Executors.newFixedThreadPool(4);
    // 获取淘宝平台的价格和优惠
    Future<PriceResult> taobaoFuture =
        executorService.submit(
            () -> {
              return computeRealPrice(
                  HttpRequest.getTaoBaoPrice(productName),
                  HttpRequest.getTaoBaoDiscount(productName));
            });

    // 获取京东平台的价格和优惠
    Future<PriceResult> jingDongFuture =
        executorService.submit(
            () -> {
              return computeRealPrice(
                  HttpRequest.getJingDongPrice(productName),
                  HttpRequest.getJingDongDiscount(productName));
            });

    // 获取拼多多平台的价格和优惠
    Future<PriceResult> pDDFuture =
        executorService.submit(
            () -> {
              return computeRealPrice(
                  HttpRequest.getPDDPrice(productName), HttpRequest.getPDDDiscount(productName));
            });

    // 计算最优的平台和价格
    return Stream.of(taobaoFuture, jingDongFuture, pDDFuture)
        .map(
            (future) -> {
              try {
                return future.get(5, TimeUnit.SECONDS);
              } catch (Exception e) {
                e.printStackTrace();
                return null;
              } finally {
                executorService.shutdown();
              }
            })
        .filter(Objects::nonNull)
        .min(Comparator.comparing(PriceResult::getRealPrice))
        .get();
  }

  // 方案三：使用CompletableFuture进一步增强并行
  public PriceResult getCheapestPlatformPrice3(String productName) {
    // 获取淘宝平台的价格和优惠
    CompletableFuture<PriceResult> taoBaoCF =
        CompletableFuture.supplyAsync(() -> HttpRequest.getTaoBaoPrice(productName))
            .thenCombine(
                CompletableFuture.supplyAsync(() -> HttpRequest.getTaoBaoDiscount(productName)),
                this::computeRealPrice);

    // 获取京东平台的价格和优惠
    CompletableFuture<PriceResult> jingDongCF =
        CompletableFuture.supplyAsync(() -> HttpRequest.getJingDongPrice(productName))
            .thenCombine(
                CompletableFuture.supplyAsync(() -> HttpRequest.getJingDongDiscount(productName)),
                this::computeRealPrice);

    // 获取拼多多平台的价格和优惠
    CompletableFuture<PriceResult> pDDCF =
        CompletableFuture.supplyAsync(() -> HttpRequest.getPDDPrice(productName))
            .thenCombine(
                CompletableFuture.supplyAsync(() -> HttpRequest.getPDDDiscount(productName)),
                this::computeRealPrice);

    return Stream.of(taoBaoCF, jingDongCF, pDDCF)
        .map(CompletableFuture::join)
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

  public PriceResult batchComparePrice(List<String> products) {
    // step1:遍历每个商品的名字，根据商品名称开启异步任务获取最终价，归集到List集合中
    List<CompletableFuture<PriceResult>> completableFutures =
        products.stream()
            .map(
                productName -> {
                  return CompletableFuture.supplyAsync(
                          () -> HttpRequest.getTaoBaoPrice(productName))
                      .thenCombine(
                          CompletableFuture.supplyAsync(
                              () -> HttpRequest.getTaoBaoDiscount(productName)),
                          this::computeRealPrice);
                })
            .collect(Collectors.toList());

    // step2:把多个商品的最终价进行排序获取最小值
    return completableFutures.stream()
        .map(CompletableFuture::join)
        .sorted(Comparator.comparing(PriceResult::getRealPrice))
        .findFirst()
        .get();
  }
}
