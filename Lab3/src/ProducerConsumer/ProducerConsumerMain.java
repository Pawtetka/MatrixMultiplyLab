package ProducerConsumer;

public class ProducerConsumerMain {
    public static void main(String[] args) {
        int[] importantInfo = new int[1000];
        for (int i = 0; i < importantInfo.length; i++) importantInfo[i] = i + 1;

        Drop drop = new Drop();
        (new Thread(new Producer(drop, importantInfo, 0))).start();
        (new Thread(new Consumer(drop, 0))).start();
    }
}
