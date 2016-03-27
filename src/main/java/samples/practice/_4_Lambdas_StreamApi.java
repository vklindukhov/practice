package samples.practice;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.*;
import java.util.stream.Stream;


//1 - B,C,D,E
//2 - B - C
//3 - A - B
//4 - A,C - C
//5 - B
//6 - B
//7 - D
//8 - D - B

//1 - D
//2 - E
//3 - D - F
//4 - C
//5 - D - A
//6 - C - A
//7 - D

public class _4_Lambdas_StreamApi {
    public static void main(String[] args) {
        List<Integer> l1 = Arrays.asList(1, 2, 3);
        List<Integer> l2 = Arrays.asList(4, 5, 6);
        List<Integer> l3 = Collections.emptyList();
        Stream.of(l1, l2, l3)
                .map(Collection::stream)
                .forEach(System.out::println);
    }

    private static void basics() {
        Supplier<String> supplier = () -> "Hello";
        Predicate<String> predicate = String::isEmpty;
        Function<Integer, String> function = (i) -> "" + i;
        Consumer<String> consumer = System.out::println;

        consumer.accept(supplier.get());
        consumer.accept("" + predicate.test(supplier.get()));
        consumer.accept(function.apply(10));

        BiPredicate<String, String> biPredicate;
        BiFunction<String, String, String> biFunction;
        BiConsumer<String, String> biConsumer;

        UnaryOperator<String> unaryOperator;
        BinaryOperator<String> binaryOperator;

        Function<Integer, Integer>
                negate = (i -> -i),
                square = (i -> i * i),
                negateSquare = negate.compose(square);
        System.out.println(negateSquare.apply(10));
    }
}

//Chapter 4 Test
//1 - D +
//2 - B - F не знал
//3 - F - E не знал, завтык
//4 - A,B +
//5 - B,F - A,B завтык
//6 - A,B,C,D - A завтык
//7 - F +
//8 - B,D,F - D,E не знал
//9 - A,B,C,D - B,D не знал, завтык
//10 - C - F не понял вопроса, не знал, завтык
//11 - B,E - B,C,E завтык
//12 - A,F,G +
//13 - B - F не знал, завтык
//14 - D +
//15 - B,D,E - D,E завтык
//16 - C +
//17 - E +
//18 - D +
//19 - A,C,E +
//20 - B +
//-10(50%)
//Stream.generate() and iterate() may be infinitely
//Stream.noneMatch return false if at lease one element does not match predicate, else scans all elements
//sum() is reduction operation for primitive streams, not for generic Stream<T>, Stream.collect() is reduction
//Stream.xxxMatch() returns boolean, Stream.findXxx() returns Optional
//(primitive)XxxStream.average() returns OptionalDouble, sum() returns primitive
// terminal operation shall be before semicolon, filter shall be called after limit to prevent infinitely execution


