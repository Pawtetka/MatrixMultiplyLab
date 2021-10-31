package MatrixMultipliers;

import java.util.Random;

public class Matrix {
    private int[][] matrix;
    public int size;

    public Matrix(int size, boolean isFilled){
        this.size = size;
        matrix = new int[size][size];

        if(isFilled){
            fillMatrix();
        }
    }

    private void fillMatrix(){
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                matrix[i][j] = new Random().nextInt();
            }
        }
    }

    public void setValueInMatrix(int row, int column, int value){
        matrix[row][column] = value;
    }

    public int getValueFromMatrix(int row, int column){
        return matrix[row][column];
    }

    public int[] getValueFromRowInMatrix(int row){
        return matrix[row];
    }

    public void transposeMatrix(){
        int[][] newMatrix = new int[size][size];
        for(int i = 0; i < matrix.length; i++){
            for(int k = 0; k < matrix.length; k++){
                newMatrix[i][k] = matrix[k][i];
            }
        }
        matrix = newMatrix;
    }

    public void print(){
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.printf("%10.2f", matrix[i][j]);
            }
            System.out.println();
        }
    }
}
