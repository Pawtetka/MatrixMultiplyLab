package ProducerConsumer;

import java.util.Random;

public class Producer implements Runnable {
    private final Drop drop;
    private final int[] importantInfo;
    private final int stopNumber;

    public Producer(Drop drop, int[] importantInfo, int stopNumber) {
        this.drop = drop;
        this.importantInfo = importantInfo;
        this.stopNumber = stopNumber;
    }

    public void run() {
        Random random = new Random();
        for (int i : importantInfo) {
            drop.put(i);
            try {
                Thread.sleep(random.nextInt(2000));
            } catch (InterruptedException ignored) {
            }
        }
        drop.put(stopNumber);
    }
}
