package samples.cuncurrency;

public class SynchronizedOnObject {
    static boolean run = true;
    static Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        // T0
        new Thread(() -> {
            // T1
            synchronized (lock) {
                run = false;
            }
        }).start();
        while (true) {
            synchronized (lock) {
                System.out.println(run);
            }
            Thread.sleep(100);
        }
    }
}
