package com.company.Multipliers;

import com.company.Matrix;
import com.company.Threads.FoxThread;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FoxMultiplier implements IMultiplier {
    private int threadsCount;

    public FoxMultiplier(int threadsCount){
        this.threadsCount = threadsCount;
    }

    public Matrix multiplyMatrices(Matrix matrix1, Matrix matrix2) {
        if (matrix1.size != matrix2.size)
            throw new IllegalArgumentException("Matrices must be same size");

        Matrix resultMatrix = new Matrix(matrix1.size, false);
        threadsCount = findNearestDivider(threadsCount, matrix1.size);

        int step = matrix1.size / threadsCount;

        ExecutorService exec = Executors.newFixedThreadPool(threadsCount);
        ArrayList<Future> threads = new ArrayList<>();

        int[][] matrixOfSizesI = new int[threadsCount][threadsCount];
        int[][] matrixOfSizesJ = new int[threadsCount][threadsCount];

        int stepI = 0;
        for (int i = 0; i < threadsCount; i++) {
            int stepJ = 0;
            for (int j = 0; j < threadsCount; j++) {
                matrixOfSizesI[i][j] = stepI;
                matrixOfSizesJ[i][j] = stepJ;
                stepJ += step;
            }
            stepI += step;
        }

        for (int l = 0; l < threadsCount; l++) {
            for (int i = 0; i < threadsCount; i++) {
                for (int j = 0; j < threadsCount; j++) {
                    int stepI0 = matrixOfSizesI[i][j];
                    int stepJ0 = matrixOfSizesJ[i][j];

                    int stepI1 = matrixOfSizesI[i][(i + l) % threadsCount];
                    int stepJ1 = matrixOfSizesJ[i][(i + l) % threadsCount];

                    int stepI2 = matrixOfSizesI[(i + l) % threadsCount][j];
                    int stepJ2 = matrixOfSizesJ[(i + l) % threadsCount][j];

                    FoxThread thread = new FoxThread(stepI0, stepJ0, copyIntBlock(matrix1, stepI1, stepJ1, step),
                            copyIntBlock(matrix2, stepI2, stepJ2, step), resultMatrix);
                    threads.add(exec.submit(thread));
                }
            }
        }

        for (Future mapFuture : threads) {
            try {
                mapFuture.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        exec.shutdown();
        return resultMatrix;
    }

    private Matrix copyIntBlock(Matrix matrix, int i, int j, int size) {
        Matrix block = new Matrix(size, false);
        for (int k = 0; k < size; k++) {
            System.arraycopy(matrix.getValueFromRowInMatrix(k + i), j, block.getValueFromRowInMatrix(k), 0, size);
        }
        return block;
    }

    private int findNearestDivider(int s, int p) {
        int i = s;
        while (i > 1) {
            if (p % i == 0) break;
            if (i >= s) {
                i++;
            } else {
                i--;
            }
            if (i > Math.sqrt(p)) i = Math.min(s, p / s) - 1;
        }

        return i >= s ? i : i != 0 ? p / i : p;
    }
}
