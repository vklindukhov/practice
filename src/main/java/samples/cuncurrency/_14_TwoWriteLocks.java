package samples.cuncurrency;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class _15_TwoWriteLocks {
    private static ReadWriteLock lock = new ReentrantReadWriteLock();
    private static Lock wLock = lock.writeLock();

    public static void main(String[] args) {
        new Thread(() -> {
            // T1
            wLock.lock();
            System.out.printf("T1");
        }).start();

        new Thread(() -> {
            // T2
            wLock.lock();
            System.out.printf("T2");
        }).start();
    }
}
