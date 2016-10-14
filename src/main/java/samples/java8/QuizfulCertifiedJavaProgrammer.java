package samples.java8;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QuizfulCertifiedJavaProgrammer {

    static void a2Q11() throws IOException {
        String s;
        FileReader fR = new FileReader("C:\\T\\1.txt");
        BufferedReader bR = new BufferedReader(fR);
        while ((s = bR.readLine()) != null) System.out.print(s);
    }

    static void a2Q12() {
//        class LP {
//            int getF(){
//                return sF;
//            }
//        }
//        System.out.println("" + new LP().getF());
//
//        class LC extends LP {
//            static int sF = 1;
//            int getsF() {
//                return sF;
//            }
//        }
//        System.out.println("" + new LC().getF());
    }

    static void a2Q1() {
//        List<B> list1 = new ArrayList<B>();
//        list1.add(new C());
//        list1.add(new B());
//
//        List<? extends B> list2 = list1;
//        list2.add(new C());
//        list2.add(new B());
//
//        list2.get(0).foo();
//        list2.get(1).foo();
//        list2.get(2).foo();
    }

    static void a2Q20() {
//        CC cc = new CC();
//        System.out.print("" + cc.getF());
    }

    private static void a2Q19() {
//        for (int i = 0; i < 3; i++) {
//            T t = new T(i);
//            t.start();
//            t.join();
//        }
    }

    private static void a2Q18() {
        Object[] objs = new Integer[]{1};
        objs[0] = "2";
        System.out.println(objs[0]);
    }

    private static void a2Q16() {
//        for (C c : C.values()) {
//            System.out.print("" + c.digit);
//        }
    }

    private static void a2Q15() {
        ArrayList<Integer> integers = new ArrayList<Integer>();
        integers.add(5);
//        ArrayList<Integer>[] array = new ArrayList<Integer>[]{integers};
//        System.out.println(array[0].get(0));
    }

    private static void a2Q14() {
        Random random = new Random();
        int r = (int) random.nextLong(/*1*/);
        switch (r) {
            case 0:
                System.out.println("0");
                break;
            case 1:
                System.out.println("1");
                break;
            default:
                System.out.println("?");
        }
    }


    private static void a2Q6() {
//        List<int> a = new ArrayList<int>();
        List<int[]> a2 = new ArrayList<int[]>();
        List<Integer> a3 = new ArrayList<Integer>();
        List<Void> a4 = new ArrayList<Void>();
        List<Number> a5 = new ArrayList<Number>();
    }

    private static void a2Q4() {
        Object[] objects = {"1", 2, new Integer(3)};
        for (Object object : objects) {

        }
        for (int i = 0; i < objects.length; i++) {
        }

        for (Object o : Arrays.asList(objects)) {

        }
    }

    private static void a2Q3() {
//        N n = new N();
//        N.I i = n.new I();
//        i.p();
    }

    private static void qUnknown() {
        try {
            str.length();
        } catch (RuntimeException e) {
            throw e;
        } finally {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    private static void q20() {
//        int $a = 3;
//        byte _b = 5;
//        long array[_b] = new long[_b];
//        final double e = $a;
//        enum Colors{R,G,B};
    }

    private static void q18() {
//        long l = 3 & 2;
//        switch (l){
//            case 0:
//                System.out.println("0");
//            case 1:
//                System.out.println("1");
//            case 2:
//                System.out.println("2");
//            default:
//                System.out.println("Other");
//        }
    }

    static void m(List list) {
        list.add(0);
    }

    private static void q16() {
        List<String> list = new ArrayList<>();
        list.add("1");
        m(list);
        list.remove(0);
        System.out.println(list);
    }

    private static void q15() {
//        new samples.java8.QuizfulCertifiedJavaProgrammer().new IC();
//        samples.java8.QuizfulCertifiedJavaProgrammer oC = new samples.java8.QuizfulCertifiedJavaProgrammer().new CIC();
//
//        oC.new CIC();
//        oC.new CIC().new CIC();
    }

    private static void q14() {
        String str = "\to\ro\n\to";
        Pattern p = Pattern.compile("\\s+");
        Matcher m = p.matcher(str);
        int c = 0;
        while (m.find()) c++;
        System.out.println(c);
    }

    private static void q13() {
        Set set = new HashSet();
        set.add("1");
        set.add("1");
        List list = new ArrayList();
        list.add("1");
        list.add("1");
        Collections.addAll(set, list);
        System.out.println(set.size());
    }

    static String str;

    private static void q7() {
        String string = "a";
        StringBuffer buffer1 = new StringBuffer('b');
        StringBuffer buffer2 = new StringBuffer("c");
        System.out.println(string + buffer1 + buffer2.toString().substring(0, 1));
    }

    private static void q4() {
        Thread thread = new Thread() {
            public void run() {
                while (true) {
                    System.out.println("working...");
                }
            }
        };
        thread.start();

        try {
            thread.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void q2() {
        Thread thread = new Thread() {
            public void run() {
                System.out.print("1");
            }
        };
        thread.setDaemon(true);
        thread.start();
        System.out.print("2");
    }

    private static void q1() {
        String s = "www.quizful.net";
        String[] tokens = s.split(".");
        System.out.println(Arrays.toString(tokens));
    }

}

//class IC {
//}
//
//class CIC extends samples.java8.QuizfulCertifiedJavaProgrammer {
//}

//class PC {
//    protected int f;
//}
//
//class C1 extends PC {
//    protected int f;
//}
//
//class C2 extends PC {
//    private int f;
//}
//
//class C3 extends PC {
//    int f;
//}
//
//class C4 extends PC {
//    private int f;
//}


//class Annotations {
//}
//
//enum C {R, G, B}
//
//@interface Anno {
//    int a();
//}
//
//@interface An1 {
//    Annotations t();
//}
//
//@interface An2 {
//    Class clazz() default Annotations.class;
//}
//
//@interface An3 {
//    int value() default new Integer(5);
//    double dbl() default 0.0;
//}
//
//@interface An4 {
//    Anno aned();
//    C c() default C.G;
//}
//
//@interface An5 {
//    String string() default null;
//}
//
//@interface An6 {
//    int[] values();
//}

//class PE extends Exception {}
//class CE extends PE {}
//class B {
//    public void make() throws PE {}
//}
//class C1 extends B {
//    public void make() throws PE {}
//}
//class C2 extends B {
//    public void make() throws Exception {}
//}
//class C3 extends B {
//    public void make() throws CE {}
//}
//class C4 extends B {
//    public void make() throws Exception {}
//}
//class C5 extends B {
//    public void make() {}
//}

//interface F {void m();}
//interface S {void m();}
//class Cl1 implements F,S {
//    public void m(){}
//}
//interface T extends F,S {}
//class Cl2 extends Cl1 implements F,S,T{
//
//}

//static class N {
//        class I{
//            void p() {
//                System.out.println("hi");
//            }
//        }
//    }

//    public void main(int a) {
//    }

//    int main(int b) {
//        return 0;
//    }

//    public void main(Integer b) {
//    }
//
//    protected void main(double a) {
//    }
//
//    void main() throws Exception {
//    }

//    private void main(int b) {
//    }
//
//    public int main() {
//        return 0;
//    }

//public enum C {
//    R(1), G(2), B(3) {
//        public int digit() {
//            return digit--;
//        }
//    };
//    int digit;
//
//    C(int digit) {
//        this.digit = digit;
//    }
//
//    public int digit() {
//        return digit;
//    }
//}

//static class T extends Thread{
//    int f;
//    T(int f){
//        this.f = f;
//    }
//    public void run() {
//        System.out.println(f);
//    }
//}

//class PC {
//    int f = 1;
//
//    public PC() {
//        System.out.print("" + getF());
//    }
//
//    public int getF() {
//        return f;
//    }
//}
//
//class CC extends PC {
//    int f = 2;
//
//    public CC() {
//        super();
//        System.out.print("" + super.getF());
//    }
//
//    @Override
//    public int getF() {
//        return f;
//    }
//}

//class B {void foo(){}}
//class C extends B {void foo(){}}

//static int i;
//
//static synchronized void set() {
//        i = 1;
//    }
//
//static class R extends Thread {
//    public void run() {
//        synchronized ((samples.java8.QuizfulCertifiedJavaProgrammer.class)) {
//            try {
//                samples.java8.QuizfulCertifiedJavaProgrammer.class.wait();
//            } catch (InterruptedException e) {
//                System.out.println(i);
//            }
//        }
//    }
//}
//
//static class W extends Thread {
//    public void run() {
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            System.out.println(i);
//        }
//        set();
//        synchronized (samples.java8.QuizfulCertifiedJavaProgrammer.class) {
//            samples.java8.QuizfulCertifiedJavaProgrammer.class.notifyAll();
//        }
//    }
//}