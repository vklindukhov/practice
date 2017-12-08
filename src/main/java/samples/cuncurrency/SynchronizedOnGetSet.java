package samples.cuncurrency;

public class SynchronizedOnGetSet {
    private static boolean run = true;

    private synchronized static boolean isRun() {
        return run;
    }

    private static synchronized void setRun(boolean run) {
        SynchronizedOnGetSet.run = run;
    }

    public static void main(String[] args) throws InterruptedException {
        // T0
        new Thread(() -> {
            // T1
            setRun(false);
        }).start();
        while (isRun()) ;
    }
}
