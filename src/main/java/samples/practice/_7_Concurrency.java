package samples.practice;

//1
//2
//3
//4
//5
//6
//7

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

    private static void isPrimeInParallelStream() {
        System.out.println(IntStream.rangeClosed(2, 200_000).parallel().filter(_7_Concurrency::isPrime).count());
    }


    public static boolean isPrime(int number) {
        for (int i = 2; i <= number/2; i++) {
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
//1 -
//2 -
//3 -
//4 -
//5 -
//6 -
//7 -
//8 -
//9 -
//10 -
//11 -
//12 -
//13 -
//14 -
//15 -
//16 -
//17 -
//18 -
//19 -
//20 -
//-(0%)