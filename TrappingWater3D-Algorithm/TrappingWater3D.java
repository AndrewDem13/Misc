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
        int[] line1 = new int[]{12,13,1,12};
        int[] line2 = new int[]{13,4,13,12};
        int[] line3 = new int[]{13,8,10,12};
        int[] line4 = new int[]{12,13,12,12};
        int[] line5 = new int[]{13,13,13,13};
        int[][] map = new int[5][4];
        map[0] = line1;
        map[1] = line2;
        map[2] = line3;
        map[3] = line4;
        map[4] = line5;
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
                    }
                } else {
                    while (leftEdgeIndex < rightEdgeIndex && rightNumber > map[y][--rightEdgeIndex]) {
                        possible.add(y);
                        possible.add(rightEdgeIndex);
                    }
                }
            }
        }
        // searching for lowest border of pool only by verticals
        int lowBorder = Integer.MAX_VALUE;
        for (int i = 0; i < possible.size(); i++) {
            int currY = possible.get(i);
            int currX = possible.get(++i);
            if (topIsBorder(possible, currY, currX, i)) {
                if (map[currY-1][currX] < lowBorder)
                    lowBorder = map[currY - 1][currX];
            }
            if (bottomIsBorder(possible, currY, currX, i)) {
                    if (map[currY+1][currX] < lowBorder)
                        lowBorder = map[currY + 1][currX];
            }
            if (leftIsBorder(possible, currY, currX, i)) {
                if (map[currY][currX-1] < lowBorder)
                    lowBorder = map[currY][currX-1];
            }
            if (rightIsBorder(possible, currY, currX, i)) {
                if (map[currY][currX+1] < lowBorder)
                    lowBorder = map[currY][currX+1];
            }
            // result += lowBorder - map[currY][currX];
            // map[currY][currX] = lowBorder;
        }

        System.out.println("lowBorder: " + lowBorder);
        // calculating water
        int futureArraySize = 0;
        ArrayList<int[]> list = new ArrayList<>();
        for (int i = 0; i < possible.size(); i+=2) {
            futureArraySize++;
            if (map[possible.get(i)][possible.get(i+1)] < lowBorder) {
                result += lowBorder - map[possible.get(i)][possible.get(i + 1)];
                if (possible.get(i) < futureArraySize)
                    futureArraySize--;
                if (list.contains(map[possible.get(i)]))
                    list.remove(map[possible.get(i)]);
            }
            else
                list.add(map[possible.get(i)]);
        }
        System.out.println("futureArraySize " + futureArraySize);

        if (futureArraySize > 2) {
            int[][] array = new int[futureArraySize][map[0].length];
            array[0] = map[0];
            array[futureArraySize - 1] = map[map.length - 1];
            for (int i = 1; i < futureArraySize - 1; i++) {
                for (int[] a : list)
                    array[i] = a;
            }
            result += compute(array);
        }

        return result;
    }

    private static boolean bottomIsBorder(ArrayList<Integer> possible, int currY, int currX, int i) {
        for (int q = 0; q < possible.size(); q+=2) {
            if (possible.get(q) == currY+1 && possible.get(q+1) == currX)
                return false;
        }
        return true;
    }

    private static boolean topIsBorder(ArrayList<Integer> possible, int currY, int currX, int i) {
        for (int q = 0; q < possible.size(); q+=2) {
            if (possible.get(q) == currY-1 && possible.get(q+1) == currX)
                return false;
        }
        return true;
    }

    private static boolean leftIsBorder(ArrayList<Integer> possible, int currY, int currX, int i) {
        for (int q = 0; q < possible.size(); q+=2) {
            if (possible.get(q) == currY && possible.get(q+1) == currX-1)
                return false;
        }
        return true;
    }

    private static boolean rightIsBorder(ArrayList<Integer> possible, int currY, int currX, int i) {
        for (int q = 0; q < possible.size(); q+=2) {
            if (possible.get(q) == currY && possible.get(q+1) == currX+1)
                return false;
        }
        return true;
    }
}
