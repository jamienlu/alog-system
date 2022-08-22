package com.asura.alog.template.dynamicplan;

/**
 * 动态规划
 */
public class DynamicPlanSolution {
	/**
	 * 求最大子序列区间
	 *
	 * @param target 序列数组
	 * @return int
	 */
	public int maxSubArray(int[] target) {
		/**
		 * 思路
		 * 1.a = Q(list,i) b= Q(list,i-1)
		 * 2.if b < 0 a = b else a = b+ target[cur_index] 累计为负重新开始
		 */
		int len = target.length;
		int maxSum = target[0];
		int curSum = target[0];
		for (int i = 1; i < len; i++) {
			curSum = Math.max(curSum + target[i],  target[i]);
			maxSum = Math.max(curSum, maxSum);
		}
		return maxSum;
	}
}
