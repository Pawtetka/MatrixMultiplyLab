package MatrixMultipliers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.*;

public class FoxAlgorithm extends RecursiveTask<Matrix> {
    private int threadsCount;
    private int[][] matrixOfSizesI;
    private int[][] matrixOfSizesJ;
    private int step;
    private Matrix matrix1;
    private Matrix matrix2;
    private Matrix resultMatrix;

    public FoxAlgorithm(int threadsCount, Matrix matrix1, Matrix matrix2){

        this.threadsCount = threadsCount;
        this.matrix1 = matrix1;
        this.matrix2 = matrix2;
        multiplyMatrices(this.matrix1, this.matrix2);
    }

    public void multiplyMatrices(Matrix matrix1, Matrix matrix2) {
        if (matrix1.size != matrix2.size)
            throw new IllegalArgumentException("Matrices must be same size");

        resultMatrix = new Matrix(matrix1.size, false);
        threadsCount = findNearestDivider(threadsCount, matrix1.size);

        step = matrix1.size / threadsCount;

        matrixOfSizesI = new int[threadsCount][threadsCount];
        matrixOfSizesJ = new int[threadsCount][threadsCount];

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
    }

    @Override
    protected Matrix compute(){
        List<RecursiveTask<HashMap<String, Object>>> tasks = new ArrayList<>();

        for (int l = 0; l < threadsCount; l++) {
            for (int i = 0; i < threadsCount; i++) {
                for (int j = 0; j < threadsCount; j++) {
                    int stepI0 = matrixOfSizesI[i][j];
                    int stepJ0 = matrixOfSizesJ[i][j];

                    int stepI1 = matrixOfSizesI[i][(i + l) % threadsCount];
                    int stepJ1 = matrixOfSizesJ[i][(i + l) % threadsCount];

                    int stepI2 = matrixOfSizesI[(i + l) % threadsCount][j];
                    int stepJ2 = matrixOfSizesJ[(i + l) % threadsCount][j];

                    FoxAlgorithmForkJoin task =
                            new FoxAlgorithmForkJoin(
                                    copyIntBlock(matrix1, stepI1, stepJ1, step),
                                    copyIntBlock(matrix2, stepI2, stepJ2, step),
                                    stepI0,
                                    stepJ0);

                    tasks.add(task);
                    task.fork();
                }
            }
        }

        for (RecursiveTask<HashMap<String, Object>> task : tasks) {
            HashMap<String, Object> result = task.join();

            Matrix blockRes = (Matrix) result.get("blockRes");
            int stepI = (int) result.get("stepI");
            int stepJ = (int) result.get("stepJ");

            for (int i = 0; i < blockRes.size; i++) {
                for (int j = 0; j < blockRes.size; j++) {
                    resultMatrix.setValueInMatrix(i + stepI, j + stepJ,
                            resultMatrix.getValueFromMatrix(i + stepI, j + stepJ) + blockRes.getValueFromMatrix(i, j));
                }
            }
        }

        return this.resultMatrix;
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
