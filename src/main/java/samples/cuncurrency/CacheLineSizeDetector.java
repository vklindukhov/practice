package samples.cuncurrency;

public class CacheLineSizeDetector {
    final static int ARRAY_SIZE = 16 * 1024 * 1024;

    public static void main(String[] args) {
        byte[] arr = new byte[ARRAY_SIZE];

        for (int i = 0; i < 10; i++) {
            test(arr);
            System.out.println("-------------------------------------");
        }
    }

    private static void test(byte[] arr) {
        for (int step = 4; step <= 512; step *= 2) {
            long s = System.nanoTime();
            int sum = 0;
            for (int j = 0; j < 100; j++) {
                for (int k = 0; k < arr.length; k += step) {
                    sum += arr[k];
                }
            }
            if (sum > 0) throw new Error();
            int stepCount = ARRAY_SIZE / step;
            long e = System.nanoTime();

            long t = e - s;

            System.out.println("len:" + step + ", time:" + t + " time/step:" + (t / stepCount));

        }
    }
}
