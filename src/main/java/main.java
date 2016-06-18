/**
 * Created by sami on 28.5.2016.
 */
import MatrixCalc.MatrixCalc;

import java.util.Random;

public class main {
    static Random random = new Random();

    public static void main(String[] args) {
        double[][] matrixA = {{4,1,15,6}, {7,2,-5,12}, {8,15,16,21}, {21, -5, 1, 12}};
        double[][] matrixB = {{12,5,8,1}, {-12,3,7,0}, {2,45,0,52}, {12, 2, 2, 11}};
        double[][] resultMatrix;

        System.out.println("This is an example main to showcase the different features of the library.");
        System.out.println("These are our test matrices:");
        System.out.println("\n");
        printMatrix(matrixA);
        System.out.println("\n");
        printMatrix(matrixB);
        System.out.println("\nAdd simply adds together the corresponding cells of the matrices");
        resultMatrix = MatrixCalc.add(matrixA, matrixB);
        System.out.println("Result of addition");
        printMatrix(resultMatrix);
        System.out.println("\n");
        System.out.println("Subtract subtracts corresponding cells of the matrices");

        resultMatrix = MatrixCalc.subtract(matrixA, matrixB);
        System.out.println("\nResult of subtraction");
        printMatrix(resultMatrix);
        System.out.println("\n");
        System.out.println("Scale multiplies all the values of the matrix with the scalar");
        resultMatrix = MatrixCalc.scale(matrixA, 4);
        System.out.println("\nResult of scaling matrixA with 4");
        printMatrix(resultMatrix);

//        System.out.println("\nMatrix multiplication uses the Strassen method recursively for large matrices, but switches to the naive method once matrices are smaller than 257 values long for performance reasons.");
//        System.out.println("Let's multiply a random matrix 2000 values long with itself, using the default cutoff point for naive multiplication");
//        double[][] randomMatrix = createRandomMatrix(2000);
//        long startStrassen = System.currentTimeMillis();
//        MatrixCalc.multiply(randomMatrix, randomMatrix);
//        long endStrassen = System.currentTimeMillis();
//        System.out.println("This took " + (endStrassen - startStrassen) + " milliseconds.");

        System.out.println("\nWe can also calculate the determinant of a matrix. For the first of our matrices this is " + MatrixCalc.determinant(matrixA));
        System.out.println("Since it's not zero, we can also calculate the inversion matrix for our first matrix,which is...");
        System.out.println("\n");
        resultMatrix = MatrixCalc.invertMatrix(matrixA);
        printMatrix(resultMatrix);
        System.out.println("\n...yeah, bad example.");
        System.out.println("\nFor the source code and the rest of the documentation, go check out www.github.com/samhareem/MatrixCalc\n");

        System.out.println("Average time of operation using a 1000 value long random matrix, each operation having been run 10 times:");
        double[][] test = createRandomMatrix(1000);

        long addTime = 0;
        for (int i = 0; i < 10; i++) {
            long start = System.currentTimeMillis();
            MatrixCalc.add(test, test);
            long end = System.currentTimeMillis();
            addTime += end - start;
        }
        System.out.println("Add: " + addTime/10 + " ms");

        long subtractTime = 0;
        for (int i = 0; i < 10; i++) {
            long start = System.currentTimeMillis();
            MatrixCalc.subtract(test, test);
            long end = System.currentTimeMillis();
            subtractTime += end - start;
        }
        System.out.println("Subtract: " + subtractTime/10 + " ms");

        long strassenTime129 = 0;
        MatrixCalc.setStrassenCutoff(129);
        for (int i = 0; i < 10; i++) {
            long start = System.currentTimeMillis();
            MatrixCalc.multiply(test, test);
            long end = System.currentTimeMillis();
            strassenTime129 += end - start;
        }
        System.out.println("Strassen, cutoff at 129: " + strassenTime129/10 + " ms");

        long strassenTime257 = 0;
        MatrixCalc.setStrassenCutoff(257);
        for (int i = 0; i < 10; i++) {
            long start = System.currentTimeMillis();
            MatrixCalc.multiply(test, test);
            long end = System.currentTimeMillis();
            strassenTime257 += end - start;
        }
        System.out.println("Strassen, cutoff at 257: " + strassenTime257/10 + " ms");

        long strassenTime513 = 0;
        MatrixCalc.setStrassenCutoff(513);
        for (int i = 0; i < 10; i++) {
            long start = System.currentTimeMillis();
            MatrixCalc.multiply(test, test);
            long end = System.currentTimeMillis();
            strassenTime513 += end - start;
        }
        System.out.println("Strassen, cutoff at 513: " + strassenTime513/10 + " ms");

        long strassenTime1025 = 0;
        MatrixCalc.setStrassenCutoff(1025);
        for (int i = 0; i < 10; i++) {
            long start = System.currentTimeMillis();
            MatrixCalc.multiply(test, test);
            long end = System.currentTimeMillis();
            strassenTime1025 += end - start;
        }
        System.out.println("Strassen, cutoff at 1025: " + strassenTime1025/10 + " ms");

        long determinantTime = 0;
        for (int i = 0; i < 10; i++) {
            long start = System.currentTimeMillis();
            MatrixCalc.multiply(test, test);
            long end = System.currentTimeMillis();
            determinantTime += end - start;
        }
        System.out.println("Determinant: " + determinantTime/10 + " ms");

        long inverseTime = 0;
        MatrixCalc.setStrassenCutoff(257);
        for (int i = 0; i < 10; i++) {
            long start = System.currentTimeMillis();
            MatrixCalc.multiply(test, test);
            long end = System.currentTimeMillis();
            inverseTime += end - start;
        }
        System.out.println("Inverse: " + inverseTime/10 + " ms");
    }

    public static void printMatrix(double[][] matrix) {
        for (int row = 0; row < matrix.length; row++) {
            for (int column = 0; column < matrix[0].length; column++) {
                System.out.print(matrix[row][column] + " ");
            }
            System.out.println("");
        }
    }

    public static double[][] createRandomMatrix(int size) {
        double[][] ret = new double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                ret[i][j] = random.nextDouble();
            }
        }
        return ret;
    }
}
