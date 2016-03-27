package samples.java8;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Runner {
    private int count;

    public static void main(String[] args) {
//        new X().lambdaExample();
        new Runner().streamExample();
//        new X().dateExample();
    }

    public void java8() {
        Function<String, String> function = String::toLowerCase;
    }

    public void lambdaExample() {
        int sumInt = 0;
        int[] sumArr = new int[1];
        Stream.iterate(0, i -> i + 1).limit(100).forEach(i -> sumArr[0] += i);
//        System.out.println(sumArr[0]);
    }

    public void streamExample() {
//        1)
        List<String> arr = Arrays.asList("One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten");
        Integer res = arr.stream().
                map(String::toLowerCase).
                filter((s) -> s.startsWith("t")).
                map(String::length).
                reduce(Math::max).
                get();
//        System.out.println(res);
//        IntStream.range(0, 100).forEach(System.out::println);
//        2)
        List<Integer> list = Stream.iterate(0, (i) -> {
//            System.out.print(i + " ");
            return i + 1;
        }).limit(100).collect(Collectors.toList());
//        System.out.println(list);

//        3)
        OptionalDouble average = IntStream.range(0, 100).average();
//        System.out.println(average.getAsDouble());
//        4)
//        AtomicInteger[] sum = new AtomicInteger[1];
//        sum[0] = new AtomicInteger(0);
//        int sum[] = new int[1];
        Optional<Integer> integer = IntStream.range(0, 1_000_000).parallel().
                mapToObj(i -> 1).
//                forEach(i -> sum[0] += i);
//                forEach(i -> sum[0].set(sum[0].addAndGet(i)));
        reduce((x1, x2) -> x1 + x2);
//        System.out.println(sum.isPresent() ? sum.get() : "Exception in thread \"main\" java8.util.NoSuchElementException: No value present\n" +
//                "\tat java8.util.Optional.get(Optional.java8:135)\n" +
//                "\tat X.streamExample(samples.java8.Java8Featurs.java8:59)\n" +
//                "\tat X.main(samples.java8.Java8Featurs.java8:17)\n" +
//                "\tat sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n" +
//                "\tat sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java8:62)\n" +
//                "\tat sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java8:43)\n" +
//                "\tat java8.lang.reflect.Method.invoke(Method.java8:497)\n" +
//                "\tat com.intellij.rt.execution.application.AppRunner.main(AppRunner.java8:140)");
//        System.out.println(sum[0]);
//        System.out.println(integer.get());
//        5)
//        IntStream.
//                range(0, 100).
//                parallel().
//                boxed().
//                forEach(System.out::println);
//        6)
        Random random = new Random();
        int size = 1000;
//        System.out.println(
//                Stream
//                        .generate(() -> String.valueOf(random.nextInt(size))).
//                        limit(size).
//                        collect(Collectors.joining(","))
//        );
//        Stream
//                .generate(() -> String.valueOf(random.nextInt(size))).
//                limit(size).
//                collect(ArrayList::new, List::add, List::addAll).
//                forEach(System.out::print);
//        7)
        /*List<Integer> collect = */
        IntStream.range(0, size).
                unordered().
                parallel().
                mapToObj(x -> x);//.
//                forEach(System.out::println);
//                collect(Collectors.toList());
//        System.out.println(collect);
//        8)
        Stream.of("one", "two", "three", "four")
                .filter(e -> e.length() > 3)
                .peek(e -> System.out.println("Filtered value: " + e))
                .map(String::toUpperCase)
                .peek(e -> System.out.println("Mapped value: " + e))
                .collect(Collectors.toList());
    }

    private void dateExample() {
//        1)
//        Stream.of(Month.values()).
//                countValues(month -> LocalDate.now().
//                                withYear(1992).
//                                withMonth(month.getValue()).
//                                with(TemporalAdjusters.lastDayOfMonth()
//                                ).getDayOfWeek()
//                ).forEach(System.out::println);

//        2)
        Stream.of(Month.values()).
                map(month -> LocalDate.now().
                        withYear(1992).
                        withMonth(month.getValue()).
                        with(TemporalAdjusters.lastDayOfMonth())).
                collect(Collectors.toSet()).stream().
                skip(2).
                limit(6).
                sorted(LocalDate::compareTo);
//                forEach(d -> System.out.println(d.getMonth() + " - " + d.getDayOfWeek()));
    }
}

//        Arrays.asList("one","two","tree").stream()
//                .countValues(String::toUpperCase)
//                .filter(s -> s.endsWith("E"))
//                .forEach(System.out::println);
//
//        I iimpl = new Iimpl();
//        iimpl.method();
