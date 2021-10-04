package com.company.Multipliers;

import com.company.Matrix;
import com.company.Threads.StripedThread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class StripedMultiplier implements IMultiplier {
    private final int threadsCount;

    public StripedMultiplier(int threadsCount){
        this.threadsCount = threadsCount;
    }

    public Matrix multiplyMatrices(Matrix matrix1, Matrix matrix2) {
        if (matrix1.size != matrix2.size)
            throw new IllegalArgumentException("Matrices must be same size");

        return calculateResultMatrix(matrix1, matrix2, new Matrix(matrix1.size, false));
    }

    private Matrix calculateResultMatrix(Matrix matrix1, Matrix matrix2, Matrix resultMatrix){
        matrix2.transposeMatrix();
        ExecutorService executor = Executors.newFixedThreadPool(this.threadsCount);
        List<Future> threads = new ArrayList<>();

        for (int j = 0; j < matrix2.size; j++) {
            for (int i = 0; i < matrix1.size; i++) {
                StripedThread thread = new StripedThread(i, j, matrix1.getValueFromRowInMatrix(i),
                        matrix2.getValueFromRowInMatrix(j), resultMatrix);
                threads.add(executor.submit(thread));
            }
        }

        for (Future mapFuture : threads) {
            try {
                mapFuture.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();
        matrix2.transposeMatrix();
        return resultMatrix;

    }

}
