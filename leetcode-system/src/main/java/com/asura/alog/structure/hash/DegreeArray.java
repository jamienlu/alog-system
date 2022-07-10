package com.asura.alog.structure.hash;

import java.util.HashMap;
import java.util.Map;

public class DegreeArray {
    public int findShortestSubArray(int[] nums) {
        // 存储数 -> 数的次数 最近位置  最远位置
        Map<Integer, int[]> numMap = new HashMap<>();
        // 存储数组的度的信息
        for (int i = 0; i < nums.length; i++) {
            if (numMap.containsKey(nums[i])) {
                numMap.get(nums[i])[0]++;
                numMap.get(nums[i])[2] = i;
            } else {
                numMap.put(nums[i], new int[]{1, i, i});
            }
        }
        int sum = 0;
        int len = 0;
        // 遍历可能的度计算存储的下标之间的距离
        for (Map.Entry<Integer, int[]> entry : numMap.entrySet()) {
            int[] arr = entry.getValue();
            // 该数是数组的度  长度等于最远和最近之间的距离
            if (sum < arr[0]) {
                sum = arr[0];
                len = arr[2] - arr[1] + 1;
            // 度相同 选择距离更大的一个
            } else if (sum == arr[0]) {
                if (len > arr[2] - arr[1] + 1) {
                    len = arr[2] - arr[1] + 1;
                }
            }
        }
        return len;
    }
}
