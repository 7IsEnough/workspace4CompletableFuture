package com.clearlove._08_parallelstream_completablefuture;

import com.clearlove.utils.CommonUtils;

/**
 * @author promise
 * @date 2024/6/5 - 17:57
 */
public class MyTask {

  private int duration;

  public MyTask(int duration) {
    this.duration = duration;
  }

  // 模拟耗时的长任务
  public int doWork() {
    CommonUtils.printThreadLog("doWork");
    CommonUtils.sleepSecond(duration);
    return duration;
  }
}
