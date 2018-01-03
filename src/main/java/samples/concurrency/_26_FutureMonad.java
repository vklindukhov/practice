package samples.concurrency;


import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;
import static java.util.Arrays.asList;
import static java.util.concurrent.Executors.newCachedThreadPool;


public class _26_FutureMonad {
    private static final Function<Integer, Integer> SQR = x -> x * x;
    private static final Function<Integer, String> EXCL_MARK = e -> "" + e + "!";
    public static final Callable<byte[]> READ_POM = () -> readAllBytes(get("pom.xml"));
    public static final Callable<byte[]> READ_README = () -> readAllBytes(get("README.md"));
    public static final Callable<byte[]> INFINITY_TASK = () -> {
        while (true) ;
    };

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService service = newCachedThreadPool();

        Future<byte[]> pomFutureBytes = service.submit(READ_POM);
        System.out.println("after submit pomFutureBytes.isDone()=" + pomFutureBytes.isDone());
        Future<byte[]> readmeFutureBytes = service.submit(READ_README);
        System.out.println("after submit readmeFutureBytes.isDone()=" + readmeFutureBytes.isDone());

        System.out.println("before cancel readmeFutureBytes.isDone()=" + readmeFutureBytes.isDone());
        readmeFutureBytes.cancel(true);
        System.out.println("after cancel readmeFutureBytes.isDone()=" + readmeFutureBytes.isDone());

        System.out.println("before get pomFutureBytes.isDone()=" + pomFutureBytes.isDone());
        System.out.println("pomBytes.bytes=" + Arrays.toString(pomFutureBytes.get()));
        System.out.println("after get pomFutureBytes.isDone()=" + pomFutureBytes.isDone());

        try {
            System.out.println("try get readmeBytes.bytes=" + Arrays.toString(readmeFutureBytes.get()));
            System.out.println("readmeBytes.bytes=" + Arrays.toString(readmeFutureBytes.get()));
        } catch (CancellationException e) {
            System.out.println("\nreadmeBytes.bytes are not got due to " + e.getClass().getSimpleName() + ": ");
            Stream.of(e.getStackTrace()).sorted((o1, o2) -> o1.equals(o2) ? 0 : -1).forEach(System.out::println);
            System.out.println("");
        }

        System.out.println("anyFileBytes=" + Arrays.toString(service.invokeAny(asList(READ_POM, READ_README, INFINITY_TASK))));

        Future<byte[]> infinityFuture = service.submit(INFINITY_TASK);
        System.out.println("after submit & before get infinityFuture.isDone()=" + infinityFuture.isDone());
        System.out.println("readmeBytes.bytes=" + Arrays.toString(infinityFuture.get()));

        List<Future<byte[]>> invokedAllFutures = service.invokeAll(asList(READ_POM, READ_README, INFINITY_TASK));

    }
}
