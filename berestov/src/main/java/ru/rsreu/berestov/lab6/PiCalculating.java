package ru.rsreu.berestov.lab6;

public class PiCalculating {

  public static final int FORMULA_CONSTANT = 4;

  public static double calc(long from, long to) {
    double sum = 0;
    int percent = 0;

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

      int newPercent = (int) ((i - from) / (double) (to - from) * 100);
      if (newPercent != percent && newPercent % 20 == 0) {
        percent = newPercent;
        System.out.println(
            "Выполнено "
                + newPercent
                + " процентов"
                + " В потоке № "
                + Thread.currentThread().getName());
      }
    }

    return sum;
  }

  public static double calcResult(double sum) {
    return FORMULA_CONSTANT * sum;
  }
}
