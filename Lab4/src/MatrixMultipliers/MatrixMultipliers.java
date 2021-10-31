package MatrixMultipliers;

import java.util.concurrent.ForkJoinPool;

public class MatrixMultipliers {
    public static void main(String[] args) {
        simpleRun(false);
    }

    public static void simpleRun(boolean printMatrices) {
        int size = 3000;

        Matrix first = new Matrix(size, true);
        Matrix second = new Matrix(size, true);

        int nThread = Runtime.getRuntime().availableProcessors();

        long currTimeFox = System.nanoTime();
        ForkJoinPool forkJoinPool = new ForkJoinPool(nThread);
        Matrix resultMatrix = forkJoinPool.invoke(new FoxAlgorithm(nThread, first, second));
        currTimeFox = System.nanoTime() - currTimeFox;

        if (printMatrices) resultMatrix.print();

        System.out.println("Time for Fox Algorithm: " + currTimeFox / 1_000_000);
        System.out.println();
        System.out.println("SpeedUp (timeFox / timeFoxForkJoin): " + (double) 8306 / (currTimeFox / 1_000_000));
    }
}
