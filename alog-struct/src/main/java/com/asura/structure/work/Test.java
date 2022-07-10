package com.asura.structure.work;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

public class Test {
	private static final Test a = new Test();
	private Test() {

	}
    public static void main(String[] args) throws InterruptedException {
		Test b = getA();
		System.out.println(1);
    }

	public static Test getA() {
		return a;
	}






    public List<List<Integer>> fourSum(int[] nums, int target) {
        if (nums.length < 4) {
            return new LinkedList<>();
        }
        Arrays.sort(nums);
        // 解空间
        List<List<Integer>> ans = new LinkedList<>();
        for (int i = 0; i < nums.length - 3; i++) {
            // 四数之和大于target不必在算了
            if ((long) nums[i] + nums[i + 1] + nums[i + 2] + nums[i + 3] > target) {
                return ans;
            }
            // 下面这四数之和小于target, 则nums[i]小了
            if ((long) nums[i] + nums[nums.length - 3] + nums[nums.length - 2] + nums[nums.length - 1] < target) {
                continue;
            }
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            // 双指针
            for (int j = i + 1; j < nums.length - 2; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    continue;
                }
                int tmp = target - nums[i] - nums[j];
                // 指向数组最右端
                int d = nums.length - 1;
                for (int c = j + 1; c < nums.length - 1; c++) {
                    if (c > j + 1 && nums[c] == nums[c - 1]) {
                        continue;
                    }
                    while (c < d && nums[c] + nums[d] > tmp) {
                        d--;
                    }
                    if (c < d && nums[c] + nums[d] == tmp) {
                        ans.add(Arrays.asList(nums[i], nums[j], nums[c], nums[d]));
                    }
                }
            }
        }
        return ans;
    }

    public boolean hasPath(int[][] maze, int[] start, int[] destination) {
        boolean[][] visited = new boolean[maze.length][maze[0].length];
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < visited[i].length; j++) {
                visited[i][j] = false;
            }
        }
        return dfs(maze, start[0], start[1], destination, visited);
    }

    private boolean dfs(int[][] maze, int x, int y, int[] destination, boolean[][] visited) {
        //走全找不到出路
        if (visited[x][y]) {
            return false;
        }
        // 找到路
        if (x == destination[0] && y == destination[1]) {
            return true;
        }
        visited[x][y] = true;
        // 右
        int ri = y + 1;
        // 左
        int le = y - 1;
        // 上
        int top = x - 1;
        // 下
        int bottom = x + 1;
        int m = maze.length, n = maze[0].length;
        if (dfsMove(maze, x, y, destination, visited, ri, le, top, bottom, m, n)) {
            return true;
        }
        return false;
    }

    private boolean dfsMove(int[][] maze, int x, int y, int[] destination, boolean[][] visited, int ri, int le, int top, int bottom, int m, int n) {
        // 右走到头
        while (ri < n && maze[x][ri] == 0) {
            ri++;
        }
        if (dfs(maze, x, ri - 1, destination, visited)) {
            return true;
        }
        // 左走到头
        while (le >= 0 && maze[x][le] == 0) {
            le--;
        }
        if (dfs(maze, x, le + 1, destination, visited)) {
            return true;
        }
        // 上走到头
        while (top >= 0 && maze[top][y] == 0) {
            top--;
        }
        if (dfs(maze, top + 1, y, destination, visited)) {
            return true;
        }
        // 下走到头
        while (bottom < m && maze[bottom][y] == 0) {
            bottom++;
        }
        if (dfs(maze, bottom - 1, y, destination, visited)) {
            return true;
        }
        return false;
    }
}
