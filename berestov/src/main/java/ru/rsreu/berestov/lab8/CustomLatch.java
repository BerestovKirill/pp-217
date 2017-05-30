package ru.rsreu.berestov.lab8;

import java.util.concurrent.CountDownLatch;

public class CustomLatch extends CountDownLatch {

  private int count;
  private Object monitor = new Object();

  public CustomLatch(int count) {
    super(count);
    this.count = count;
  }

  @Override
  public void await() throws InterruptedException {
    synchronized (monitor) {
      if (count > 0) {
        monitor.wait();
      }
    }
  }

  @Override
  public void countDown() {
    synchronized (monitor) {
      if (count > 0) {
        count--;
      }

      if (count == 0) {
        monitor.notifyAll();
      }
    }
  }
}
