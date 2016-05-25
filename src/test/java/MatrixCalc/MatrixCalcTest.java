package MatrixCalc;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class MatrixCalcTest {
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

}
