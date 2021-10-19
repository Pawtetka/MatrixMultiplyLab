package ProducerConsumer;

import java.util.Random;

public class Consumer implements Runnable {
    private final Drop drop;
    private final int stopNumber;

    public Consumer(Drop drop, int stopNumber) {
        this.drop = drop;
        this.stopNumber = stopNumber;
    }

    public void run() {
        Random random = new Random();
        for (int number = drop.take(); number != stopNumber; number = drop.take()) {
            System.out.format("MESSAGE RECEIVED: %s%n", number);
            try {
                Thread.sleep(random.nextInt(2000));
            } catch (InterruptedException ignored) {
            }
        }
    }
}

