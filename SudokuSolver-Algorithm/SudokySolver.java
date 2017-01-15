/**
 Problem from: https://leetcode.com/problems/sudoku-solver/
 Write a program to solve a Sudoku puzzle by filling the empty cells.
 Empty cells are indicated by the character '.'.
 You may assume that there will be only one unique solution.
 */

public class SudokySolver {
    public void solveSudoku(char[][] board) {
        if(board == null || board.length == 0)
            return;
        solve(board);
    }

    private static boolean solve(char[][] board) {
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[y].length; x++) {
                if (board[y][x] == '.') {
                    for (char c = '1'; c <= '9'; c++) {
                        if (isValid(c, y, x, board)) {
                            board[y][x] = c;
                            if (solve(board))
                                return true;
                            else
                                board[y][x] = '.';
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isValid(char c, int y, int x, char[][] board) {
        for (int i = 0; i < 9; i++) {
            if (board[y][i] == c || board[i][x] == c || board[y/3*3+i/3][x/3*3+i%3] == c)
                return false;
        }
        return true;
    }
}
