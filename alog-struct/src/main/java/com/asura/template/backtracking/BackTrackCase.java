package com.asura.template.backtracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author jamieLu
 * @create 2025-02-13
 */
public class BackTrackCase {
    private List<List<Integer>> res = new ArrayList<>();
    public void backtrack(int[] nums, LinkedList<Integer> result, boolean [] record) {
        if (result.size() == nums.length) {
            res.add(new LinkedList<>(result));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (record[i]) {
                continue;
            }
            // 处理选择
            result.addLast(i);
            record[i] = true;
            // 递归
            backtrack(nums, result, record);
            // 回溯
            result.removeLast();
            record[i] = false;
        }
    }

    private boolean end;
    /**
     * 37. 解数独
     */
    public void solveSudoku(char[][] board) {
        backtrack37(board, 0);
    }
    private void backtrack37(char[][] board, int index) {
        if (end) {
            return;
        }
        if (index == 81) {
            end = true;
            return;
        }
        int x = index / 9;
        int y = index % 9;
        if (board[x][y] != '.') {
            backtrack37(board, index + 1);
        } else {
            for (char i = '1'; i <= '9'; i++) {
                if (!isValid(board, x, y, i)) {
                    continue;
                }
                // 选择
                board[x][y] = i;
                backtrack37(board, index + 1);
                if (end) {
                    return;
                }
                // 撤销
                board[x][y] = '.';
            }
        }
    }
    private boolean isValid(char[][] board, int x, int y, char value) {
        for (int i = 0; i < board.length; i++) {
            if (board[x][i] == value) {
                return false;
            }
            if (board[i][y] == value) {
                return false;
            }
            if (board[3 * (x / 3) + i / 3][3 * (y / 3) + i % 3] == value) {
                return false;
            }
        }
        return true;
    }
    private List<List<String>> nQueensRes = new ArrayList<>();
    /**
     * 51. N 皇后
     */
    public List<List<String>> solveNQueens(int n) {
        List<String> board = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            board.add(".".repeat(n));
        }
        backtrack51(board,0);
        return nQueensRes;
    }

    private void backtrack51(List<String> board, int row) {
        if (board.size() == row) {
            nQueensRes.add(new ArrayList<>(board));
            return;
        }
        int len = board.get(row).length();
        for (int col = 0; col < len; col++) {
            // 检查和上方是否合法
            if (!validNQueens(board,row,col)) {
                continue;
            }
            // 选择
            char[] rowChars = board.get(row).toCharArray();
            rowChars[col] = 'Q';
            board.set(row, new String(rowChars));
            // 递归
            backtrack51(board, row + 1);
            // 取消选择
            rowChars[col] = '.';
            board.set(row, new String(rowChars));
        }
    }
    private boolean validNQueens(List<String> board, int row, int col) {
        // 检查上方列
        for (int i = 0; i < row; i++) {
            if (board.get(i).charAt(col) == 'Q') {
                return false;
            }
        }
       // 检查右斜线
        for (int i = row - 1, j = col + 1; i >= 0 && j < board.size(); i--, j++) {
            if (board.get(i).charAt(j) == 'Q') {
                return false;
            }
        }
        // 检查左斜线
        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
            if (board.get(i).charAt(j) == 'Q') {
                return false;
            }
        }
        return true;
    }
    private List<List<Integer>> res216 = new ArrayList<>();
    /**
     * 216. 组合总和 III
     */
    public List<List<Integer>> combinationSum3(int k, int n) {
        // 不重复 可复选
        backtrack216(k,n,1,new LinkedList<>());
        return res216;
    }
    private void backtrack216(int k, int n, int start, LinkedList<Integer> result) {
        if (result.size() == k && n == 0) {
            res216.add(new LinkedList<>(result));
            return;
        }
        for (int i = start; i <= 9; i++) {
            if (n - i < 0) {
                break;
            }
            // 选择
            result.addLast(i);
            // 递归
            backtrack216(k, n - i, i + 1, result);
            // 撤销
            result.removeLast();
        }
    }


    private List<List<Integer>> res78 = new ArrayList<>();
    /**
     * 78. 子集
     */
    public List<List<Integer>> subsets(int[] nums) {
        // 不重复 不可复选
        backtrack78(nums, 0, new LinkedList<>());
        return res78;

    }
    private void backtrack78(int[] nums, int start,LinkedList<Integer> track) {
        res78.add(new LinkedList<>(track));

        for (int i = start; i < nums.length; i++) {
            track.addLast(nums[i]);

            backtrack78(nums, i + 1, track);

            track.removeLast();

        }
    }
    private List<List<Integer>> res90 = new ArrayList<>();
    /**
     * 90. 子集 II
     */
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        // 可重复 不可复选 先排序
        Arrays.sort(nums);
        backtrack90(nums,0, new LinkedList<>());
        return res90;
    }

    private void backtrack90(int[] nums, int start, LinkedList<Integer> track) {

        res90.add(new LinkedList<>(track));

        for (int i = start; i < nums.length; i++) {
            if (i > start && nums[i] == nums[i - 1]) {
                continue;
            }
            track.addLast(nums[i]);

            backtrack90(nums, i + 1,track);

            track.removeLast();

        }
    }
}
