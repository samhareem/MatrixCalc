package MatrixCalc;

/**
 * Contains methods for calculating matrix products. The class currently supports addition, subtraction,
 * scaling, multiplication, and determinant calculations. The matrices are required to be square or rectangular
 * (depending on calculation) 2-dimensional arrays of doubles.
 */
public final class MatrixCalc {
    /**
     * Matrices where the longest side is less than the cutoff value will be multiplied using the naive method. For larger
     * matrices, the Strassen method will be used. The value must be at least 3.
     */
    private static int strassenCutoff = 257;

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
     * Checks that the matrices are rectangular and that the row count of firstMatrix equals the column count of secondMatrix. If the
     * matrices are valid, the longest side of the two matrices is determined. If the longest side is less than the strassenCutoff
     * variable, the matrices are multiplied using the naive method. For larger matrices, the Strassen method is used.
     *
     * @param firstMatrix First matrix used in the multiplication
     * @param secondMatrix Second matrix used in the multiplication
     * @return Returns the result of the multiplication as a 2-dimensional double array
     */
    public static double[][] multiply(double[][] firstMatrix, double[][] secondMatrix) {
        if (!isValidMultiplication(firstMatrix, secondMatrix)) {
            throw new IllegalArgumentException("Both matrices must be rectangular, and the row length of firstMatrix must equal the column length of secondMatrix");
        }
        int longestSide = determineLongestSide(firstMatrix, secondMatrix);
        if (longestSide < strassenCutoff) {
            return multiplyNaive(firstMatrix, secondMatrix);
        } else {
            return strassenWrapper(firstMatrix, secondMatrix, longestSide);
        }
    }

    /**
     * Checks that the matrices are rectangular and that the row count of firstMatrix equals the column count of secondMatrix. If the
     * matrices are valid, the longest side of the two matrices is determined. If the longest side is less than 1024
     * values long, the matrices are multiplied using the naive method. For larger matrices, the Strassen method is used.
     *
     * @param matrix The matrix whose determinant is to be determined
     * @return The determinant of the given matrix
     */
    public static double determinant(double[][] matrix) {
        if (!isSquare(matrix)) {
            throw new IllegalArgumentException("Matrix must be square");
        }
        int matrixSize = matrix.length;
        double[][] matrixLU = new double[matrixSize][matrixSize];
        for (int row = 0; row < matrixSize; row++) {
            copyRow(matrix[row], 0, matrixLU[row], 0, matrixSize);
        }
        // Base cases for matrices with length < 4
        if (matrixSize == 1) {
            return matrixLU[0][0];
        } else if (matrixSize == 2) {
            return matrixLU[0][0] * matrixLU[1][1] - matrixLU[0][1] * matrixLU[1][0];
        } else if (matrixSize == 3) {
            return matrixLU[0][0] * matrixLU[1][1] * matrixLU[2][2] + matrixLU[0][1] * matrixLU[1][2] * matrixLU[2][0]
                    + matrixLU[0][2] * matrixLU[1][0] * matrixLU[2][1] - matrixLU[0][2] * matrixLU[1][1] * matrixLU[2][0] -
                    matrixLU[0][1] * matrixLU[1][0] * matrixLU[2][2] - matrixLU[0][0] * matrixLU[1][2] * matrixLU[2][1];
        } else {
            // LU Factorization using Doolittle method for matrices with length >= 4.
            int determinantSign = 1;
            // Main loop
            for (int i = 0; i < matrixSize; i++) {
                // Partial pivoting is used to minimize the chance of division by zero. CURRENTLY NOT WORKING
                int pivotRow = i;
                for (int row = i + 1; row < matrixSize; row++) {
                    if (matrixLU[row][i] > matrixLU[i][i]) {
                        pivotRow = row;
                    }
                }
                //Swap rows if necessary
                if (pivotRow != i) {
                    for (int column = 0; column < matrixSize; column++) {
                        double temp = matrixLU[pivotRow][column];
                        matrixLU[pivotRow][column] = matrixLU[i][column];
                        matrixLU[i][column] = temp;
                    }
                    // In the case of a row swap, the sign of the determinant changes. The variable determinantSign
                    // is used to store the sign of the given matrix' determinant.
                    determinantSign *= -1;
                }

                // Determine i:th row
                for (int j = i; j < matrixSize; j++) {
                    for (int k = 0; k <= i - 1; k++) {
                        matrixLU[i][j] = matrixLU[i][j] - matrixLU[i][k] * matrixLU[k][j];
                    }
                }
                // Determine i:th column
                for (int j = i + 1; j < matrixSize; j++) {
                    for (int k = 0; k <= i - 1; k++) {
                        matrixLU[j][i] = matrixLU[j][i] - matrixLU[j][k] * matrixLU[k][i];
                    }
                    matrixLU[j][i] = matrixLU[j][i] / matrixLU[i][i];
                    // If division by zero is undertaken and a cell of the array becomes NaN, the matrix is singular and
                    // its determinant is 0
                    if (Double.isNaN(matrixLU[j][i])) {
                        return Double.NaN;
                    }
                }
            }
            // Calculate and return determinant
            double determinant = matrixLU[0][0];
            for (int i = 1; i < matrixSize; i++) {
                determinant *= matrixLU[i][i];
            }
            return determinant * determinantSign;
        }
    }

