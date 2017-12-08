package samples.cuncurrency;

import java.time.ZonedDateTime;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class _12_ProduceConsumer {
    private static BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(16, true);
    private static Random random = new Random();

    public static void main(String[] args) {
        // Producer
        new Thread(_12_ProduceConsumer::generateData).start();
        new Thread(_12_ProduceConsumer::processViaTake).start();
        new Thread(_12_ProduceConsumer::processViaPoll).start();
//        new Thread(_12_ProduceConsumer::processViaElement).start();
//        new Thread(_12_ProduceConsumer::processViaRemove).start();
    }

    private static void generateData() {
        int data = random.nextInt(10);
        while (true) {
            try {
                int millis = random.nextInt(3000);
                Thread.sleep(millis);
                System.out.println(ZonedDateTime.now() + " - [Producer] - After " + millis + " MILLISECONDS putting data...");
                queue.put(data);
                System.out.println(ZonedDateTime.now() + " - [Producer] - Data:" + data + " has been put");
                System.out.println(ZonedDateTime.now() + " - [Producer] - Size:" + queue.size());
            } catch (InterruptedException ignore) {/*NOP*/}
        }
    }

    private static void processViaTake() {
        while (true) {
            try {
                for (int i = 0; i < 3; i++) {
                    System.out.println(ZonedDateTime.now() + " - [Consumer] - waiting for taking data...");
                    System.out.println(ZonedDateTime.now() + " - [Consumer] - taken:" + queue.take());
                    System.out.println(ZonedDateTime.now() + " - [Consumer] - Size:" + queue.size());
                }
                Thread.sleep(10000);
            } catch (InterruptedException ignore) {/*NOP*/}
        }

    }

    private static void processViaPoll() {
        while (true) {
            try {
                int timeout = random.nextInt(2000);
                TimeUnit unit = MILLISECONDS;
                System.out.println(ZonedDateTime.now() + " - [Consumer] - waiting for polling data " + timeout + " " + unit + "...");
                Integer data = queue.poll(timeout, unit);
                if (data == null) {
                    System.out.println(ZonedDateTime.now() + " - [Consumer] - No data yet");
                } else {
                    System.out.println(ZonedDateTime.now() + " - [Consumer] - taken:" + data);
                    System.out.println(ZonedDateTime.now() + " - [Consumer] - Size:" + queue.size());
                }
            } catch (InterruptedException ignore) {/*NOP*/}
        }
    }
}
