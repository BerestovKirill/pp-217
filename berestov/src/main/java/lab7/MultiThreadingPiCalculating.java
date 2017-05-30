package lab7;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class MultiThreadingPiCalculating {
    private long complexity;
    private int threadCount;

    public MultiThreadingPiCalculating(long complexity, int threadCount) {
        this.complexity = complexity;
        this.threadCount = threadCount;
    }

    public double calculatePI() throws ExecutionException, InterruptedException {
        long threadComplexity = complexity / threadCount;
        List<Future<Double>> futures = new ArrayList<Future<Double>>(threadCount);
        ExecutorService pool = Executors.newFixedThreadPool(threadCount);

        Semaphore semaphore = new Semaphore(threadCount / 2);
        CountDownLatch latch = new CountDownLatch(threadCount);
        final long[] time = new long[threadCount];
        ProgressPrinter printer = new ProgressPrinter(0, complexity);
        for (int i = 0; i < threadCount; i++) {
            final int index = i;
            LongWrapper from = new LongWrapper(threadComplexity * i);
            LongWrapper to = new LongWrapper(threadComplexity * (i + 1));

            Future<Double> future =
                    pool.submit(
                            () -> {
                                double result;
                                try {
                                    semaphore.acquire();
                                    result = PiCalculating.calc(from.getValue(), to.getValue()
                                            , printer);
                                    time[index] = System.nanoTime();
                                } finally {
                                    semaphore.release();
                                    latch.countDown();
                                }
                                return result;
                            });
            futures.add(future);
        }

        latch.await();
        long globalEndTime = System.nanoTime();
        for (int index = 0; index < time.length; index++) {
            long result = globalEndTime - time[index];
            String message =
                    "Время выполнения потока "
                            + Integer.toString(index + 1)
                            + " равно "
                            + result
                            + "\t наносекунд";
            System.out.println(message);
        }

        double result = 0;
        for (Future<Double> future : futures) {
            result += future.get();
        }

        return PiCalculating.calcResult(result);
    }
}
