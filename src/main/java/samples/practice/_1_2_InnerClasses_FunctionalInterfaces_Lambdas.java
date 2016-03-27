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


public class _1_2_InnerClasses_FunctionalInterfaces_Lambdas {
    public static void main(String[] args) {
        Lambda lambda = () -> System.out.println("Hello world from lambda");
        lambda.m();
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
            System.out.println("Interface");
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