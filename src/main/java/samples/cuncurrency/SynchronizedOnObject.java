package samples.cuncurrency;


// s1 - sout data1
// s2 - sout data2
// u1 - update data1
// u2 - update data2
// ur = update run
// rr - read run in while statement
// *  - happens-before
// sl - sleep
//
// T0 ----------------------*------------------------------------------>
//
// T1 --------------------------------*-------------------------------->
//
public class VolatileVariable {
    private static int data1;
    private static boolean run = true;

    public static void main(String[] args) throws InterruptedException {
        // T0
        new Thread(() -> {
            // T1
            while (run) ;
            System.err.println("data1=" + data1);
        }).start();
        data1 = 1;
        run = false;
    }
}
