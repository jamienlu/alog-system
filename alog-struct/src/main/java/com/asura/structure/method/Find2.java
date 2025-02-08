package com.asura.structure.method;

import java.util.Arrays;

/**
 * @author jamieLu
 * @create 2025-02-05
 */
public class Find2 {
    // common
    public int binary_search(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while(left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < target) {
                left = mid + 1;
            } else if (nums[mid] > target) {
                right = mid - 1;
            } else if(nums[mid] == target) {
                // 直接返回
                return mid;
            }
        }
        // 直接返回
        return -1;
    }
    // 搜索左侧边界
    public int left_bound(int[] nums, int target) {
        if (nums.length == 0) return -1;
        int left = 0, right = nums.length;

        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                // 当找到 target 时，收缩右侧边界
                right = mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else if (nums[mid] > target) {
                right = mid;
            }
        }
        return left;
    }
    // 搜索右侧边界
    public int right_bound(int[] nums, int target) {
        if (nums.length == 0) return -1;
        int left = 0, right = nums.length;

        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                // 当找到 target 时，收缩左侧边界
                left = mid + 1;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else if (nums[mid] > target) {
                right = mid;
            }
        }
        return left - 1;
    }

    /**
     * 875. 爱吃香蕉的珂珂
     */
    public int minEatingSpeed(int[] piles, int h) {
        // 吃完的最小速度，寻找吃完时间的最大右边界
        // 寻找速度最小值和最大值
        Arrays.sort(piles);
        // 闭区间二分查找
        int left = 1;
        int right = piles[piles.length - 1];
        while (left <= right) {
            int mid = left + (right- left) / 2;
            long time = time(piles, mid);
            if (time <= h) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    private long time(int[] nums, int speed) {
        long time = 0;
        for (int num : nums) {
            time += num / speed;
            if (num % speed != 0) {
                time++;
            }
        }
        return time;
    }
    /**
     * 1011. 在 D 天内送达包裹的能力
     */
    public int shipWithinDays(int[] weights, int days) {
        // 寻找关于承重能力和到达时间的函数
        // 二分查找承重寻找最大右边界
        int left = 0;
        int right = 0;
        for (int w : weights) {
            left = Math.max(left, w);
            right += w;
        }
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int need = day(weights, mid);
            if (need <= days) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    private int day(int[] weights, int weight) {
        int day = 0;
        for (int i = 0; i < weights.length;) {
            int cap = weight;
            while (i < weights.length) {
                if (cap < weights[i]) {
                    break;
                }
                else {
                    cap -= weights[i];
                }
                i++;
            }
            day++;
        }
        return day;
    }

    public int splitArray(int[] nums, int k) {
        // 寻找关于子数组和和切割次数的函数
        // 二分查找子数组和的最大右边界
        int left = 0;
        int right = 0;
        for (int num : nums) {
            left = Math.max(left, num);
            right += num;
        }
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (splitCount(nums, mid) > k) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return left;

    }
    private int splitCount(int[] nums, int sum) {
        int count = 0;
        for (int i = 0; i < nums.length;) {
            int temp = 0;
            while (i < nums.length) {
                if (temp + nums[i] > sum) {
                    break;
                }
                else {
                    temp += nums[i];
                }
                i++;
            }
            count++;

        }
        return count;
    }

}
