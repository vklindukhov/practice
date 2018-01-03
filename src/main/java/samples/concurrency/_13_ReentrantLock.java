package samples.concurrency;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.time.ZonedDateTime.now;

public class _13_ReentrantLock {
    private static final Lock lock = new ReentrantLock(true);
    private static final Condition notEmpty = lock.newCondition();
    private static final Condition notFull = lock.newCondition();
    private static Integer elem;
    private static Random random = new Random();

    public static void main(String[] args) {
        // Producer
        new Thread(_13_ReentrantLock::put).start();
        // Consumer
        new Thread(_13_ReentrantLock::get).start();
    }

    public static void put() {
        while (true) {
            lock.lock();
            try {
                while (elem != null) notFull.await();
                elem = random.nextInt(100);
                System.err.println(now() + " - [Producer] - Data:" + elem + " has been put");
                notEmpty.signal();
                Thread.sleep(random.nextInt(1000));
            } catch (Throwable ignore) {/*NOP*/} finally {
                lock.unlock();
            }
        }
    }


    public static int get() {
        while (true) {
            Integer data = -1;
            lock.lock();
            try {
                while (elem == null) notEmpty.await();
                data = elem;
                elem = null;
                System.err.println(now() + " - [Consumer] - Data:" + data + " has been got");
                notEmpty.signal();
                Thread.sleep(random.nextInt(1000));
            } catch (Throwable ignore) {/*NOP*/} finally {
                lock.unlock();
            }
            return data;
        }
    }
}
