package com.asura.structure.stack;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Stack;

/**
 * @author jamieLu
 * @create 2025-02-06
 */
public class SortedStackCase {
    /**
     * 496. 下一个更大元素 I
     */
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        // 单调栈 遍历存储下一个最大元素的hash表
        // 待查列表可以在O(1)时间复杂度内查询到下一个最大元素
        int[] res = new int[nums1.length];
        Map<Integer, Integer> nextMax = new HashMap<>();
        Stack<Integer> stack = new Stack<>();
        for (int i = nums2.length -1; i >= 0 ; i--) {
            while (!stack.isEmpty() && stack.peek() < nums2[i]) {
                stack.pop();
            }
            if (!stack.isEmpty()) {
                nextMax.put(nums2[i], stack.peek());
            } else {
                nextMax.put(nums2[i], -1);
            }
            stack.push(nums2[i]);
        }
        for (int i = 0; i < nums1.length; i++) {
            res[i] = nextMax.get(nums1[i]);
        }
        return res;
    }
    /**
     * 739. 每日温度
     */
    public int[] dailyTemperatures(int[] temperatures) {
        // 单调栈存索引
        int[] res = new int[temperatures.length];
        Stack<Integer> stack = new Stack<>();
        for (int i = temperatures.length -1; i >= 0; i--) {
            while (!stack.isEmpty() && temperatures[i] >= temperatures[stack.peek()]) {
                stack.pop();
            }
            res[i] = stack.isEmpty() ? 0 : stack.peek() - i;
            stack.push(i);
        }
        return res;
    }
    /**
     * 503. 下一个更大元素 II
     */
    public int[] nextGreaterElements(int[] nums) {
        // 环数组 双倍数组单调返回前半段即可
        int [] result = new int[nums.length * 2];
        int[] res = new int[nums.length * 2];
        for (int i = 0; i < nums.length; i++) {
            res[i] = nums[i];
            res[i + nums.length] = nums[i];
        }
        Stack<Integer> stack = new Stack<>();
        for (int i = res.length -1 ; i >= 0; i--) {
            while (!stack.isEmpty() && res[i] >= stack.peek()) {
                stack.pop();
            }
            result[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(res[i]);
        }
        return Arrays.copyOfRange(result, 0 , nums.length);
    }
    /**
     * 1019. 链表中的下一个更大节点
     */
    public int[] canSeePersonsCount(int[] heights) {
        // 寻找右侧第一个比他高的人和他之间的距离
        int[] res = new int[heights.length];
        Stack<Integer> stack = new Stack<>();
        for (int i = heights.length -1; i >= 0; i--) {
            // 记录有多少个比它矮
            int count = 0;
            while (!stack.isEmpty() && stack.peek() < heights[i]) {
                stack.pop();
                count++;
            }
            // 栈空后面的都可以看到 不空可以看到比它高的第一个人
            res[i] = stack.isEmpty() ? count : count + 1;
            stack.push(heights[i]);
        }
        return res;
    }

    /**
     * 901. 股票价格跨度
     */
    private class StockSpanner {
        // 记录小于该股票的价格 和 小于该股票的天数
        Stack<int[]> stack = new Stack<>();
        public StockSpanner() {

        }
        public int next(int price) {
            int count = 1;
            while (!stack.isEmpty() && stack.peek()[0] <= price) {
                int[] temp =stack.pop();
                count+= temp[1];
            }
            int[] result = new int[]{price,count};
            stack.push(new int[]{price,count});
            return result[1];
        }
    }
    /**
     * 402. 移掉 K 位数字
     */
    public String removeKdigits(String num, int k) {
        // 顺序存储单调递增的子序列
        char[] chars = num.toCharArray();
        Deque<Character> stack = new LinkedList<>();
        for (int i = 0; i < chars.length; i++) {
            while (k > 0 && !stack.isEmpty() && stack.peek() > chars[i]) {
                stack.pollFirst();
                k--;
            }
            // 0 不能作首位
            if (chars[i] != '0' || !stack.isEmpty()) {
                stack.addFirst(chars[i]);
            }
        }
        // 单调递增后还没移除玩 继续删除
        while (k > 0 && !stack.isEmpty()) {
            stack.pollFirst();
            k--;
        }
        if (stack.isEmpty()) {
            return "0";
        }
        // 从右往左吐
        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            sb.append(stack.pollLast());
        }
        return sb.toString();
    }
    /**
     * 853. 车队
     */
    public int carFleet(int target, int[] position, int[] speed) {
        // 寻找递增的连续时间
        // 必须位置递增才会有追赶机制成为一个车队
        int [][] pairs = new int[position.length][2];
        for (int i = 0; i < position.length; i++) {
            pairs[i][0] = position[i];
            pairs[i][1] = speed[i];
        }
        Arrays.sort(pairs, Comparator.comparingInt(a -> a[0]));
        double [] times = new double[position.length];
        for (int i = 0; i < pairs.length; i++) {
            times[i] = (double) (target - pairs[i][0]) / pairs[i][1];
        }
        Stack<Double> stack = new Stack();
        for (int i = 0; i < times.length; i++) {
            while (!stack.isEmpty() && stack.peek() <= times[i]) {
                stack.pop();
            }
            stack.push(times[i]);
        }
        return stack.size();
    }
    /**
     * 581. 最短无序连续子数组
     */
    public int findUnsortedSubarray(int[] nums) {
        // 思路1 数组排序 比较2数组 左右2边最开始的不同值
        // 思路2 单调+栈 比当前元素大的栈元素都应该被排出
        Stack<Integer> stack = new Stack<>();
        int min = nums.length - 1;
        int max = 0;
        for (int i = 0; i < nums.length ; i++) {
            while (!stack.isEmpty() &&  nums[stack.peek()] > nums[i]) {
                int index = stack.pop();
                min = Math.min(min, index);
            }
            stack.push(i);
        }
        stack.clear();
        for (int i = nums.length -1; i >= 0; i--) {
            while (!stack.isEmpty() && nums[i] > nums[stack.peek()]) {
                int index = stack.pop();
                max = Math.max(max, index);
            }
            stack.push(i);
        }
        if (min == nums.length - 1 && max == 0) {
            return 0;
        }
        return max - min + 1;
    }
}
