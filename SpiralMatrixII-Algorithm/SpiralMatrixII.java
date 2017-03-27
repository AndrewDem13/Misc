import java.util.Arrays;

/**
 Problem from: leetcode.com/problems/spiral-matrix-ii/
 Given an integer n, generate a square matrix filled with elements from 1 to n2 in spiral order.

 For example,
 Given n = 3,

 You should return the following matrix:
 [
 [ 1, 2, 3 ],
 [ 8, 9, 4 ],
 [ 7, 6, 5 ]
 ]
 */

public class SpiralMatrixII {
    public static void main(String args[]) {
        long st = System.currentTimeMillis();
        int[][] result = generateMatrix(15);
        long delta = System.currentTimeMillis() - st;
        System.out.println("Time spent: " + delta + "ms");
        for (int[] i : result) {
            System.out.println(Arrays.toString(i));
        }
    }

    public static int[][] generateMatrix(int n) {
        int[][] result = new int[n][n];
        int firstRow = 0, lastRow = n-1, firstInt = 0, lastInt = n-1;
        int current = 0;

        while (current < n*n) {
            for (int i = firstInt; i <= lastInt; i++)
                result[firstRow][i] = ++current;
            firstRow++;

            for (int i = firstRow; i <= lastRow; i++)
                result[i][lastInt] = ++current;
            lastInt--;

            for (int i = lastInt; i >= firstInt; i--)
                result[lastRow][i] = ++current;
            lastRow--;

            for (int i = lastRow; i >= firstRow; i--)
                result[i][firstInt] = ++current;
            firstInt++;
        }

        return result;
    }
}
