package samples.concurrency;


import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.lang.Thread.currentThread;

public class _27_CompleatableFutureMonad {
    private static final Function<Integer, Integer> SQR = e -> e * e;
    private static Supplier<String> TASK_SUPPLIER = () -> {
        String res = "0";
        for (int i = 0; i < 100; i++) {
            res = String.valueOf(i % 42);
        }
        return res;
    };

    public static void main(String[] args) throws IOException {
        CompletableFuture
                .supplyAsync(TASK_SUPPLIER)
                .thenApply(Integer::valueOf)
                .thenApply(SQR)
                .thenAccept(System.out::println);


        CompletableFuture
                .supplyAsync(() -> {
                    sleep(100);
                    return currentThread().getName();
                })
                .thenAcceptBothAsync(
                        CompletableFuture.supplyAsync(() -> {
                            sleep(200);
                            return currentThread().getName();
                        }),
                        (x, y) -> System.out.println(x + "#" + y)
                );

        System.in.read();
    }


    private static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
