package com.asura.template.search;

import com.asura.structure.tree.TreeNode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * @author jamieLu
 * @create 2025-02-11
 */
public class DFSCase {
    public void traverse(TreeNode root) {
        if (root == null) return;
        // 前序
        traverse(root.left);
        // 中序
        traverse(root.right);
        // 后序
    }
    private boolean[][] dp;
    /**
     * 130 矩阵中找出所有被包围的0
     */
    public void solve(char[][] board) {
        // 从外圈往里遍历 相邻的需要被替换
        // 最后把替换的替换回来，每被替换的转为x

        if (board == null || board.length == 0) {
            return;
        }
        dp = new boolean[board.length][board[0].length];
        // 从外往里搜 外面的o需要被替换
        // 与替换元素相连的也需要被替换
        for (int i = 0; i < board.length; i++) {
            dfs(board, i, 0);
            dfs(board, i, board[0].length - 1);
        }
        for (int i = 1; i < board[0].length; i++) {
            dfs(board, 0, i);
            dfs(board, board.length - 1, i);
        }
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                // 不相连的被替换相连的还原
                if (board[i][j] == 'O') {
                    board[i][j] = 'X';
                } else if (board[i][j] == '*') {
                    board[i][j] = 'O';
                }
            }
        }
    }

    private void dfs(char[][] board, int i, int j) {
        if (i < 0 || j < 0 || i >= board.length  || j >= board[0].length) {
            return;
        }
        if (dp[i][j]) {
            return;
        }
        if (board[i][j] == 'O') {
            board[i][j] = '*';
            dfs(board, i + 1, j);
            dfs(board, i - 1, j);
            dfs(board, i, j + 1);
            dfs(board, i, j - 1);
        }
        dp[i][j] = true;
    }
    /**
     * 1020 飞地的数量
     */
    public int numEnclaves(int[][] grid) {
        visited1020 = new boolean[grid.length][grid[0].length];
        // 4边遍历
        for (int i = 0; i < grid.length; i++) {
            dfs1020(grid,i, 0);
            dfs1020(grid,i, grid[0].length - 1);
        }
        for (int i = 1; i < grid[0].length - 1; i++) {
            dfs1020(grid,0, i);
            dfs1020(grid,grid.length -1, i);
        }

        int count = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1 && !visited1020[i][j]) {
                    count++;
                }
            }
        }
        return count;
    }
    private boolean[][] visited1020;
    private void dfs1020(int[][] grid, int i, int j) {
        if (i < 0 || j < 0 || i >= grid.length || j >= grid[0].length) {
            return;
        }
        if (grid[i][j] == 0 || visited1020[i][j]) {
            return;
        }
        visited1020[i][j] = true;
        dfs1020(grid, i + 1, j);
        dfs1020(grid, i - 1, j);
        dfs1020(grid, i, j + 1);
        dfs1020(grid, i, j - 1);
    }
    /**
     * 200 岛屿数量
     */
    public int numIslands(char[][] grid) {
        int count = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '1') {
                    count++;
                    dfs200(grid,i,j);
                }
            }
        }
        return count;
    }

    private void dfs200(char[][] grid, int i, int j) {
        if (i < 0 || j < 0 || i >= grid.length || j >= grid[0].length) {
            return;
        }
        if (grid[i][j] == '0') {
            return;
        }
        grid[i][j] = '0';
        dfs200(grid, i + 1, j);
        dfs200(grid, i - 1, j);
        dfs200(grid, i, j + 1);
        dfs200(grid, i, j - 1);
    }
    /**
     * 1254 统计封闭岛屿的数目
     */
    public int closedIsland(int[][] grid) {
        // 4边遍历排除边界岛屿
        for (int i = 0; i < grid.length; i++) {
            dfs1254(grid,i, 0);
            dfs1254(grid,i, grid[0].length - 1);
        }
        for (int i = 1; i < grid[0].length - 1; i++) {
            dfs1254(grid,0, i);
            dfs1254(grid,grid.length -1, i);
        }

        int count = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 0) {
                    count++;
                    dfs1254(grid, i, j);
                }
            }
        }
        return count;
    }
    private void dfs1254(int[][] grid, int i, int j) {
        if (i < 0 || j < 0 || i >= grid.length || j >= grid[0].length) {
            return;
        }
        if (grid[i][j] == 1) {
            return;
        }
        grid[i][j] = 1;
        dfs1254(grid, i + 1, j);
        dfs1254(grid, i - 1, j);
        dfs1254(grid, i, j + 1);
        dfs1254(grid, i, j - 1);
    }
    private int maxSum;
    /**
     * 695 岛屿的最大面积
     */
    public int maxAreaOfIsland(int[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1) {
                    List<Integer> nums = new ArrayList<>();
                    dfs695(grid,i,j,nums);
                    maxSum = Math.max(maxSum, nums.size());
                }
            }
        }
        return maxSum;
    }
    private void dfs695(int[][] grid, int i, int j,List<Integer> nums) {
        if (i < 0 || j < 0 || i >= grid.length || j >= grid[0].length) {
            return;
        }
        if (grid[i][j] == 0) {
            return;
        }
        grid[i][j] = 0;
        nums.add(0);
        dfs695(grid, i + 1, j,nums);
        dfs695(grid, i - 1, j,nums);
        dfs695(grid, i, j + 1,nums);
        dfs695(grid, i, j - 1,nums);
    }
    /**
     * 1905 统计封闭岛屿的数目
     */
    public int countSubIslands(int[][] grid1, int[][] grid2) {
        for (int i = 0; i < grid2.length; i++) {
            for (int j = 0; j < grid2[0].length; j++) {
                if (grid1[i][j] == 0 && grid2[i][j] == 1) {
                    dfs1905(grid2,i,j);
                }
            }
        }
        int count = 0;
        for (int i = 0; i < grid2.length; i++) {
            for (int j = 0; j < grid2[0].length; j++) {
                if (grid2[i][j] == 1) {
                    count++;
                    dfs1905(grid2,i,j);
                }
            }
        }
        return count;
    }

    private void dfs1905(int[][] grid, int i, int j) {
        if (i < 0 || j < 0 || i >= grid.length || j >= grid[0].length) {
            return;
        }
        if (grid[i][j] == 0) {
            return;
        }
        grid[i][j] = 0;
        dfs1905(grid, i + 1, j);
        dfs1905(grid, i - 1, j);
        dfs1905(grid, i, j + 1);
        dfs1905(grid, i, j - 1);
    }
    private List<Integer> res967 = new ArrayList<>();
    /**
     * 967. 连续差相同的数字
     */
    public int[] numsSameConsecDiff(int n, int k) {
        traverse967(n,k,0,0);
        return res967.stream().mapToInt(Integer::intValue).toArray();
    }

    private void traverse967(int n, int k, int index, int value) {
        if (index == n) {
            res967.add(value);
            return;
        }
        for (int i = 0; i < 10; i++) {
            if (index == 0 && i == 0) {
                continue;
            }
            if (index == 0 || Math.abs(i - value % 10) == k) {
                traverse967(n, k, index + 1, value * 10 + i);
            }
        }
    }

    private List<List<Integer>> res491 = new ArrayList<>();
    /**
     * 491. 递增子序列
     */
    public List<List<Integer>> findSubsequences(int[] nums) {
        dfs491(nums,0,new LinkedList<>());
        return res491;
    }
    private void dfs491(int[] nums, int index, LinkedList<Integer> result) {
        if (result.size() >= 2) {
            res491.add(new ArrayList<>(result));
        }
        HashSet<Integer> used = new HashSet<>();
        for (int i = index; i < nums.length; i++) {
            if (!result.isEmpty() && result.getLast() > nums[i]) {
                continue;
            }
            if (used.contains(nums[i])) {
                continue;
            }
            result.addLast(nums[i]);
            used.add(nums[i]);
            dfs491(nums, i+1, result);
            result.removeLast();
        }
    }
    private int pathCount = 0;
    /**
     * 980. 不同路径 III
     */
    public int uniquePathsIII(int[][] grid) {
        // 从起点到终点要走完所有可以走的格子返回路径数
        int startX = 0,startY = 0;
        int pathLength = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1) {
                    startX = i;
                    startY = j;
                    pathLength++;
                }
                if (grid[i][j] == 0) {
                    pathLength++;
                }
            }
        }
        dfs980(grid, startX, startY, pathLength);
        return pathCount;
    }
    private void dfs980(int[][] grid, int startX, int startY, int pathlength) {
        // 边界或者路障
        if (startX < 0 || startY < 0 || startX >= grid.length || startY >= grid[0].length || grid[startX][startY] == -1) {
            return;
        }
        // 走到终点
        if (grid[startX][startY] == 2) {
            if (pathlength == 0) {
                pathCount++;
            }
            return;
        }

        // 走过的变成路障
        pathlength--;
        grid[startX][startY] = -1;
        dfs980(grid, startX + 1, startY,pathlength);
        dfs980(grid, startX - 1, startY,pathlength);
        dfs980(grid, startX, startY + 1,pathlength);
        dfs980(grid, startX, startY - 1,pathlength);
        // 撤销
        grid[startX][startY] = 0;
    }

}
