package MatrixMultipliers;

import java.util.HashMap;
import java.util.concurrent.RecursiveTask;

public class FoxAlgorithmForkJoin extends RecursiveTask<HashMap<String, Object>> {
    private final Matrix firstMatrix;
    private final Matrix secondMatrix;

    private final int stepI;
    private final int stepJ;

    public FoxAlgorithmForkJoin(Matrix first, Matrix second, int stepI, int stepJ) {
        this.firstMatrix = first;
        this.secondMatrix = second;

        this.stepI = stepI;
        this.stepJ = stepJ;
    }

    private Matrix multiplyBlock() {
        Matrix blockResult = new Matrix(firstMatrix.size, false);
        for (int i = 0; i < firstMatrix.size; i++) {
            for (int j = 0; j < secondMatrix.size; j++) {
                for (int k = 0; k < firstMatrix.size; k++) {
                    int value = firstMatrix.getValueFromMatrix(i, k) * secondMatrix.getValueFromMatrix(k, j);
                    blockResult.setValueInMatrix(i, j, blockResult.getValueFromMatrix(i, j) + value);
                }
            }
        }
        return blockResult;
    }

    @Override
    protected HashMap<String, Object> compute() {
        Matrix blockRes = multiplyBlock();

        HashMap<String, Object> result = new HashMap<>();
        result.put("blockRes", blockRes);
        result.put("stepI", stepI);
        result.put("stepJ", stepJ);

        return result;
    }
}
