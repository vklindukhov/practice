package samples.concurrency;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Comparator.comparingInt;

public class _11_ThreadsWithPriority {
    private static volatile int counter;
    private static Random random = new Random();
    private static Map<PrintSelfJob, Long> jobStatistics = new ConcurrentHashMap<>();

    public static void main(String[] args) throws InterruptedException {
        List<PrintSelfJob> threads = createThreads();
        for (Thread thread : threads) thread.start();
        Thread.sleep(1000);
        Map<PrintSelfJob, Long> map = new TreeMap<>(comparingInt(job -> job.priority));
        map.putAll(jobStatistics);
        map.forEach((printSelfJob, value) -> System.err.println(printSelfJob + ": " + value));

    }

    private static List<PrintSelfJob> createThreads() {
        List<PrintSelfJob> threads = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            int priority = 1 + random.nextInt(9);
            PrintSelfJob job = new PrintSelfJob(i, priority);
            System.out.println("Created a job with id=" + i + ", priority=" + priority);
            threads.add(job);
        }
        return threads;
    }


    static class PrintSelfJob extends Thread {
        private int id;
        private int priority;


        public PrintSelfJob(int id, int priority) {
            super();
            this.id = id;
            this.priority = priority;
            this.setPriority(priority);
            this.setDaemon(true);
        }

        @Override
        public void run() {
            while (true) {

                jobStatistics.put(this, jobStatistics.getOrDefault(this, 0L) + 1L);
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PrintSelfJob that = (PrintSelfJob) o;
            return id == that.id && priority == that.priority;
        }

        @Override
        public int hashCode() {
            int result = id;
            result = 31 * result + priority;
            return result;
        }

        @Override
        public String toString() {
            return "PrintSelfJob{" +
                    "id=" + id +
                    ", priority=" + priority +
                    '}';
        }
    }
}
