package samples.java8;

import one.util.streamex.IntStreamEx;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.*;
import java.util.stream.*;

import static java.util.Arrays.asList;


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
    private static String XML_FILENAME = "xmlFile.xml";
    private static Node root = xmlDocument(XML_FILENAME);

    public static void main(String[] args) {
        List<String> list = Arrays.asList("One", "Two", "Three", "Four", "Five", "Three", "Three");
    }


    private static void streamsOptionals() {
        Stream<Integer> stream = null;
        Optional<Integer> optional = null;
        IntStream intStream = null;
        OptionalInt optionalInt = null;
    }

    private static void generateExample() {
        Stream<String> generate = Stream.generate(() -> "");
        boolean b = generate.noneMatch(String::isEmpty);
        System.out.println(b);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
    }

    private static void manyCollectionsToStream() {
        List<Integer> l1 = asList(1, 2, 3);
        List<Integer> l2 = asList(4, 5, 6);
        List<Integer> l3 = Collections.emptyList();
        Stream.of(l1, l2, l3)
                .map(Collection::stream)
                .forEach(System.out::println);
    }

    private static void primitiveStream() {
        DoubleStream doubleStream = DoubleStream.empty();
        OptionalDouble any = doubleStream.findAny();
        System.out.println(any.getAsDouble());
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


    private static Document xmlDocument(String fileName) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Document document = null;
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            String xmlStringFile = stream.reduce("", String::concat);
            builder = factory.newDocumentBuilder();
            document = builder.parse(new InputSource(new StringReader(xmlStringFile)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return document;
    }

    private static Stream<Node> nodes(Node root) {
        NodeList childNodes = root.getChildNodes();
        return IntStream.range(0, childNodes.getLength()).mapToObj(childNodes::item);
    }

    private static void decartFromThreeLists() {
        List<List<String>> input = asList(
                asList("a", "b", "c"),
                asList("x", "y"),
                asList("1", "2", "3")
        );

        Stream<String> stream = input.get(0).stream().flatMap(a ->
                input.get(1).stream().flatMap(b ->
                        input.get(2).stream().map(c -> a + b + c))
        );

        stream.forEach(System.out::println);
    }

    private static void decartFromNLists() {
        List<List<String>> input = asList(
                asList("a", "b", "c"),
                asList("x", "y"),
                asList("1", "2", "3"),
                asList("ь", "ё", "щ", "э")
        );

        Supplier<Stream<String>> supplier = input.stream()
//                Stream<List<String>>
                .<Supplier<Stream<String>>>map(list -> list::stream)
//                Stream<Supplier<Stream<String>>>
                .reduce((sup1, sup2) -> () -> sup1.get().flatMap(e1 -> sup2.get().map(e2 -> e1 + e2)))
                .orElse(() -> Stream.of(""));
//        Optional<Supplier<Stream<String>>>
        supplier.get().forEach(System.out::println);
    }

    private static void terminalStreamExSelectUseCase() {
        NodeList childNodes = root.getChildNodes();
        IntStreamEx.range(childNodes.getLength())
                .mapToObj(childNodes::item)
                .select(Element.class)
                .forEach(e -> System.out.println(e.getTagName()));
    }

    private static void simpleDistinct(List<String> list) {
        Map<String, Long> counts = list.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        counts.values().removeIf(cnt -> cnt < 3);
        counts.keySet().forEach(System.out::println);
    }

    private static <T> Predicate<T> distinct(long atLeast) {
        Map<T, Long> map = new ConcurrentHashMap<>();
        return t -> map.merge(t, 1L, Long::sum) == atLeast;
    }

    private static <T> Predicate<T> takeWhile(Predicate<T> predicate) {
        AtomicBoolean matched = new AtomicBoolean();
        return t -> {
            if (matched.get()) return false;
            if (!predicate.test(t)) {
                matched.set(true);
                return false;
            }
            return true;
        };
    }

    private static <T> Stream<T> takeWhile(Stream<T> stream, Predicate<T> predicate) {
        Spliterator<T> src = stream.spliterator();
        Spliterator<T> res = new Spliterators.AbstractSpliterator<T>(src.estimateSize(),
                src.characteristics() & ~Spliterator.SIZED & ~Spliterator.SUBSIZED) {
            boolean finished = false;
            T next;

            @Override
            public boolean tryAdvance(Consumer<? super T> action) {
                if (finished || !src.tryAdvance(t -> next = t) || !predicate.test(next)) {
                    finished = true;
                    return false;
                }
                action.accept(next);
                return true;
            }
        };
        return StreamSupport.stream(res, stream.isParallel()).onClose(stream::close);
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


