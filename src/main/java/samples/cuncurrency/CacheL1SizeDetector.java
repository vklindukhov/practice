package samples.cuncurrency;

public class CacheL1SizeDetector {

    public static void main(String[] args) {
        byte[] arr = new byte[64 * 1024];

        for (int i = 0; i < 10; i++) {
            test(arr);
            System.out.println("-------------------------------------");
        }
    }

    private static void test(byte[] arr) {
        for (int len = 8192; len < arr.length; len += 8192) {
            long s = System.nanoTime();
            for (int j = 0; j < 100; j++) {
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
