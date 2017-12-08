package samples.cuncurrency;

public class _1_IncrementVariableWithNonVolatileStopFlags {
    private static int counter;
    private static boolean finish1;
    private static boolean finish2;

//    may not finish
//    finish1 || finish2 could be saved in cache of some core
    public static void main(String[] args) throws InterruptedException {
        // T0
        new Thread(() -> {
            // T1
            for (int i = 0; i < 10_000_000; i++) counter++;
            finish1 = true;
        }).start();
        new Thread(() -> {
            // T2
            for (int i = 0; i < 10_000_000; i++) counter++;
            finish2 = true;
        }).start();
        while (!finish1 || !finish2);
        System.out.println(counter);
    }
}
