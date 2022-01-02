package com.asura.structure.array;

/**
 * 就地删除有序数组重复元素
 */
public class Distinct {
    public int removeDuplicates(int[] nums) {
        // 记录判重值
        int index = 0;
        for (int i = 0; i < nums.length; i++) {
            // 重复条件
            if (i == 0 || nums[i - 1] != nums[i]) {
                nums[index] = nums[i];
                index++;
            }
        }
        return index;
    }
}
