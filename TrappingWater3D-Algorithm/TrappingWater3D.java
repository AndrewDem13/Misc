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

/**
 * То все херня собача. знач дивимся на матрицю і
 * - берем по черзі кожне число окрім країв:
 * -- перевіряєм його в 4 напрямках (спочатку краї) і повертаєм найменшого сусіда
 * --- якщо ні але сусід є краєм, то обриваємся й берем наступне число
 * --- якщо ні і сусід не край, то беремо сусіда (координати) і шукаєм його край окрім попереднього числа(РЕКУРСІЯ до --)
 * --- інакше сусіди всі більші, рахуєм воду і йдем далі
 */
public class TrappingWater3D {
    public static void main(String[] args) {
        [[1,4,3,1,3,2],[3,2,1,3,2,4],[2,3,3,2,3,1]]
        int[] line1 = new int[]{78,30,94,36};
        int[] line2 = new int[]{87,93,50,22}; //  impos
        int[] line3 = new int[]{63,27,91,60}; // 28 - 63(37)     // 9
        int[] line4 = new int[]{64,28,41,27}; // 27 - 41(37)     // + 10 = 19
        int[] line5 = new int[]{73,37,12,69}; // 37 (impos bc of 30), 12 - 41 (37 actually) // +25 = 44!!!
        int[] line6 = new int[]{68,30,83,31}; // 30 - 24 // impos
        int[] line7 = new int[]{63,24,68,36};
        int[][] map = new int[7][4];
        map[0] = line1;
        map[1] = line2;
        map[2] = line3;
        map[3] = line4;
        map[4] = line5;
        map[5] = line6;
        map[6] = line7;

        System.out.print(compute(map));
    }

    public static int compute(int[][] map) {
        if (map == null && map.length < 3 || map[0].length < 3)
            return 0;

        int result = 0;
        // copy int map to working Cell map
        Cell[][] cellMap = new Cell[map.length][map[0].length];
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                boolean isBorder = false;
                if (y == 0 || y == map.length-1 || x == 0 || x == map[y].length-1)
                    isBorder = true;
                cellMap[y][x] = new Cell(y, x, map[y][x], isBorder);
            }
        }

        int currValue;
        for (int y = 1; y < cellMap.length-1; y++) {
            for (int x = 1; x < cellMap[y].length-1; x++) {
                if (!cellMap[y][x].isVisited) {
                    currValue = cellMap[y][x].value;
                    result += check(currValue, y, x, cellMap);
                    cellMap[y][x].isVisited = true;
                }
            }
        }
        return result;
    }

    private static int check(int currValue, int y, int x, Cell[][] cellMap) {
        int low = Integer.MAX_VALUE;
        int result = 0;
        // checking top
        if (!cellMap[y-1][x].isVisited) {
            if (cellMap[y-1][x].value <= currValue) {
                if (cellMap[y-1][x].isBorder)
                    low = -1;
                else if (cellMap[y-1][x].value < low)
                    low = cellMap[y-1][x].value;
            }
        }

        // checking left
        if (!cellMap[y][x-1].isVisited && low > -1) {
            if (cellMap[y][x-1].value <= currValue) {
                if (cellMap[y][x-1].isBorder)
                    low = -1;
                else if (cellMap[y][x-1].value < low)
                    low = cellMap[y][x-1].value;
            }
        }

        // checking right
        if (!cellMap[y][x+1].isVisited && low > -1) {
            if (cellMap[y][x+1].value <= currValue) {
                if (cellMap[y][x+1].isBorder)
                    low = -1;
                else if (cellMap[y][x+1].value < low)
                    low = cellMap[y][x+1].value;
            }
        }

        // checking bottom
        if (!cellMap[y+1][x].isVisited && low > -1) {
            if (cellMap[y+1][x].value <= currValue) {
                if (cellMap[y+1][x].isBorder)
                    low = -1;
                else if (cellMap[y+1][x].value < low)
                    low = cellMap[y+1][x].value;
            }
        }


        if (low < 0 || low == currValue) // near border is lower
            return result; // yet empty

        else if (low == Integer.MAX_VALUE) { // all around is higher
            result += findLowestNeighbour(y, x, cellMap) - currValue;
            cellMap[y][x].value += result;
            if (y > 1)
                result += check(cellMap[y-1][x].value, y-1, x, cellMap);
            if (x > 1)
                result += check(cellMap[y][x-1].value, y, x-1, cellMap);
            //cellMap[y][x].isVisited = true;
        }
        else {
            boolean isRight = checkIsRight(low, y, x, cellMap);// right is lower, otherwise bottom
            if (isRight) {
                int add = check(low, y, x + 1, cellMap);
                result += add;
                cellMap[y][x+1].value += add;
                cellMap[y][x+1].isVisited = true;
            }
            else {
                int add = check(low, y+1, x, cellMap);
                result += add;
                cellMap[y+1][x].value += add;
                cellMap[y+1][x].isVisited = true;
            }
        }
        return result;
    }

    private static int findLowestNeighbour(int y, int x, Cell[][]cellMap) {
        int lowest = Integer.MAX_VALUE;
        if (!cellMap[y-1][x].isVisited)
            lowest = cellMap[y-1][x].value;
        if (!cellMap[y+1][x].isVisited && cellMap[y+1][x].value < lowest)
            lowest = cellMap[y+1][x].value;
        if (!cellMap[y][x-1].isVisited && cellMap[y][x-1].value < lowest)
            lowest = cellMap[y][x-1].value;
        if (!cellMap[y][x+1].isVisited && cellMap[y][x+1].value < lowest)
            lowest = cellMap[y][x+1].value;
        return lowest;
    }

    private static boolean checkIsRight(int checkResult, int y, int x, Cell[][]cellMap) {
        if (cellMap[y][x+1].value == checkResult)
            return true;
        else
            return false;
    }

    private static class Cell {
        private int x, y, value;
        private boolean isBorder, isVisited;

        public Cell (int y, int x, int value, boolean isBorder) {
            this.y = y;
            this.x = x;
            this.value = value;
            this.isBorder = isBorder;
            isVisited = false;
        }
    }
}
