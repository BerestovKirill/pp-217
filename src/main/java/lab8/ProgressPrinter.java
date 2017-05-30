package lab8;

import lab8.customs.CustomLock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Кирилл on 30.05.2017.
 */
public class ProgressPrinter {
    private ReentrantLock lock = new CustomLock();
    private long from;
    private long to;
    private int currentPercent = 0;
    private long iterationNumber = 0;

    public ProgressPrinter(long from, long to) {
        this.from = from;
        this.to = to;
    }

    public boolean needToPrint() {
        lock.lock();
        boolean result;
        try {
            int newPercent = (int) ((iterationNumber - from) / (double) (to - from) * 100);
            result = newPercent > currentPercent;
        } finally {
            lock.unlock();
        }
        return result;
    }

    public void printProgress() {
        lock.lock();
        try {
            System.out.println(
                    "Выполнено "
                            + currentPercent
                            + " процентов"
                            + " В потоке под именем "
                            + Thread.currentThread().getName());
        } finally {
            lock.unlock();
        }
    }

    public void setCurrentPercent() {
        lock.lock();
        try {
            currentPercent = (int) ((iterationNumber - from) / (double) (to - from) * 100);
        } finally {
            lock.unlock();
        }
    }

    public void increaseIterationNumber(long delta) {
        lock.lock();
        iterationNumber += delta;
        lock.unlock();
    }
}
