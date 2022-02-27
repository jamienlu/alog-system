package com.asura.structure.autoplan;

public class AntoSolution {
    /**
     *
     * 假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
     *
     * 每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
     *
     * @param n  2
     * @return   2
     */
    public int climbStairs(int n) {
        // 滚动数组
        int first = 0;
        int second = 0;
        int end = 1;
        for (int i = 1; i <= n; ++i) {
            first = second;
            second = end;
            end = first + second;
        }
        return end;

    }

    public int findNumberOfLIS(int[] nums) {
        int n = nums.length;
        int maxLen = 0;
        int ans = 0;
        int[] status = new int[n];
        int[] cnt = new int[n];
        for (int i = 0; i < n; ++i) {
            status[i] = 1;
            cnt[i] = 1;
            for (int j = 0; j < i; ++j) {
                if (nums[i] > nums[j]) {
                    if (status[j] + 1 > status[i]) {
                        status[i] = status[j] + 1;
                        cnt[i] = cnt[j];
                    } else if (status[j] + 1 == status[i]) {
                        cnt[i] += cnt[j];
                    }
                }
            }
            if (status[i] > maxLen) {
                maxLen = status[i];
                ans = cnt[i];
            } else if (status[i] == maxLen) {
                ans += cnt[i];
            }
        }
        return ans;
    }

    public int numSquares(int n) {
        int[] status = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            int minValue = Integer.MAX_VALUE;
            for (int j = 1; j * j <= i; j++) {
                minValue = Math.min(minValue, status[i - j * j]);
            }
            status[i] = minValue + 1;
        }
        return status[n];
    }

    public int longestPalindromeSubseq(String s) {
        int len = s.length();
        int[][] status = new int[len][len];
        for (int i = len - 1; i >= 0; i--) {
            status[i][i] = 1;
            char c1 = s.charAt(i);
            for (int j = i + 1; j < len; j++) {
                char c2 = s.charAt(j);
                // 长度较短的子序列向长度较长的子序列转移
                if (c1 == c2) {
                    status[i][j] = status[i + 1][j - 1] + 2;
                } else {
                    status[i][j] = Math.max(status[i + 1][j], status[i][j - 1]);
                }
            }
        }
        return status[0][len - 1];
    }


}
