package ru.rsreu.berestov.lab7;

public class PiCalculating {

  public static final int FORMULA_CONSTANT = 4;

  public static double calc(long from, long to, ProgressPrinter printer) {
    double sum = 0;
    long iterationDelta = (to - from) / 100;

    for (long i = from; i < to; i++) {
      if (Thread.currentThread().isInterrupted()) {
        System.out.println(Thread.currentThread().getName() + " прерван");
        return sum;
      }

      long denominator = i * 2 + 1;

      if (i % 2 == 0) {
        sum = sum + (1.0 / denominator);
      } else {
        sum = sum - (1.0 / denominator);
      }

      if (i % iterationDelta == 0) {
        printer.increaseIterationNumber(iterationDelta);
        if (printer.needToPrint()) {
          printer.setCurrentPercent();
          printer.printProgress();
        }
      }
    }

    return sum;
  }

  public static double calcResult(double sum) {
    return FORMULA_CONSTANT * sum;
  }
}
