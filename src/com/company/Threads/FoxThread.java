package com.company.Threads;

import com.company.Matrix;
import com.company.Multipliers.FoxMultiplier;

public class FoxThread extends Thread{
    private Matrix matrix1;
    private Matrix matrix2;
    private Matrix resultMatrix;
    private int iBlockCoordinate;
    private int kBlockCoordinate;

    public FoxThread(int i, int k, Matrix matrix1, Matrix matrix2, Matrix resultMatrix){
        iBlockCoordinate = i;
        kBlockCoordinate = k;

        this.matrix1 = matrix1;
        this.matrix2 = matrix2;
        this.resultMatrix = resultMatrix;
    }

    @Override
    public void run(){
        Matrix blockResult = calculateResultBlock();
        for (int i = 0; i < blockResult.size; i++) {
            for (int k = 0; k < blockResult.size; k++) {
                int value = blockResult.getValueFromMatrix(i, k) + resultMatrix.getValueFromMatrix(i + iBlockCoordinate, k + kBlockCoordinate);
                resultMatrix.setValueInMatrix(i + iBlockCoordinate, k + kBlockCoordinate, value);
            }
        }
    }

    private Matrix calculateResultBlock() {
        Matrix blockResult = new Matrix(matrix1.size, false);
        for (int i = 0; i < matrix1.size; i++) {
            for (int j = 0; j < matrix2.size; j++) {
                for (int k = 0; k < matrix1.size; k++) {
                    int value = matrix1.getValueFromMatrix(i, k) * matrix2.getValueFromMatrix(k, j);
                    blockResult.setValueInMatrix(i, j, blockResult.getValueFromMatrix(i, j) + value);
                }
            }
        }
        return blockResult;
    }
}
