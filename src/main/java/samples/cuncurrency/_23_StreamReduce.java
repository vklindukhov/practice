package samples.cuncurrency;


import java.util.function.BinaryOperator;
import java.util.stream.Stream;

public class _23_StreamReduce {
    public static void main(String[] args) {
        Stream<Double> fibonacci1 = Stream.iterate(new double[]{.0, 1.}, e -> new double[]{e[1], e[0] + e[1]}).limit(10).map(e -> e[0]);
        Stream<Double> fibonacci2 = Stream.iterate(new double[]{.0, 1.}, e -> new double[]{e[1], e[0] + e[1]}).limit(10).map(e -> e[0]);
        Stream<Double> fibonacci3 = Stream.iterate(new double[]{.0, 1.}, e -> new double[]{e[1], e[0] + e[1]}).limit(10).map(e -> e[0]);
        Stream<Double> fibonacci4 = Stream.iterate(new double[]{.0, 1.}, e -> new double[]{e[1], e[0] + e[1]}).limit(10).map(e -> e[0]);

        BinaryOperator<Double> associativeOperator = (acc, e) -> acc + e;
        BinaryOperator<Double> nonAssociativeOperator = (acc, e) -> acc - e;

        Double res1 = fibonacci1.reduce(0., associativeOperator);
        Double res2 = fibonacci2.parallel().reduce(0., associativeOperator);
        Double res3 = fibonacci3.reduce(0., nonAssociativeOperator);
        Double res4 = fibonacci4.parallel().reduce(0., nonAssociativeOperator);

        System.out.println("Sequential associative res: " + res1);
        System.out.println("Parallel associative res: " + res2);
        System.out.println("Sequential non associative res: " + res3);
        System.out.println("Parallel non associative res: " + res4);
    }
}
