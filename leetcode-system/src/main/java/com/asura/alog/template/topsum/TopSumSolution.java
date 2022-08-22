package com.asura.alog.template.topsum;

/**
 * 前缀和应用
 *
 * S{i} = a(0) + a(1) +..a(i)
 * S{j} - S{i} = 区间前缀和
 *
 */
public class TopSumSolution {
	/**
	 * 求最大子序列区间
	 *
	 * @param target 序列数组
	 * @return int
	 */
	public int maxSubArray(int[] target) {
		/**
		 * 思路
		 * 1.从第一个下标开始求连续最小子序列 和 当前下标序列和
		 * 2.当前下标序列和-最小连续子序列最大为前缀最大和
		 *
		 */
		int len = target.length;
		int maxSum = target[0];
		// 当前位置已知最小前缀和
		int minSum = 0;
		// 当前位置前缀和
		int nowSum = 0;
		for (int i = 0; i < len; i++) {
			nowSum += target[i];
			maxSum = Math.max(maxSum, nowSum - minSum);
			minSum = Math.min(minSum,nowSum);
		}
		return maxSum;
	}
}
