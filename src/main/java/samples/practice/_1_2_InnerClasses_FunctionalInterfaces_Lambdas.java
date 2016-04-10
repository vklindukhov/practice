package samples.practice;

//1 - D
//2 - B
//3 - A
//4 - C
//5 - C
//6 - B
//7 - D
//8 - C
//9 - C
//10 - D


import java.io.Console;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

//class Clazz1<Type> {
//    void m(Type type) {
//        System.out.println("Type");
//    }
//
//    void m(Object obj) {
//        System.out.println("obj");
//    }
//}
abstract interface I{
    default void m(){}
}
class Cl implements I{
    public void m(){}
}

public class _1_2_InnerClasses_FunctionalInterfaces_Lambdas {
    public static void main(String[] args)  {
        Console console = System.console();
        console.readPassword();
        PrintWriter writer = console.writer();
        writer.write(new char[1]);
    }



    private static void example2() {
        Clazz instance = new Clazz();
        int var = 0;
        if(instance instanceof Interface) var+=1;
        if(instance instanceof Clazz) var+=2;
        if(null instanceof ClazzImplementsInterface) var+=3;
        System.out.println(var);
    }

    static interface Interfaze {}
    static class Clazz{}
    static class ClazzImplementsInterface implements Interface{}

    private static void example() {
        _1_2_InnerClasses_FunctionalInterfaces_Lambdas a = new B();
        B b = new B();
        Method[] methods = b.getClass().getMethods();
        System.out.println(methods);
    }


    String field = "A";
    public void m(){
        System.out.println(field);
    }
    static class B extends _1_2_InnerClasses_FunctionalInterfaces_Lambdas{
        String field = "B";
    }


    private int f = new StaticClass().f;

    static class StaticClass {
        private int f = new _1_2_InnerClasses_FunctionalInterfaces_Lambdas().f;

    }

    interface StaticInterface {

    }

    class InnerClass {

    }

    static void innerClasses() {
        class LocalClass {
        }
        Object staticClassInstance = new StaticClass();
        Object innerClassInstance = new _1_2_InnerClasses_FunctionalInterfaces_Lambdas().new InnerClass();
        Object localClassInstance = new LocalClass();
        Object anonymousClassInstance = returnAnonymousClass();
        System.out.println(staticClassInstance.getClass().getName());
        System.out.println(innerClassInstance.getClass().getName());
        System.out.println(localClassInstance.getClass().getName());
        System.out.println(anonymousClassInstance.getClass().getName());
    }

    static Object returnAnonymousClass() {
        return new Object() {
        };
    }


    interface Interface {
        class StaticClass {
        }

        interface StaticInterface {
        }

        default Object returnLocalClass() {
            class LocalClass {
            }
            return new LocalClass();
        }

        default Object returnAnonymousClass() {
            return new Object() {
            };
        }
    }


    abstract class C {
        public void m() {
            System.out.println("Class");
        }
    }

    interface I {
        default void m() {
            System.out.println("Interfaze");
        }
    }

    class CI extends C implements I {
        public void d() {
            m();
        }
    }

    interface I1 {
        default void m() {
            System.out.println("I1");
        }
    }


    interface I2 {
        default void m() {
            System.out.println("I2");
        }
    }

    class IImpl implements I1, I2 {
        @Override
        public void m() {

        }
    }


    @FunctionalInterface
    interface Lambda {
        void m();

        boolean equals(Object obj);
    }

    @FunctionalInterface
    interface Lambda2 extends Lambda {
        boolean equals(Object obj);
    }
}