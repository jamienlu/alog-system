package com.asura.structure.array;

/**
 * 就地移除元素为0的到末尾
 */
public class RemoveZeros {
    public void moveZeroes(int[] nums) {
        int index = 0;
        for (int i = 0; i < nums.length; i++) {
            // 是否为0
            if (nums[i] != 0) {
                nums[index++] = nums[i];
            }
        }
        // 数组非0位填完后其他补0
        if (index < nums.length) {
            for (int i = index; i < nums.length; i++) {
                nums[i] = 0;
            }
        }

    }
}
