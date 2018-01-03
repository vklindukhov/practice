package samples.concurrency;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class _16_TwoReadLocks {
    private static ReadWriteLock lock = new ReentrantReadWriteLock();
    private static Lock rLock = lock.readLock();

    public static void main(String[] args) {
        new Thread(() -> {
            // T1
            rLock.lock();
            System.out.println("T1");
        }).start();
        new Thread(() -> {
            // T2
            rLock.lock();
            System.out.println("T2");
        }).start();
    }
}
