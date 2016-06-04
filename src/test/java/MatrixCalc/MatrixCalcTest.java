package MatrixCalc;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Random;

import static org.junit.Assert.*;

public class MatrixCalcTest {
    private Random numberGenerator= new Random();
    @Rule
    public final ExpectedException exception =   ExpectedException.none();

    public final double[][] matrixA = {{1,2,3}, {4,5,6}, {6,7,8}};

    @Test
    public void addThrowsExceptionWithJaggedMatrix() {
        double[][] invalidMatrix = {{0,1,2}, {0,1,2,3}, {0,1,2}};
        exception.expect(IllegalArgumentException.class);
        MatrixCalc.add(matrixA, invalidMatrix);
    }

    @Test
    public void addThrowsExceptionWithEmptyMatrix() {
        double[][] emptyMatrix = new double[0][0];
        exception.expect(IllegalArgumentException.class);
        MatrixCalc.add(emptyMatrix, emptyMatrix);
    }

    @Test
    public void addThrowsExceptionWithMatricesOfDifferentSize() {
        double[][] sizeOneMatrix = {{1}};
        double[][] sizeTwoMatrix = {{1,2}, {3,4}};
        exception.expect(IllegalArgumentException.class);
        MatrixCalc.add(sizeOneMatrix, sizeTwoMatrix);
    }

    @Test
    public void addFunctionsProperly() {
        double[][] result = MatrixCalc.add(matrixA, matrixA);
        for (int y = 0; y < matrixA.length; y++) {
            for (int x = 0; x < matrixA[0].length; x++) {
                assertEquals(matrixA[y][x]+matrixA[y][x], result[y][x], 0.001);
            }
        }
    }

    @Test
    public void subtractThrowsExceptionWithJaggedMatrix() {
        double[][] invalidMatrix = {{0,1,2}, {0,1,2,3}, {0,1,2}};
        exception.expect(IllegalArgumentException.class);
        MatrixCalc.subtract(matrixA, invalidMatrix);
    }

    @Test
    public void subtractThrowsExceptionWithEmptyMatrix() {
        double[][] emptyMatrix = new double[0][0];
        exception.expect(IllegalArgumentException.class);
        MatrixCalc.subtract(emptyMatrix, emptyMatrix);
    }

    @Test
    public void subtractThrowsExceptionWithMatricesOfDifferentSize() {
        double[][] sizeOneMatrix = {{1}};
        double[][] sizeTwoMatrix = {{1,2}, {3,4}};
        exception.expect(IllegalArgumentException.class);
        MatrixCalc.add(sizeOneMatrix, sizeTwoMatrix);
    }

    @Test
    public void subtractFunctionsProperly() {
        double[][] result = MatrixCalc.subtract(matrixA, matrixA);
        for (int y = 0; y < matrixA.length; y++) {
            for (int x = 0; x < matrixA[0].length; x++) {
                assertEquals(matrixA[y][x]-matrixA[y][x], result[y][x], 0.001);
            }
        }
    }

    @Test
    public void scaleThrowsExceptionWithNonRectangularMatrix() {
        double[][] invalidMatrix = {{0,1,2}, {0,1,2,3}, {0,1,2}};
        exception.expect(IllegalArgumentException.class);
        MatrixCalc.scale(invalidMatrix, numberGenerator.nextDouble());
    }

    @Test
    public void scaleThrowsExceptionWithEmptyMatrix() {
        double[][] emptyMatrix = new double[0][0];
        exception.expect(IllegalArgumentException.class);
        MatrixCalc.scale(emptyMatrix, numberGenerator.nextDouble());
    }

    @Test
    public void scaleFunctionsProperly() {
        double scalar = numberGenerator.nextDouble();
        double[][] result = MatrixCalc.scale(matrixA, scalar);
        for (int y = 0; y < matrixA.length; y++) {
            for (int x = 0; x < matrixA[0].length; x++) {
                assertEquals(matrixA[y][x] * scalar, result[y][x], 0.001);
            }
        }
    }

    @Test
    public void multiplyThrowsExceptionWithNonRectangularMatrix() {
        double[][] invalidMatrix = {{0,1,2}, {0,1,2,3}, {0,1,2}};
        exception.expect(IllegalArgumentException.class);
        MatrixCalc.multiply(invalidMatrix, matrixA);
    }

    @Test
    public void multiplyThrowsExceptionWithEmptyMatrix() {
        double[][] emptyMatrix = new double[0][0];
        exception.expect(IllegalArgumentException.class);
        MatrixCalc.multiply(emptyMatrix, matrixA);
    }

    @Test
    public void multiplyThrowsExceptionWithIncomptabileMatrices() {
        double[][] twoByTwoMatrix = new double[2][2];
        double[][] threeByTwoMatrix = new double[3][2];
        exception.expect(IllegalArgumentException.class);
        MatrixCalc.multiply(twoByTwoMatrix, threeByTwoMatrix);
    }

    @Test
    public void multiplyFunctionsProperly() {
        //Cutoff is set to 3 so both naive and Strassen methods will be tested
        MatrixCalc.setStrassenCutoff(3);
        double[][] result = MatrixCalc.multiply(matrixA, matrixA);
        for (int row = 0; row < matrixA.length; row++) {
            for (int column = 0; column < matrixA[0].length; column++) {
                double resultWanted = 0;
                int rowA = 0;
                while (rowA < matrixA[0].length) {
                    for (int columnB = 0; columnB < matrixA.length; columnB++) {
                        resultWanted += (matrixA[row][rowA] * matrixA[columnB][column]);
                        rowA++;
                    }
                }
                assertEquals(resultWanted, result[row][column], 0.0001);
            }
        }
        //Test that result matrix is reduced to original size
        assertEquals(matrixA.length, result.length);
        assertEquals(matrixA[0].length, result[0].length);
    }

    @Test
    public void strassenCutoffSetWorksProperly() {
        MatrixCalc.setStrassenCutoff(15);
        assertEquals(15, MatrixCalc.getStrassenCutoff());
    }

    @Test
    public void strassenCutoffIsNotSetToLessThanThree() {
        MatrixCalc.setStrassenCutoff(2);
        assertNotEquals(2, MatrixCalc.getStrassenCutoff());
    }

}
