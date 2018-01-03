package samples.concurrency;


import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Stream.of;


public class _25_OptionalMonad {
    private static final Function<Integer, Integer> SQR = x -> x * x;
    private static final Function<Integer, String> EXCL_MARK = e -> "" + e + "!";

    public static void main(String[] args) {
        int reducedWithSeed = of(1, 2, 3, 40, 50, 60).reduce(0, (a, b) -> a + b);
        System.out.println("reducedWithSeed=" + reducedWithSeed);

        Stream<Integer> stream = of(1, 2, 3);
        Optional<Integer> optional = stream.filter(e -> e < 10).reduce(Math::max);
        optional.map(SQR).map(EXCL_MARK).ifPresent(e -> System.out.println("reducedJust=" + e));

        stream = of(1, null, 3, -1);
        optional = stream.filter(Objects::nonNull).filter(e -> e > 10).reduce((a, b) -> a + b);
        optional.map(SQR).map(EXCL_MARK).ifPresent(e -> System.out.println("reducedNothing=" + e));
    }
}
