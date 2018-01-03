package samples.concurrency;

public class CacheL2SizeDetector {

    public static void main(String[] args) {
        byte[] arr = new byte[512 * 1024];

        for (int i = 0; i < 10; i++) {
            test(arr);
            System.out.println("-------------------------------------");
        }
    }

    private static void test(byte[] arr) {
        for (int len = 64 * 1024; len < arr.length; len += 64 * 1024) {
            long s = System.nanoTime();
            for (int j = 0; j < 1000; j++) {
                for (int k = 0; k < len; k += 64) {
                    arr[k] = 1;
                }
            }
            long e = System.nanoTime();
            long t = e - s;
            System.out.println("len:" + len + ", time:" + t + " 10time/len:" + (10 * t / len));

        }
    }
}
