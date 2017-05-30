package ru.rsreu.berestov.lab6;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MultiThreadingPiCalculating {

  private long complexity;
  private int threadCount;

  public MultiThreadingPiCalculating(long complexity, int threadCount) {
    this.complexity = complexity;
    this.threadCount = threadCount;
  }

  public double calculatePi() throws ExecutionException, InterruptedException {
    long threadComplexity = complexity / threadCount;
    List<Future<Double>> futures = new ArrayList<Future<Double>>(threadCount);
    ExecutorService pool = Executors.newFixedThreadPool(threadCount);

    for (int i = 0; i < threadCount; i++) {
      LongWrapper from = new LongWrapper(threadComplexity * i);
      LongWrapper to = new LongWrapper(threadComplexity * (i + 1));

      Future<Double> future =
          pool.submit(
              () -> {
                return PiCalculating.calc(from.getValue(), to.getValue());
              });
      futures.add(future);
    }

    double result = 0;
    for (Future<Double> future : futures) {
      result += future.get();
    }

    return PiCalculating.calcResult(result);
  }
}
