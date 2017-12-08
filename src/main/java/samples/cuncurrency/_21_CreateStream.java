package samples.cuncurrency;


import java.util.Arrays;
import java.util.stream.Stream;

import static java.lang.Math.random;

public class _21_Streams {
    public static void main(String[] args) {
        Stream<Double> stream1 = Arrays.asList(random(), random(), random()).stream();
        Stream<Double> stream2 = Stream.generate(Math::random).limit(10);
        Stream<Double> stream3 = Stream.iterate(new double[]{.0, 1.}, e -> new double[]{e[1], e[0] + e[1]}).limit(10).map(e -> e[0]);
        Stream<Double> stream4 = Stream.empty();
        Stream<Double> stream5 = Stream.concat(stream1, stream2);
        Stream.Builder<Double> builder = Stream.builder();
        builder.add(random()).accept(random());
        Stream<Double> stream6 = builder.build();
        Stream<Double> stream7 = Stream.of(random(), random(), random());

    }
}
