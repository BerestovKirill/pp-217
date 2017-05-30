package lab8;

import java.util.concurrent.ExecutionException;

public class Launcher {

    public static void main(String[] args) {
        try {
            MultiThreadingPiCalculating piCalculating = new MultiThreadingPiCalculating(100000000L, 5);
            double pi = piCalculating.calculatePI();
            System.out.println("PI=" + pi);
        } catch (InterruptedException e) {
            System.err.println("Решение прервано");
        } catch (ExecutionException e) {
            System.err.println("Исполнение было прервано : " + e.getCause().getMessage());
        }
    }
}
