package samples.cuncurrency;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class _15_ReadWriteLocks {
    private static ReadWriteLock lock1 = new ReentrantReadWriteLock();
    private static Lock rLock1 = lock1.readLock();
    private static Lock wLock1 = lock1.writeLock();
    private static ReadWriteLock lock2 = new ReentrantReadWriteLock();
    private static Lock rLock2 = lock2.readLock();
    private static Lock wLock2 = lock2.writeLock();

    public static void main(String[] args) {
        new Thread(() -> {
            // T1
            rLock1.lock();
            System.out.println("T1");
        }).start();
        new Thread(() -> {
            // T2
            wLock1.lock();
            System.out.println("T2");
        }).start();

        new Thread(() -> {
            // T3
            wLock2.lock();
            System.out.println("T3");
        }).start();
        new Thread(() -> {
            // T4
            rLock2.lock();
            System.out.println("T4");
        }).start();
    }
}