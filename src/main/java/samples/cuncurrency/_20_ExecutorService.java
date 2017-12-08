package samples.cuncurrency;


import java.util.List;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeoutException;

import static java.lang.Thread.sleep;
import static java.util.Arrays.asList;
import static java.util.concurrent.TimeUnit.SECONDS;

public class _20_ExecutorService {
    private static BlockingQueue<Runnable> QUEUE = new ArrayBlockingQueue<>(1024, true);
    private static ThreadFactory FACTORY = (ThreadFactory) Thread::new;
    private static RejectedExecutionHandler HANDLER = (task, handler) -> handler.execute(task);

    private static ExecutorService customExecutor = new ThreadPoolExecutor(4, 32, 60, SECONDS, QUEUE);
    private static ExecutorService scheduledExecutor = new ScheduledThreadPoolExecutor(4, FACTORY, HANDLER);

    private static ExecutorService singlePool = Executors.newSingleThreadExecutor(FACTORY);
    private static ExecutorService singleScheduledPool = Executors.newSingleThreadScheduledExecutor(FACTORY);
    private static ExecutorService scheduledPool = Executors.newScheduledThreadPool(8, FACTORY);
    private static ExecutorService fixedPool = Executors.newFixedThreadPool(16, FACTORY);
    private static ExecutorService cachedPool = Executors.newCachedThreadPool(FACTORY);
    private static ExecutorService stealingPool = Executors.newWorkStealingPool(32);


    public static void main(String[] args) throws InterruptedException, TimeoutException, ExecutionException {
        ExecutorService service = cachedPool;
        service.execute(getRunnable());

        Future<?> runnableResult = service.submit(getRunnable());
//        Future<Integer> runnableParametrizedResult = service.submit(getStuckRunnable(), 0);
        Future<Integer> runnableParametrizedResult = service.submit(getRunnable(), 0);
        Future<Integer> callableResult = service.submit(getStuckCallable());
        Integer invokedAnyResult = service.invokeAny(asList(getStuckCallable(), getCallable()), 60, SECONDS);

        sleep(100);
        System.err.println("Simple runnable result: " + runnableResult.get());
        System.err.println("Simple runnable parametrized (stuck) result: " + runnableParametrizedResult.get());
        System.err.println("Simple callable result: " + callableResult.get());
        System.err.println("Invoked any result: " + invokedAnyResult);

        List<Future<Integer>> invokedAllResults = service.invokeAll(asList(getCallable(), getStuckCallable()), 60, SECONDS);
        System.err.println("Invoked all result: " + invokedAllResults);

        System.err.println("Bye!");
    }

    private static Runnable getRunnable() {
        return () -> {/*System.err.println("Hello from the " + currentThread());*/};
    }

    private static Runnable getStuckRunnable() {
        return () -> {
//            System.err.println("Hello from the " + currentThread());
            while (true) ;
        };
    }

    private static Callable<Integer> getCallable() {
        return () -> {
            int res = new Random().nextInt(100500 - 42) + 42;
//            System.err.println("Hello from the " + currentThread());
            return res;
        };
    }

    private static Callable<Integer> getStuckCallable() {
        return () -> {
            int res = new Random().nextInt(100500 - 42) + 42;
//            System.err.println("Hello from the " + currentThread());
            boolean b = false;
            while (b = true) ;
            return res;
        };
    }
}
