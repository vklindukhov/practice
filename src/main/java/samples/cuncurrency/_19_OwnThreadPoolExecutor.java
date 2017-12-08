package samples.cuncurrency;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;

import static java.lang.Thread.currentThread;

public class _19_OwnThreadPoolExecutor {
    public static void main(String[] args) {
        Executor executor = new MyThreadPoolExecutor(2);
        executor.execute(getTask());
        executor.execute(getTask());
        executor.execute(getTask());
        executor.execute(getTask());
        executor.execute(getTask());
        System.err.println("Bye!");
    }

    private static Runnable getTask() {
        return () -> System.err.println("Hello from the " + currentThread());
    }

    static class MyThreadPoolExecutor implements Executor {
//        private final BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(256);
//        VS
        private final BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(1);
        private final Thread[] pool;

        MyThreadPoolExecutor(int size) {
            this.pool = new Thread[size];
            for (int i = 0; i < pool.length; i++) {
                pool[i] = new Thread(() -> {
                    while (true) try {
                        queue.take().run();
                    } catch (InterruptedException ignore) {/*NOP*/}
                });
                pool[i].start();
            }
        }

        @Override
        public void execute(Runnable command) {
            // Inserts if free place existed and does not lock main thread
//            if(queue.offer(command)) {
//                System.err.println("Rejected");
//            }

//            try {
//                queue.add(command);
//            } catch (IllegalStateException e) {throw new Error("REJECTED");}
//
            // Blocks main
            try {
                queue.put(command);
            } catch (InterruptedException e) {throw new Error("NEVER");}
        }
    }

}
