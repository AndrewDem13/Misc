/**
 Problem from: https://leetcode.com/problems/trapping-rain-water/
 Given n non-negative integers representing an elevation map where the width of each bar is 1,
 compute how much water it is able to trap after raining.
 For example,
 Given [0,1,0,2,1,0,1,3,2,1,2,1], return 6.
 */

// My second try of solution. Works well, requires less space and code looks more compact

public class TrappingWater2 {
    public static int compute(int[] array) {
        if (array.length < 3)
            return 0;

        int result = 0;

        // searching for global left and right edge within which water can be trapped
        int leftEdgeIndex = 0, rightEdgeIndex = array.length-1;
        while (leftEdgeIndex < rightEdgeIndex && array[leftEdgeIndex] <= array[leftEdgeIndex +1])
            leftEdgeIndex++;
        while (rightEdgeIndex > leftEdgeIndex && array[rightEdgeIndex] <= array[rightEdgeIndex -1])
            rightEdgeIndex--;

        // moving from edges to center counting water
        int leftNumber, rightNumber;
        while (leftEdgeIndex < rightEdgeIndex) {
            leftNumber = array[leftEdgeIndex];
            rightNumber = array[rightEdgeIndex];
            if (rightNumber >= leftNumber) { // current edges are the same, or left is lower (max height of water there)
                while (leftEdgeIndex < rightEdgeIndex && leftNumber >= array[++leftEdgeIndex])
                    result += leftNumber - array[leftEdgeIndex];
            }
            else { // current right edge is lover
                while (leftEdgeIndex < rightEdgeIndex && rightNumber >= array[--rightEdgeIndex])
                    result += rightNumber - array[rightEdgeIndex];
            }
        }
        return result;
    }
}