    /**
     *Checks that the given matrix is square and calculates it's inverse matrix. Note that the method does not check
     * whether or not the give matrix is invertible, so the result matrix may consist of NaN values.
     *
     * @param matrix The matrix to be inverted
     * @result The inverse of the given matrix
     */
    public static double[][] invert(double[][] matrix) {
        if (!isSquare(matrix)) {
            throw new IllegalArgumentException("Matrix must be square");
        }
        double[][] ret = new double[matrix.length][matrix.length];
        if (matrix.length == 1) {
            ret[0][0] = 1 / matrix[0][0];
        } else {
            ret = strassenInvert(matrix);
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
     * Multiplies the given matrices using the naive method.
     *
     * @param firstMatrix First matrix used in the multiplication
     * @param secondMatrix Second matrix used in the multiplication
     * @return The result of the multiplication
     */
    private static double[][] multiplyNaive(double[][] firstMatrix, double[][] secondMatrix) {
        double[][] ret = new double[firstMatrix.length][secondMatrix[0].length];
        for (int row = 0; row < ret.length; row++) {
            for (int column = 0; column < ret[0].length; column++) {
                double result = 0;
                int rowA = 0;
                while (rowA < firstMatrix[0].length) {
                    for (int columnB = 0; columnB < secondMatrix.length; columnB++) {
                        result += (firstMatrix[row][rowA] * secondMatrix[columnB][column]);
                        rowA++;
                    }
                }
                ret[row][column] = result;
            }
        }
        return ret;
    }

    /**
     * Prepares the given matrices for the Strassen method of multiplication and trims the result back to original length
     *
     * @param firstMatrix First matrix used in the multiplication
     * @param secondMatrix Second matrix used in the multiplication
     * @param longestSide The longer side of the matrices
     *
     * @return Result of multiplication as new array
     */
    private static double[][] strassenWrapper(double[][] firstMatrix, double[][] secondMatrix, int longestSide) {
        // Record the row and column count of the result matrix
        int originalRows = firstMatrix.length;
        int originalColumns = secondMatrix[0].length;
        // Calculates the next power of two that is equal or larger than the length of the longest side
        int calcSize = closestPowerOfTwo(longestSide);
        // If necessary, increases the size of the matrices
        if (firstMatrix.length != calcSize || firstMatrix[0].length != calcSize) {
            firstMatrix = increaseMatrixSize(firstMatrix, calcSize);
        }
        if (secondMatrix.length != calcSize || secondMatrix[0].length != calcSize) {
            secondMatrix = increaseMatrixSize(secondMatrix, calcSize);
        }
        // Recursively calculate the result of the multiplication using the Strassen method
        double[][] strassenResult = multiplyStrassen(firstMatrix, secondMatrix);
        // If necessary, trim resulting matrix to original size and return
        if (calcSize == originalRows && calcSize == originalColumns) {
            return strassenResult;
        } else {
            double[][] ret = new double[originalRows][originalColumns];
            for (int row = 0; row < originalRows; row++) {
                copyRow(strassenResult[row], 0, ret[row], 0, originalColumns);
            }
            return ret;
        }
    }

    /**
     * The main recursive method used to multiply the two matrices using the Strassen method.
     *
     * @param firstMatrix First matrix to be multiplied
     * @param secondMatrix Second matrix to be multiplied
     *
     * @return The result of the multiplication as a new array, not trimmed to original size
     */
    private static double[][] multiplyStrassen(double[][] firstMatrix, double[][] secondMatrix) {
        int matrixSize = firstMatrix.length;
        int halfpoint = matrixSize / 2;

        // Initialize 8 submatrices used in calculation
        double[][] a11 = new double[halfpoint][halfpoint];
        double[][] a12 = new double[halfpoint][halfpoint];
        double[][] a21 = new double[halfpoint][halfpoint];
        double[][] a22 = new double[halfpoint][halfpoint];
        double[][] b11 = new double[halfpoint][halfpoint];
        double[][] b12 = new double[halfpoint][halfpoint];
        double[][] b21 = new double[halfpoint][halfpoint];
        double[][] b22 = new double[halfpoint][halfpoint];

        // Divide the matrices being multiplied into the 8 submatrices
        for (int row = 0; row < halfpoint; row++) {
            copyRow(firstMatrix[row], 0, a11[row], 0, halfpoint);
            copyRow(firstMatrix[row], halfpoint, a12[row], 0, halfpoint);
            copyRow(firstMatrix[row + halfpoint], 0, a21[row], 0, halfpoint);
            copyRow(firstMatrix[row + halfpoint], halfpoint, a22[row], 0, halfpoint);
            copyRow(secondMatrix[row], 0, b11[row], 0, halfpoint);
            copyRow(secondMatrix[row], halfpoint, b12[row], 0, halfpoint);
            copyRow(secondMatrix[row + halfpoint], 0, b21[row], 0, halfpoint);
            copyRow(secondMatrix[row + halfpoint], halfpoint, b22[row], 0, halfpoint);
        }

        // Initialize 7 helper matrices
        double[][] m1;
        double[][] m2;
        double[][] m3;
        double[][] m4;
        double[][] m5;
        double[][] m6;
        double[][] m7;

        // if current matrix is less than strassenCutoff, calculate the helper matrices using naive multiplication,
        // else call the Strassen method recursively
        if (matrixSize < strassenCutoff) {
            m1 = multiplyNaive(addMatrices(a11, a22), addMatrices(b11, b22));
            m2 = multiplyNaive(addMatrices(a21, a22), b11);
            m3 = multiplyNaive(a11, subtractMatrices(b12, b22));
            m4 = multiplyNaive(a22, subtractMatrices(b21, b11));
            m5 = multiplyNaive(addMatrices(a11, a12), b22);
            m6 = multiplyNaive(subtractMatrices(a21, a11), addMatrices(b11, b12));
            m7 = multiplyNaive(subtractMatrices(a12, a22), addMatrices(b21, b22));
        } else {
            m1 = multiplyStrassen(addMatrices(a11, a22), addMatrices(b11, b22));
            m2 = multiplyStrassen(addMatrices(a21, a22), b11);
            m3 = multiplyStrassen(a11, subtractMatrices(b12, b22));
            m4 = multiplyStrassen(a22, subtractMatrices(b21, b11));
            m5 = multiplyStrassen(addMatrices(a11, a12), b22);
            m6 = multiplyStrassen(subtractMatrices(a21, a11), addMatrices(b11, b12));
            m7 = multiplyStrassen(subtractMatrices(a12, a22), addMatrices(b21, b22));
        }

        // Calculate the 4 quarters of the result matrix using the helper matrices
        double[][] c11 = addMatrices(m7, subtractMatrices(addMatrices(m1, m4), m5));
        double[][] c12 = addMatrices(m3, m5);
        double[][] c21 = addMatrices(m2, m4);
        double[][] c22 = addMatrices(m6, addMatrices(m3, subtractMatrices(m1, m2)));

        // Combine the resulting quarters into one matrix, and return
        double[][] ret = new double[matrixSize][matrixSize];
        for (int row = 0; row < halfpoint; row++) {
            copyRow(c11[row], 0, ret[row], 0, halfpoint);
            copyRow(c12[row], 0, ret[row], halfpoint, halfpoint);
            copyRow(c21[row], 0, ret[row + halfpoint], 0, halfpoint);
            copyRow(c22[row], 0, ret[row + halfpoint], halfpoint, halfpoint);
        }
        return ret;
    }

    /**
     * Inverts the given matrix using blockwise invertion and the Strassen method for matrix multiplication. Switches to
     * the naive inversion method for matrices smaller than the 2x2 values.
     *
     * @param matrix Matrix to be inverted
     * @return Result of inversion
     */
    /**
     * Inverts the given matrix using blockwise invertion and the Strassen method for matrix multiplication. Switches to
     * the naive inversion method for matrices smaller than the 2x2 values.
     *
     * @param matrix Matrix to be inverted
     * @return Result of inversion
     */
    private static double[][] strassenInvert(double[][] matrix) {
        int matrixSize = matrix.length;

        // If current matrix size is 2, calculate the inverse of the matrix using blockwise inversion naively, else call
        // the Strassen method recursively on the top left quarter and calculate the other quarters using the result
        if (matrixSize <= 2) {
            return naiveInvert(matrix);
        }

        int halfpoint = matrixSize / 2;

        // Initialize 4 submatrices used in calculation
        double[][] a11 = new double[matrixSize-halfpoint][matrixSize-halfpoint];
        double[][] a12 = new double[matrixSize-halfpoint][halfpoint];
        double[][] a21 = new double[halfpoint][matrixSize-halfpoint];
        double[][] a22 = new double[halfpoint][halfpoint];



        // Divide the matrix into the 4 submatrices
        for (int row = 0; row < matrixSize - halfpoint; row++) {
            copyRow(matrix[row], 0, a11[row], 0, matrixSize-halfpoint);
            copyRow(matrix[row], matrixSize-halfpoint, a12[row], 0, halfpoint);
        }
        for (int row = matrixSize-halfpoint; row < matrixSize; row++) {
            copyRow(matrix[row], 0, a21[row - (matrixSize-halfpoint)], 0, matrixSize-halfpoint);
            copyRow(matrix[row], matrixSize-halfpoint, a22[row - (matrixSize-halfpoint)], 0, halfpoint);
        }

        a22 = strassenInvert(a22);

        // Calculate the 4 quarters of the result matrix using blockwise invertion
        double[][] c11 = invert(subtract(a11, multiply(multiply(a12, a22), a21)));
        double[][] c22 = add(a22, multiply(multiply(multiply(multiply(a22, a21), c11), a12), a22));
        double[][] c12 = multiply(multiply(scale(c11, -1), a12), a22);
        double[][] c21 = multiply(multiply(scale(a22, -1), a21), c11);

        // Combine the resulting quarters into one matrix, and return
        double[][] ret = new double[matrixSize][matrixSize];
        for (int row = 0; row < matrixSize - halfpoint; row++) {
            copyRow(c11[row], 0, ret[row], 0, matrixSize-halfpoint);
            copyRow(c12[row], 0, ret[row], matrixSize-halfpoint, halfpoint);
        }
        for (int row = matrixSize-halfpoint; row < matrixSize; row++) {
            copyRow(c21[row - (matrixSize-halfpoint)], 0, ret[row], 0, matrixSize-halfpoint);
            copyRow(c22[row - (matrixSize-halfpoint)], 0, ret[row], matrixSize-halfpoint, halfpoint);
        }
        return ret;
    }

    /**
     * Naive matrix inversion method for 2x2 and smaller matrices
     *
     * @param matrix Matrix to be inverted
     * @return Result of inversion
     */
    private static double[][] naiveInvert(double[][] matrix) {
        if (matrix.length == 1) {
            matrix[0][0] = 1/matrix[0][0];
            return matrix;
        } else {
            double scalar = 1 / (matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0]);
            matrix[0][1] *= -1;
            matrix[1][0] *= -1;
            double temp = matrix[0][0];
            matrix[0][0] = matrix[1][1];
            matrix[1][1] = temp;
            return scale(matrix, scalar);
        }
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
     * Checks that the matrices are rectangulare and that the row count of the first matrix equals the column count
     * of the second matrix.
     *
     * @param first First matrix to be checked
     * @param second Second matrix to be checked
     * @return True if valid, else false
     */
    private static boolean isValidMultiplication(double[][] first, double[][] second) {
        if (!isRectangular(first) || !isRectangular(second)) {
            return false;
        }
        return first[0].length == second.length;
    }

    /**
     * Checks that the given matrix is square.
     *
     * @param matrix Matrix to be checked
     * @return True if square, else false
     */
    private static boolean isSquare(double[][] matrix) {
        if (!isRectangular(matrix)) {
            return false;
        }
        return matrix.length == matrix[0].length;
    }

    /**
     * Checks that the supplied matrix is rectangular.
     *
     * @param matrix Matrix to be checked
     * @return True if matrix is rectangular, else false
     */
    private static boolean isRectangular(double[][] matrix) {
        if (matrix.length <= 0) {
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

    /**
     * Returns the next power of two that is larger or equal to given value.
     *
     * @param value Comparison value
     * @return Next power of two
     */
    private static int closestPowerOfTwo(int value) {
        int closest = 2;
        while (closest < value) {
            closest *= 2;
        }
        return closest;
    }

    /**
     * Determines the longest side present in the two matrices.
     *
     * @param firstMatrix First matrix to be checked
     * @param secondMatrix Second matrix to be checked
     * @return The length of the longest side
     */
    private static int determineLongestSide(double[][] firstMatrix, double[][] secondMatrix) {
        if (firstMatrix[0].length > secondMatrix[0].length) {
            if (firstMatrix.length < firstMatrix[0].length) {
                return firstMatrix[0].length;
            }
        } else {
            if (firstMatrix.length < secondMatrix[0].length) {
                return secondMatrix[0].length;
            }
        }
        return firstMatrix.length;
    }

    /**
     * Creates a new matrix of newSize length, and copies the contents of the given matrix into it
     *
     * @param matrix Matrix to copy
     * @param newSize Size of new matrix
     *
     * @return Matrix of increased size with values of given matrix
     */
    private static double[][] increaseMatrixSize(double[][] matrix, int newSize) {
        double[][] ret = new double[newSize][newSize];
        for (int row = 0; row < matrix.length; row++) {
            copyRow(matrix[row], 0, ret[row], 0, matrix[0].length);
        }
        return ret;
    }

    /**
     * Copies a row or part of the row from the source matrix to the target matrix.
     *
     * @param source Matrix to be copied from
     * @param startPositionSource The position to copy from
     * @param target Matrix to be copy to
     * @param startPositionTarget The first position to copy to
     * @param length The number of values to copy
     */
    private static void copyRow(double[] source, int startPositionSource, double[] target, int startPositionTarget, int length) {
        int targetIndex = startPositionTarget;
        int end = startPositionSource + length;
        for (int sourceIndex = startPositionSource; sourceIndex < end; sourceIndex++) {
            target[targetIndex] = source[sourceIndex];
            targetIndex++;
        }
    }

    public static void setStrassenCutoff(int newCutoff) {
        strassenCutoff = newCutoff >= 3 ? newCutoff : strassenCutoff;
    }

    public static int getStrassenCutoff() {
        return strassenCutoff;
    }
}


