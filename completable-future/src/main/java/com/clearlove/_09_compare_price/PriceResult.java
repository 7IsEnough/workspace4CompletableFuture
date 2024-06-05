package com.clearlove._09_compare_price;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author promise
 * @date 2024/6/5 - 22:35
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceResult {
  private int price;

  private int discount;

  private int realPrice;

  private String platform;


  public PriceResult(String platform) {
    this.platform = platform;
  }
}
