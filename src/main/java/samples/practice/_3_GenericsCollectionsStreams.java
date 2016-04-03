package samples.practice;

//1 - C
//2 - A
//3 - C
//4 - B - D
//5 - A
//6 - D
//7 - C - B
//8 - B
//9 - A - C

import java.util.*;

public class _3_GenericsCollectionsStreams {
    public static void main(String[] args) {
    }

    public static void m(List<? super Number> list){
        list.add(123);
    }

    private static void lambda() {
        Set<String> s = new HashSet<>();
        s.add("lion");
        s.add("tiger");
        s.add("bear");
//        s.forEach(() -> System.out.println(s));
//        s.forEach(s -> System.out.println(s));
//        s.forEach((s) -> System.out.println(s));
//        s.forEach(System.out.println(s));
//        s.forEach(System::out::println);
        s.forEach(System.out::println);
    }

    private static void remove() {
        List<Integer> collection = new LinkedList<>();
        collection.add(10);
        collection.add(11);
        collection.remove(1);
        Queue<Integer> queue = new LinkedList<>();
        queue.remove(1);
        System.out.println(collection);
    }

    private static void explisitlytypeArgument() {
        Generic.<Object>myMethod(new Object());
    }

    private static void stringsNaturalOrder() {
        Set<String> strings = new TreeSet<>();
        strings.add("AAA");
        strings.add("aaa");
        strings.add("bbb");
        strings.add("BBB");
        System.out.println(strings);
    }

    private static void typeErasure() {
        System.out.print(new Generic<String>("hi"));
        System.out.print(new Generic("there"));
    }

    static class Generic<C> {
        //1
        C t;

        public Generic(C t) {
            this.t = t;
        }

        @Override
        public String toString() {
            return t.toString();

        }


        //2
        public Generic() {
        }

        public static <Type extends Object> void myMethod(Type type) {
        }


        //3
//        class A {
//        }
//
//        class B extends A {
//        }
//
//        class C extends B {
//        }

//        A a1 = new A();
//        A a2 = new B();
//        A a3 = new C();
//        C c1 = new A();
//        C c2 = new B();
//        C c3 = new C();
    }

    private static void deque() {
//        ArrayDeque<String> strings = new ArrayDeque<>();
//        strings.add("1"); //right
//        System.out.println(strings);
//        strings.push("2"); //left !pop !poll
//        System.out.println(strings);
//        strings.offer("3"); //right
//        System.out.println(strings);
//        System.out.println(strings);
//        System.out.println(strings.pop()); // left !push
//        System.out.println(strings);
//        System.out.println(strings.poll()); //left !push
//        System.out.println(strings);
//        System.out.println(strings.peek()); // right !add !offer
//        System.out.println(strings);

        ArrayDeque<String> strings = new ArrayDeque<>();
        strings.push("hello");
        strings.push("hi");
        System.out.println(strings); //[hi, hello]
        strings.push("ola");
        System.out.println(strings); //[ola, hi, hello]
        strings.pop();
        System.out.println(strings); //[hi, hello]
        strings.peek();
        System.out.println(strings); //[hi, hello]
        while (strings.peek() != null) System.out.print(strings.pop()); //hihello
    }
}


//Chapter 3 Test
//1 - A - ?B?, ?? ????
//2 - E - D, ?? ????
//3 - E +
//4 - D - E, ?? ????
//5 - B,C,G +
//6 - F - C ?? ????
//7 - D - A,D ??????
//8 - C +
//9 - E +
//10 - A - D ??????
//11 - A +
//12 - A,B - A,B,D ?? ????
//13 - B,C,E - B,E ??????
//14 - E - C ??????
//15 - A - D ?? ????
//16 - A,B,D,F - B,D,F ?? ????
//17 - B,D +
//18 - A,B,C,F - A,B ?? ????
//19 - A,C - A,D
//20 - A - E ??????
//21 - A,F +
//22 - B +
//23 - A,B - B,E ?? ????
//24 - B,C,F - F
//25 - H - F ?? ????
//if need to display not unique products from a database, should use ArrayList
//if need to work with a collection of elements associated with unique string field that need to bee sorted in their natural order, should use TreeMap
//Deque: push ->left !pop !poll, add/offer -> right !peek
//String natural order is: special symbols, numbers, uppercase, lowercase
//Tree::ceiling() returns the smallest element grater than the specific one
//using odd syntax by explicitly listing the type: MyClass.<MyType>myMethod(new MyType())
//Comparator and instanceof FunctionalInterface
//if TreeSet does not specify Comparator, it uses Comparable::compareTo
//Collections::binarySearch and passed List should have the same sorting order (asc by default for Collections::binarySearch)
//java.lang.Comparable and java.util.Comparator are FunctionalInterface (s)
//if generic uses class as type parameter, this class is type parameter, but not class anymore
//List has remove(Object) and remove(index), Queue only remove(Object)
//Map has containsKey and containsValue methods, but DOES NOT have contains method
//when using generic types in a static method, the generic specification goes before the return type
//Comparator and Collection::removeIf (was added in Java 8 to allow specifying tha lambda to check removing elements) method make sense to implement with lambda, Comparable does not
//learn Map::merge, especially using with null


