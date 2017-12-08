package samples.cuncurrency;

public class _3_IncrementVolatileVariableWithVolatileStopFlags {
    private static volatile int counter;
    private static volatile boolean finish1;
    private static volatile boolean finish2;

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
