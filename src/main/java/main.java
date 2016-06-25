/**
 * Created by sami on 28.5.2016.
 */
import MatrixCalc.MatrixCalc;

import java.util.Random;

public class main {
    static Random random = new Random();

    public static void main(String[] args) {
        double[][] matrixA = {{2,3,1,5}, {1,0,3,1}, {0,2,-3,2}, {0,2,3,1}};
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

        System.out.println("\nMatrix multiplication uses the Strassen method recursively for large matrices, but switches to the naive method once matrices are smaller than 257 values long for performance reasons.");
        System.out.println("Let's multiply a random matrix 2000 values long with itself, using the default cutoff point for naive multiplication");
        double[][] randomMatrix = createRandomMatrix(2000);
        long startStrassen = System.currentTimeMillis();
        MatrixCalc.multiply(randomMatrix, randomMatrix);
        long endStrassen = System.currentTimeMillis();
        System.out.println("This took " + (endStrassen - startStrassen) + " milliseconds.");

        System.out.println("\nWe can also calculate the determinant of a matrix. For the first of our matrices this is " + MatrixCalc.determinant(matrixA));
        System.out.println("Since it's not zero, we can also calculate the inversion matrix for our first matrix,which is...");
        System.out.println("\n");
        resultMatrix = MatrixCalc.invert(matrixA);
        printMatrix(resultMatrix);
        System.out.println("\n...yeah, bad example.");
        System.out.println("\nFor the source code and the rest of the documentation, go check out www.github.com/samhareem/MatrixCalc\n");

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
