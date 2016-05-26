package MatrixCalc;

/**
 * Contains methods for calculating matrix products. The class currently supports addition, subtraction,
 * scaling, multiplication, and determinant calculations. The matrices are required to be square or rectangular
 * (depending on calculation) 2-dimensional arrays of doubles.
 */
public final class MatrixCalc {

    private MatrixCalc() {
        // Utility class, constructor not called
    }

    /**
     * Checks that the two matrices have identical size and returns the result of the addition.
     *
     * @param firstMatrix  First matrix to be used in the calculation
     * @param secondMatrix Second matrix to be used in the calculation
     * @return Returns the result of the addition as a 2-dimensional double array
     */
    public static double[][] add(double[][] firstMatrix, double[][] secondMatrix) {
        if (!isValidAddOrSub(firstMatrix, secondMatrix)) {
            throw new IllegalArgumentException("Both matrices must be rectangular and of the same size");
        }
        return addMatrices(firstMatrix, secondMatrix);
    }

    /**
     * Checks that the two matrices have identical size and returns the result of the subtraction.
     *
     * @param firstMatrix  First matrix to be used in the calculation
     * @param secondMatrix Second matrix to be used in the calculation
     * @return Returns the result of the addition as a 2-dimensional double array
     */
    public static double[][] subtract(double[][] firstMatrix, double[][] secondMatrix) {
        if (!isValidAddOrSub(firstMatrix, secondMatrix)) {
            throw new IllegalArgumentException("Both matrices must be rectangular and of the same size");
        }
        return subtractMatrices(firstMatrix, secondMatrix);
    }

    /**
     * Checks that the matrix is rectangular and scales it by multiplying each value with the scalar.
     *
     * @param matrix Matrix to be scaled
     * @param scalar The value by which to scale the matrix
     * @return Returns the result of the multiplication as a 2-dimensional double array
     */
    public static double[][] scale(double[][] matrix, double scalar) {
        if (!isRectangular(matrix)) {
            throw new IllegalArgumentException("Matrix must be rectangular");
        }
        int rows = matrix.length;
        int columns = matrix[0].length;
        double[][] ret = new double[rows][columns];
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                ret[row][column] = matrix[row][column] * scalar;
            }
        }
        return ret;
    }


    /**
     * Adds the values of the two matrices together.
     *
     * @param firstMatrix First matrix to be added
     * @param secondMatrix Second matrix to be added
     *
     * @return Result of addition
     */
    private static double[][] addMatrices(double[][] firstMatrix, double[][] secondMatrix) {
        double[][] ret = new double[firstMatrix.length][firstMatrix[0].length];
        for (int row = 0; row < firstMatrix.length; row++) {
            for (int column = 0; column < firstMatrix[0].length; column++) {
                ret[row][column] = firstMatrix[row][column] + secondMatrix[row][column];
            }
        }
        return ret;
    }

    /**
     * Subtract the values of the second matrix from the first.
     *
     * @param firstMatrix Matrix to be subtracted from
     * @param secondMatrix Matrix to subtract with
     *
     * @return Result of subtraction
     */
    private static double[][] subtractMatrices(double[][] firstMatrix, double[][] secondMatrix) {
        double[][] ret = new double[firstMatrix.length][firstMatrix[0].length];
        for (int row = 0; row < firstMatrix.length; row++) {
            for (int column = 0; column < firstMatrix[0].length; column++) {
                ret[row][column] = firstMatrix[row][column] - secondMatrix[row][column];
            }
        }
        return ret;
    }

    /**
     * Checks that the two matrices are of identical size.
     *
     * @param first First matrix supplied
     * @param second Second matrix supplied
     * @return True if matrices are of identical size, else false
     */
    private static boolean isValidAddOrSub(double[][] first, double[][] second) {
        if (!isRectangular(first) || !isRectangular(second)) {
            return false;
        }
        if (first.length != second.length || first[0].length != second[0].length) {
            return false;
        }
        return true;
    }

    /**
     * Checks that the supplied matrix is rectangular.
     *
     * @param matrix Matrix to be checked
     * @return True if matrix is rectangular, else false
     */
    private static boolean isRectangular(double[][] matrix) {
        if (matrix.length == 0) {
            return false;
        }
        int rowLength = matrix[0].length;
        for (int row = 1; row < matrix.length; row++) {
            if (matrix[row].length != rowLength) {
                return false;
            }
        }
        return true;
    }


}