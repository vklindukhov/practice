package samples.concurrency;


import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicLong;

import static java.lang.Thread.NORM_PRIORITY;
import static java.lang.Thread.currentThread;

public class _17_ExecutorAndAtomic {
    public static void main(String[] args) {
        Executor executor = getExecuter();
        executor.execute(getTask());
        executor.execute(getTask());
    }

    private static Runnable getTask() {
        return () -> System.err.println("Hello from the " + currentThread());
    }

    private static Executor getExecuter() {
        return new Executor() {
            private final AtomicLong counter = new AtomicLong(0);
            private final ThreadGroup group = new ThreadGroup("ExecutorsGroup");
            @Override
            public void execute(Runnable command) {
                Thread thread = new Thread(group, command);
                thread.setPriority(NORM_PRIORITY + 1);
                thread.setDaemon(true);
                thread.setName("Thread#" + counter.getAndIncrement());
                thread.start();
            }
        };
    }
}
