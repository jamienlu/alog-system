package com.asura.structure.array;

/**
 *   合并 nums2 到 nums1 中，使合并后的数组同样按 非递减顺序 排列
 *   num1 长度为m+n  num2 长度n  num1 > m部分初始为0
 */
public class MergeSorted {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        // 重大到小填值避免值被覆盖
        for (int i = m + n - 1; i >= 0; i--) {
            // 记录哪边大
            if (n < 1 || (m >= 1 && nums1[m - 1] >= nums2[n - 1])) {
                nums1[i] = nums1[--m];
            } else {
                nums1[i] = nums2[--n];
            }
        }

    }
}
