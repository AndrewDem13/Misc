import java.util.ArrayList;

/**
 Problem from: https://leetcode.com/problems/trapping-rain-water-ii/
 Given an m x n matrix of positive integers representing the height of each unit cell in a 2D elevation map,
 compute the volume of water it is able to trap after raining.
 Note:
 Both m and n are less than 110. The height of each unit cell is greater than 0 and is less than 20,000.
 Example:
 Given the following 3x6 height map:
 [
 [1,4,3,1,3,2],
 [3,2,1,3,2,4],
 [2,3,3,2,3,1]
 ]
 Return 4.
 */
public class TrappingWater3D {
    public static void main(String[] args) {
        int[] line1 = new int[]{5,5,5,1};
        int[] line3 = new int[]{5,1,5,5};
        int[] line2 = new int[]{5,1,1,5};
        int[] line4 = new int[]{5,2,5,8};
        int[][] map = new int[4][4];
        map[0] = line1;
        map[1] = line3;
        map[2] = line2;
        map[3] = line4;
        System.out.print(compute(map));
    }
    public static int compute(int[][] map) {
        if (map.length < 3 || map[0].length < 3)
            return 0;

        int result = 0;
        ArrayList<Integer> possible = new ArrayList<>();

        for (int y = 1; y < map.length-1; y++) {
            // searching for global left and right edge within which water can be trapped
            int leftEdgeIndex = 0, rightEdgeIndex = map[y].length - 1;
            while (leftEdgeIndex < rightEdgeIndex && map[y][leftEdgeIndex] <= map[y][leftEdgeIndex + 1])
                leftEdgeIndex++;
            while (rightEdgeIndex > leftEdgeIndex && map[y][rightEdgeIndex] <= map[y][rightEdgeIndex - 1])
                rightEdgeIndex--;

            // moving from edges to center counting possible water in 2D
            int leftNumber, rightNumber;
            while (leftEdgeIndex < rightEdgeIndex) {
                leftNumber = map[y][leftEdgeIndex];
                rightNumber = map[y][rightEdgeIndex];
                if (rightNumber >= leftNumber) {
                    while (leftEdgeIndex < rightEdgeIndex && leftNumber > map[y][++leftEdgeIndex]) {
                        possible.add(y);
                        possible.add(leftEdgeIndex);
                        possible.add(leftNumber-map[y][leftEdgeIndex]);
                    }
                } else {
                    while (leftEdgeIndex < rightEdgeIndex && rightNumber > map[y][--rightEdgeIndex]) {
                        possible.add(y);
                        possible.add(rightEdgeIndex);
                        possible.add(rightNumber-map[y][rightEdgeIndex]);
                    }
                }
            }
        }
        // searching for lowest border of pool only by verticals
        int lowBorder = Integer.MAX_VALUE;
        for (int i = 0; i < possible.size(); i++) {
            int currY = possible.get(i);
            int currX = possible.get(++i);
            int currZ = possible.get(++i);
            if (topIsBorder(possible, currY, currX, i) && map[currY-1][currX] >= map[currY][currX]) {
                if (map[currY-1][currX] < lowBorder)
                    lowBorder = map[currY - 1][currX];
            }
            if (bottomIsBorder(possible, currY, currX, i) && map[currY+1][currX] >= map[currY][currX]) {
                    if (map[currY+1][currX] < lowBorder)
                        lowBorder = map[currY + 1][currX];
            }
            // result += lowBorder - map[currY][currX];
            // map[currY][currX] = lowBorder;
        }

        System.out.println("lowBorder: " + lowBorder);
        // calculating water
        for (int i = 0; i < possible.size(); i+=3) {
            if (map[possible.get(i)][possible.get(i+1)] < lowBorder)
                result += lowBorder - map[possible.get(i)][possible.get(i+1)];
        }
        return result;
    }

    private static boolean bottomIsBorder(ArrayList<Integer> possible, int currY, int currX, int i) {
        for (i++; i < possible.size(); i+=3) {
            if (possible.get(i) == currY+1 && possible.get(i+1) == currX)
                return false;
        }
        return true;
    }

    private static boolean topIsBorder(ArrayList<Integer> possible, int currY, int currX, int i) {
        for (int q = 0; q < i-2; q+=3) {
            if (possible.get(q) == currY-1 && possible.get(q+1) == currX)
                return false;
        }
        return true;
    }
}
