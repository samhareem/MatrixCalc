/**
 * Created by sami on 28.5.2016.
 */
import MatrixCalc.MatrixCalc;

import java.util.ArrayList;
import java.util.Random;

public class main {

    public static void main(String[] args) {
        double[][] test = {{1, 8, -9, 7, 5}, {0, 1, 0, 4, 4}, {0, 0, 1, 2, 5}, {0, 0, 0, 1, -5}, {0, 0, 0, 0, 1}};
        long aikaAlussa = System.currentTimeMillis();
        double[][] test2 = MatrixCalc.invertMatrix(test);
        long aikaLopussa = System.currentTimeMillis();
        test = MatrixCalc.multiply(test, test2);
        for (int i = 0; i < test.length; i++) {
            for (int j = 0; j < test.length; j++) {
                System.out.println(test[i][j]);
            }
        }


    }
        /*Random numGenerator = new Random();
        double[][] matrixA = createMatrix(2500, numGenerator);
        double[][] matrixB = createMatrix(2500, numGenerator);

        MatrixCalc.setStrassenCutoff(513);
        for (int i = 0; i < 5; i++) {
            long aikaAlussa = System.currentTimeMillis();
            MatrixCalc.multiply(matrixA, matrixB);
            long aikaLopussa = System.currentTimeMillis();
            System.out.println("Operaatioon kului aikaa: " + (aikaLopussa - aikaAlussa) + "ms.");
        }

        MatrixCalc.setStrassenCutoff(257);
        for (int i = 0; i < 5; i++) {
            long aikaAlussa = System.currentTimeMillis();
            MatrixCalc.multiply(matrixA, matrixB);
            long aikaLopussa = System.currentTimeMillis();
            System.out.println("Operaatioon kului aikaa: " + (aikaLopussa - aikaAlussa) + "ms.");
        }

        MatrixCalc.setStrassenCutoff(129);
        for (int i = 0; i < 5; i++) {
            long aikaAlussa = System.currentTimeMillis();
            MatrixCalc.multiply(matrixA, matrixB);
            long aikaLopussa = System.currentTimeMillis();
            System.out.println("Operaatioon kului aikaa: " + (aikaLopussa - aikaAlussa) + "ms.");
        }
    }

    private static double[][] createMatrix(int size, Random numGenerator) {
        double[][] ret = new double[size][size];
        for (int y = 0; y < size; y++) {
            for (int x =0; x < size; x++) {
                ret[y][x] = numGenerator.nextDouble();
            }
        }
        return ret;
    }*/
}
