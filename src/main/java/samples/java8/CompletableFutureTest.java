package samples.java8;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureTest {
    public static void main(String[] args) {
        CompletableFuture.supplyAsync(() -> 42L)
                .thenApply(r -> r + 2016)
                .thenAccept(System.out::println);
    }
}
