package samples.cuncurrency;


import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

import static java.lang.Thread.currentThread;
import static java.util.concurrent.TimeUnit.SECONDS;

public class _20_ThreadPoolExecutors {
    private static BlockingQueue<Runnable> QUEUE = new ArrayBlockingQueue<>(1024, true);
    private static ThreadFactory FACTORY = (ThreadFactory) Thread::new;
    private static RejectedExecutionHandler HANDLER = (task, handler) -> handler.execute(task);

    private static Executor customExecutor = new ThreadPoolExecutor(4, 32, 60, SECONDS, QUEUE);
    private static Executor scheduledExecutor = new ScheduledThreadPoolExecutor(4, FACTORY, HANDLER);

    private static Executor singlePool = Executors.newSingleThreadExecutor(FACTORY);
    private static Executor singleScheduledPool = Executors.newSingleThreadScheduledExecutor(FACTORY);
    private static Executor scheduledPool = Executors.newScheduledThreadPool(8, FACTORY);
    private static Executor fixedPool = Executors.newFixedThreadPool(16, FACTORY);
    private static Executor cachedPool = Executors.newCachedThreadPool(FACTORY);
    private static Executor stealingPool = Executors.newWorkStealingPool(32);



    public static void main(String[] args) {
        Executor executor = cachedPool;
        executor.execute(getRunnable());
        executor.execute(getRunnable());
        executor.execute(getRunnable());
        executor.
        System.err.println("Bye!");
    }

    private static Runnable getRunnable() {
        return () -> System.err.println("Hello from the " + currentThread());
    }

    private static Callable<Integer> getCallable() {
        return () -> {
            int res = new Random().nextInt();
            System.err.println("Hello from the " + currentThread());
            return res;
        }
    }
}
