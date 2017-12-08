package samples.cuncurrency;


import java.util.concurrent.Executor;

import static java.lang.Thread.currentThread;

public class _19_ThreadPoolExecutor {
    public static void main(String[] args) {
        Executor executor = new _19_ThreadPoolExecutor(2);
        executor.execute(getTask());
        executor.execute(getTask());
    }

    private static Runnable getTask() {
        return () -> System.err.println("Hello from the " + currentThread());
    }

}
