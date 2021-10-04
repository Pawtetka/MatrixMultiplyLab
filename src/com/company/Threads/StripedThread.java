package com.company.Threads;

import com.company.Matrix;

public class StripedThread extends Thread {
    private final int rowIndex;
    private final int columnIndex;
    private final int[] row;
    private final int[] column;
    private final Matrix resultMatrix;

    public StripedThread(int rowIndex, int columnIndex, int[] row, int[] column, Matrix resultMatrix){
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
        this.row = row;
        this.column = column;
        this.resultMatrix = resultMatrix;
    }

    @Override
    public void run(){
        int result = 0;
        for (int i = 0; i < row.length; i++) {
            result += row[i] * column[i];
        }

        resultMatrix.setValueInMatrix(rowIndex, columnIndex, result);
    }
}
