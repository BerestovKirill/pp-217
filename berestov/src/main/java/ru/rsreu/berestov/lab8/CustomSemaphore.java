package ru.rsreu.berestov.lab8;

import java.util.concurrent.Semaphore;

public class CustomSemaphore extends Semaphore {

  private Object lock = new Object();
  private final int permits;
  private volatile int acquiredCount;

  public CustomSemaphore(int permits) {
    super(permits);
    this.permits = permits;
    this.acquiredCount = 0;
  }

  @Override
  public void acquire() throws InterruptedException {
    acquire(1);
  }

  @Override
  public void acquire(int permits) throws InterruptedException {
    synchronized (lock) {
      ensureValidness(permits);
      if (this.acquiredCount + permits > this.permits) {
        lock.wait();
      }
      this.acquiredCount += permits;
    }
  }

  private void ensureValidness(int permits) throws IllegalArgumentException {
    if (permits < 0) {
      throw new IllegalArgumentException("permits не может быть отрицательным");
    }
  }

  @Override
  public void release() {
    release(1);
  }

  @Override
  public void release(int permits) {
    synchronized (lock) {
      ensureValidness(permits);
      this.acquiredCount -= permits;

      lock.notify();
    }
  }
}
