package lab8.customs;

import java.util.concurrent.locks.ReentrantLock;

public class CustomLock extends ReentrantLock {
    private int lockHoldCount;
    private long lockedBy;
    private Object monitor = new Object();

    public CustomLock() {
        lockHoldCount = 0;
    }

    @Override
    public void lock() {
        synchronized (monitor) {
            if (lockHoldCount == 0) {
                lockHoldCount++;
                lockedBy = Thread.currentThread().getId();
            } else if (lockHoldCount > 0 && lockedBy == Thread.currentThread().getId()) {
                lockHoldCount++;
            } else {
                try {
                    while (lockHoldCount != 0) {
                        monitor.wait();
                    }
                    lockHoldCount++;
                    lockedBy = Thread.currentThread().getId();
                } catch (InterruptedException e) {
                    System.out.println("Lock was interrupted in thread " + Thread.currentThread().getName());
                }
            }
        }
    }

    @Override
    public void unlock() {
        synchronized (monitor) {
            if (lockHoldCount == 0) {
                throw new IllegalMonitorStateException();
            }

            lockHoldCount--;
            if (lockHoldCount == 0) {
                monitor.notify();
            }
        }
    }
}
