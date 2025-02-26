package com.asura.template.doubleref;

/**
 * @author jamieLu
 * @create 2025-02-05
 */
public class DecrArrayCase {
    private class DiffArray {
        // 差分数组
        private int[] diff;

        public DiffArray(int[] nums) {
            assert nums.length > 0;
            diff = new int[nums.length];
            // 构造差分数组
            diff[0] = nums[0];
            for (int i = 1; i < nums.length; i++) {
                diff[i] = nums[i] - nums[i - 1];
            }
        }

        // 给闭区间 [i, j] 增加 val（可以是负数）
        public void increment(int i, int j, int val) {
            diff[i] += val;
            if (j + 1 < diff.length) {
                diff[j + 1] -= val;
            }
        }

        public int[] result() {
            int[] res = new int[diff.length];
            // 根据差分数组构造结果数组
            res[0] = diff[0];
            for (int i = 1; i < diff.length; i++) {
                res[i] = res[i - 1] + diff[i];
            }
            return res;
        }
    }

    /**
     * 1109.航班预订统计
     * */
    public int[] corpFlightBookings(int[][] bookings, int n) {
        DiffArray diffArray = new DiffArray(new int[n]);
        for (int i = 0; i < bookings.length; i++) {
            int l = bookings[i][0] - 1;
            int r = bookings[i][1] - 1;
            int k = bookings[i][2];
            diffArray.increment(l, r, k);
        }
        return diffArray.result();
    }

    /**
     * 1094.拼车
     * */
    public boolean carPooling(int[][] trips, int capacity) {
        // 思路 在数组对应位置上下车 构造差分数组
        // 还原数组 判断是否大于capacity
        int[] diff = new int[1001];
        for (int i = 0; i < trips.length; i++) {
            int val = trips[i][0];
            int from = trips[i][1];
            int to = trips[i][2]; // 已经下车了开区间
            diff[from] += val;
            if (to < diff.length) {
                diff[to] -= val;
            }
        }

        int[] res = new int[diff.length];
        // 根据差分数组构造结果数组
        res[0] = diff[0];
        if (res[0] > capacity) {
            return false;
        }
        for (int i = 1; i < diff.length; i++) {
            res[i] = res[i - 1] + diff[i];
            if (res[i] > capacity) {
                return false;
            }
        }
        return true;
    }
}
