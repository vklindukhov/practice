package samples.practice;

//1 D
//2 B
//3 A,B
//4 D
//5 C
//6 C
//7 C

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class _7_Concurrency {
    private static final int THREADS_AMOUNT = Runtime.getRuntime().availableProcessors();
    private static final long N = 1_000_000_000L;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
    }

    private static void reduceSyntax() {
//        BiFunction<Integer, String, Integer> integerStringIntegerBiFunction = (c1, c2) -> c1.length() + c2.length();
//        BinaryOperator<Integer> integerBinaryOperator = (s1, s2) -> s1 + s2;
//        System.out.println(Arrays.asList("duck", "chicken", "flamingo", "pelican").parallelStream().parallel().reduce(0, integerStringIntegerBiFunction, integerBinaryOperator));
    }

    private static void isPrimeInParallelStream() {
        System.out.println(IntStream.rangeClosed(2, 200_000).parallel().filter(_7_Concurrency::isPrime).count());
    }


    public static boolean isPrime(int number) {
        for (int i = 2; i <= number / 2; i++) {
            if (number % i == 0) return false;
        }
        return true;
    }

    private static void forkJoinPool() {
        ForkJoinPool pool = new ForkJoinPool(THREADS_AMOUNT);
        long computedSum = pool.invoke(new MyRecursiveTask(0, N));
        long formulaSum = (N * (N + 1)) / 2;
        System.out.printf("Sum for range 1..%d; computed sum = %d, " + "formula sum = %d %n", N, computedSum, formulaSum);
    }


    private static class MyRecursiveTask extends RecursiveTask<Long> {
        private final long from;
        private final long to;

        private MyRecursiveTask(long from, long to) {
            this.from = from;
            this.to = to;
        }

        @Override
        protected Long compute() {
            if ((to - from) <= N / THREADS_AMOUNT) {
                long localSum = 0;
                for (long i = from; i <= to; i++) localSum += i;
                System.out.printf("\tSum of value range %d to %d is %d %n", from, to, localSum);
                return localSum;
            } else {
                long mid = (from + to) / 2;
                System.out.printf("Forking computation into two ranges: " + "%d to %d and %d to %d %n", from, mid, mid, to);
                MyRecursiveTask firstHalf = new MyRecursiveTask(from, mid);
                firstHalf.fork();
                MyRecursiveTask secondHalf = new MyRecursiveTask(mid + 1, to);
                long resultSecond = secondHalf.compute();
                return firstHalf.join() + resultSecond;
            }
        }
    }

    private static void executorService() throws InterruptedException, ExecutionException {
        Future<Integer> future = Executors.newSingleThreadExecutor().submit(() -> 42);
        System.out.println(future.get());
    }

    private static void copyOnWriteArrayList() {
        List<String> stringsCOWAL = new CopyOnWriteArrayList<>();
        stringsCOWAL.add("First");
        Iterator<String> iteratorCOWAL = stringsCOWAL.iterator();
        while (iteratorCOWAL.hasNext()) {
            System.err.println(iteratorCOWAL.next());
            stringsCOWAL.add("New");
        }
        System.err.println();
        stringsCOWAL.forEach(System.err::println);

        System.err.println();
        List<String> strings = new ArrayList<>();
        strings.add("First");
        Iterator<String> iterator = strings.iterator();
        while (iterator.hasNext()) {
            System.err.println(iterator.next());
            strings.add("New");
        }

        System.err.println();
        strings.forEach(System.err::println);
    }
}

//Chapter 7 Test
//1 - D,F +
//2 - A,D,F - A,C,D,F не знал
//3 - A - B,?!C!? не знал
//4 - C +
//5 - D +
//6 - B +
//7 - A +
//8 - G +
//9 - A,C,D - A,C,E не знал
//10 - D - C не знал
//11 - A,F +
//12 - C,D - E не знал, завтык !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//13 - C,E - A,G не знал
//14 - F - D не знал !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//15 - C,E,G +
//16 - F - F,H не знал
//17 - B +
//18 - F +
//19 - F - A,F завтык
//20 - A,D + !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//21 - A - A,C,D,E не знал, завтык
//22 - F +
//-10(45%)
//V Callable::call throws Exception
//ExecutorService does NOT have the scheduleWithFixedDelay() method
//parallel stream does not guarantee that after sorted() findAny() returns a first element in a sorted list
//public abstract class ForkJoinTask<V> implements Future<V>, Serializable
//using ForkJoinTask.fork().join() produces single-threaded performance at runtime
//<U> U Stream<U>.reduce(U identity, BiFunction<U, ? super T, U> accumulator, BinaryOperator<U> combiner);
//protected abstract void RecursiveAction.compute()
//Stream.flatMap() creates a new sequential stream
//public static <T, K> Collector<T, ?, Map<K, List<T>>> Collectors.groupingBy(Function<? super T, ? extends K> classifier)
//overloaded methods with long timeout, TimeUnit unit from BlockingDeque throws InterruptedException
//If ExecutorService is not shutdowned by calling ExecutorService::shutdown, the code will run but it will never terminate
//If used Thread.sleep(timeout) and assuming this is enough to finnish a task, nevertheless the output cannot be determined ahead of time
//JVM might not allocate NUMBER threads to the parallel stream
//Applications with many resource-heavy tasks tend to benefit more from concurrency than ones with CPU-intensive tasks
//Future<V> ExecutorService.submit(Callable<V>), void Executor.execute(Runnable)
