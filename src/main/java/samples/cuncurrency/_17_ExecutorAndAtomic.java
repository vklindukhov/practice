package samples.cuncurrency;


import java.util.concurrent.Executor;

import static java.lang.Thread.currentThread;

public class _17_Executor {
    public static void main(String[] args) {
        Executor executor = getExecuter();
        executor.execute(getTask());
        executor.execute(getTask());
    }

    private static Runnable getTask() {
        return () -> System.err.println("Hello from the " + currentThread());
    }

    private static Executor getExecuter() {
        return command -> new Thread(command).start();
    }
}
