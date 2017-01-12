/**
Problem from: https://leetcode.com/problems/trapping-rain-water/
Given n non-negative integers representing an elevation map where the width of each bar is 1,
compute how much water it is able to trap after raining.
For example,
Given [0,1,0,2,1,0,1,3,2,1,2,1], return 6.
 */

public class TrappingWater {
    public static void main(String[] args) {
        int[] x = {0,1,0,2,1,0,1,3,2,1,2,1};
        compute(x);
    }

    public static int compute(int[] array) {
        int result = 0;
        int[][] matrix = getMatrix(array);

        for (int y = 0; y < matrix.length; y++) {
            for (int x = 1; x < matrix[y].length-1; x++) { // ignoring beginning and end
                if (matrix[y][x] == 0) {
                    if (lookRight(matrix[y], x)) { // to not iterate to the end of the line
                        if (lookLeft(matrix[y], x))
                            result++;
                    }
                    else
                        break;
                }
            }
        }

        System.out.print("Result is: " + result);
        /*  In matrix such this expected result is 6
            0 0 0 0 0 0 0 3 0 0 0 0
            0 0 0 2 0 0 0 3 2 0 2 0
            0 1 0 2 1 0 1 3 2 1 2 1
        */

        return result;
    }

    private static int[][] getMatrix(int[] array) {
        int top = 0;
        for (int i : array)
            if (i > top)
                top = i;

        int[][] matrix = new int[top][array.length];
        for (int y = 0; y < top; y++) {
            for (int x = 0; x < array.length; x++) {
                if (array[x] >= top-y)
                    matrix[y][x] = array[x];
                else
                    matrix[y][x] = 0;
            }
        }
        return matrix;
    }

    private static boolean lookRight(int[] array, int x) {
        for (int i = x+1; i < array.length; i++) {
            if (array[i] > array[x])
                return true;
        }
        return false;
    }

    private static boolean lookLeft(int[] array, int x) {
        for (int i = x-1; i >= 0; i--) {
            if (array[i] > array[x])
                return true;
        }
        return false;
    }
}
