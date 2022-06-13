package com.asura.structure.array;

/**
 * solution about array
 *
 */
public class ArraySolution {
	/**
	 * 就地删除有序数组重复元素
	 *
	 * @param nums [1,2,3,3,5]
	 * @return no repeat nums length
	 */
	public static int removeSortDuplicates(int[] nums) {
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

	/**
	 * move array value = 0 to tail
	 *
	 * @param nums
	 */
	public static void moveZeroes(int[] nums) {
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

	/**
	 *  合并 nums2 到 nums1 中，使合并后的数组同样按 递增顺序 排列
	 *  num1存在n个空值在尾
	 *
	 * @param nums1
	 * @param m
	 * @param nums2
	 * @param n
	 */
	public static void merge(int[] nums1, int m, int[] nums2, int n) {
		// 重大到小填值避免值被覆盖
		for (int i = m + n - 1; i >= 0; i--) {
			// 记录哪边大
			if (n == 0 || (nums1[m - 1] >= nums2[n - 1] && m >= 1)) {
				nums1[i] = nums1[--m];
			} else {
				nums1[i] = nums2[--n];
			}
		}

	}
}
