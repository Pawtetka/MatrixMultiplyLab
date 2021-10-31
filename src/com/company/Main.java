package com.company;

import com.company.Multipliers.IMultiplier;
import com.company.Multipliers.StripedMultiplier;
import com.company.Multipliers.FoxMultiplier;
import com.company.Multipliers.SerialMultiplier;
import com.company.Matrix;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        runTestWithMatrix(3000, false);
        //runBigTest();
    }

    private static void runTestWithMatrix(int size, boolean printMatrices){
        System.out.println("Matrix size:" + size);

        Matrix matrix1 = new Matrix(size, true);
        Matrix matrix2 = new Matrix(size, true);

        //IMultiplier serialMultiplier = new SerialMultiplier();
        //IMultiplier stripedMultiplier = new StripedMultiplier(12);
        IMultiplier foxMultiplier = new FoxMultiplier(12);

        /*long currentTime = System.nanoTime();
        Matrix resultMatrix = serialMultiplier.multiplyMatrices(matrix1, matrix2);
        long timeResult = System.nanoTime() - currentTime;
        System.out.printf("Time for serial: %d ms\n", timeResult / 1_000_000);

        currentTime = System.nanoTime();
        resultMatrix = stripedMultiplier.multiplyMatrices(matrix1, matrix2);
        timeResult = System.nanoTime() - currentTime;
        System.out.printf("Time for striped: %d ms\n", timeResult / 1_000_000);*/

        long currentTime = System.nanoTime();
        Matrix resultMatrix = foxMultiplier.multiplyMatrices(matrix1, matrix2);
        long timeResult = System.nanoTime() - currentTime;
        System.out.printf("Time for fox: %d ms\n", timeResult / 1_000_000);

        if(printMatrices){
            System.out.println("\nFirst double matrix:");
            matrix1.print();
            System.out.println("\nSecond double matrix:");
            matrix2.print();
            System.out.println("\nResult double matrix:");
            resultMatrix.print();
        }
    }

    private static void runBigTest(){
        int[] sizes = new int[]{1000, 1500, 2000, 2500, 3000};
        int[] threadsCounts = new int[]{2, 4, 8, 12};
        System.out.println("Size\t\t\tSerial time\t\t\tStriped 2 threads\t\tFox 2 threads\t\tStriped 4 threads\t\tFox 4 threads\t\tStriped 8 threads\t\tFox 8 threads\t\tStriped 12 threads\t\tFox 12 threads");
        for (int size: sizes){
            System.out.printf("%d\t\t\t", size);

            Matrix matrix1 = new Matrix(size, true);
            Matrix matrix2 = new Matrix(size, true);
            IMultiplier serialMultiplier = new SerialMultiplier();

            System.out.printf("%d ms\t\t\t\t", getTime(serialMultiplier, matrix1, matrix2));

            for (int nThread: threadsCounts){
                IMultiplier stripedMultiplier = new StripedMultiplier(nThread);
                IMultiplier foxMultiplier = new FoxMultiplier(nThread);

                System.out.printf("%d ms\t\t\t\t\t", getTime(stripedMultiplier, matrix1, matrix2));
                System.out.printf("%d ms\t\t\t\t", getTime(foxMultiplier, matrix1, matrix2));
            }
            System.out.println();
        }
    }

    private static long getTime(IMultiplier multiplier, Matrix matrix1, Matrix matrix2){
        long[] execTimes = new long[3];
        for (int i = 0; i < 3; i++) {
            long currTime = System.nanoTime();
            multiplier.multiplyMatrices(matrix1, matrix2);
            execTimes[i] = System.nanoTime() - currTime;
        }

        return Arrays.stream(execTimes).sum()/3/1_000_000;
    }
}
