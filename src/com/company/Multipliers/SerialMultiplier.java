package com.company.Multipliers;

import com.company.Matrix;

public class SerialMultiplier implements IMultiplier {

    public Matrix multiplyMatrices(Matrix matrix1, Matrix matrix2) {
        if(matrix1.size != matrix2.size){
            throw new IllegalArgumentException("Matrices must be same size");
        }

        Matrix resultMatrix = new Matrix(matrix1.size, false);
        matrix2.transposeMatrix();
        for (int row = 0; row < matrix1.size; row++) {
            for (int column = 0; column < matrix2.size; column++) {
                int value = calculateValue(matrix1, matrix2, row, column);
                resultMatrix.setValueInMatrix(row, column, value);
            }
        }
        matrix2.transposeMatrix();
        return resultMatrix;
    }

    private int calculateValue(Matrix matrix1, Matrix matrix2, int row, int column){
        int result = 0;
        for (int i = 0; i < matrix1.size; i++) {
            result += matrix1.getValueFromMatrix(row, i) * matrix2.getValueFromMatrix(column, i);
        }
        return result;
    }

}
