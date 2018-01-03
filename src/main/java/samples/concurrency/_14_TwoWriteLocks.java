package samples.concurrency;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class _14_TwoWriteLocks {
    private static ReadWriteLock lock = new ReentrantReadWriteLock();
    private static Lock wLock = lock.writeLock();

    public static void main(String[] args) {
        new Thread(() -> {
            // T1
            wLock.lock();
            System.out.println("T1");
        }).start();

        new Thread(() -> {
            // T2
            wLock.lock();
            System.out.println("T2");
        }).start();
    }
}
