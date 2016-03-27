package samples.algorithms.famousproblem;


public class TasksResolver {


    //    Wrong Solution
    public static int maxSubArrayWrong(int[] A) {
        int sum = 0;
        int maxSum = Integer.MIN_VALUE;

        for (int aA : A) {
            sum += aA;
            maxSum = Math.max(maxSum, sum);

            if (sum < 0)
                sum = 0;
        }

        return maxSum;
    }

    public static int maxSubArrayDynamic(int[] A) {
        int max = A[0];
        int[] sum = new int[A.length];
        sum[0] = A[0];

        for (int i = 1; i < A.length; i++) {
            sum[i] = Math.max(A[i], sum[i - 1] + A[i]);
            max = Math.max(max, sum[i]);
        }

        return max;
    }

    public static int maxSubArraySimple(int[] A) {
        int newsum = A[0];
        int max = A[0];
        for (int i = 1; i < A.length; i++) {
            newsum = Math.max(newsum + A[i], A[i]);
            max = Math.max(max, newsum);
        }
        return max;
    }
}
