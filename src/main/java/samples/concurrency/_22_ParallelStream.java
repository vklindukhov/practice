package samples.concurrency;


import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class _22_ParallelStream {
    public static void main(String[] args) {
        long t1 = System.nanoTime();
        Integer res = sequential();
//        Integer res = parallel();
        long t2 = System.nanoTime();
        System.out.println("Time:" + (t2 - t1) / 1_000_000);

        System.out.println("Result: " + res);
    }

    private static Integer sequential() {
        return Stream
                .iterate(0, e -> e + 1)
                .limit(1_000_000)
                .mapToInt(e -> e)
                .sum();
    }


    private static List<Integer> badParallel() {
        return Stream
                .iterate(0, e -> e + 1)
                .skip(5)
                .parallel()
                .filter(e -> e % 3 == 0)
                .map(e -> ~e)
                .limit(1_000_000)
                //                .peek(System.out::println)
                .collect(toList());
    }

    private static Integer parallel() {
        return Stream
                .iterate(0, e -> e + 1)
                .parallel()
                .limit(1_000_000)
                .mapToInt(e -> e)
                .sum();
    }
}
