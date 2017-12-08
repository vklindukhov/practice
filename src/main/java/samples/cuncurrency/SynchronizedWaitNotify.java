package samples.cuncurrency;

public class SynchronizedWaitNotify {
    private static Object lock = new Object();
    private static int x;
    private static int y;

    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


        synchronized (lock) {
            x = 1;
            lock.notify();
            y = 1;
            while (true);
        }
    }


}
