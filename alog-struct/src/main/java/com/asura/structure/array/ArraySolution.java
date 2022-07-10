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
		/**
		 * 思路：
		 *  1.保存当前数组已存非重复数下标 init = 0
		 *  2.扫描数组 不重复填充，下标+1，重复跳到下一个元素
		 *
 		 */
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
		/**
		 * 思路：
		 *  1.保存当前数组非0下标 init = 0
		 *  2.扫描数组 ！=0填充，下标+1，=0跳到下一个元素
		 *  3.从填充的下标开始后面都重新补0
		 */
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
		/**
		 * 思路：
		 *  1.从大到小排序 从右到左放数
		 *  2.比较2端最大值 最大值位-1
		 *  3.最大值放最高位 最高位减1
		 *  4.最后一个放到最低位
		 */
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
