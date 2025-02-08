package com.asura.structure.queue;

import java.util.LinkedList;
import java.util.List;

/**
 * @author jamieLu
 * @create 2025-02-06
 */
public class SortedQueueCase {
    private class MonotonicQueue<T extends Comparable<T>> {
        private LinkedList<T> queue = new LinkedList<T>();

        // 取出比这更小的数
        public void push(T elem) {
            while (!queue.isEmpty() && queue.getLast().compareTo(elem) < 0) {
                queue.pollLast();
            }
            queue.addLast(elem);
        }

        // 弹出最大的数
        public T pop() {
            return (T) queue.pollFirst();
        }

        public int size() {
            return queue.size();
        }

        public T max() {
            return (T) queue.getFirst();
        }

        public T min() {
            return (T) queue.getLast();
        }
    }
    /**
     * 239. 滑动窗口最大值
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        MonotonicQueue<Integer> queue = new MonotonicQueue<>();
        int[] res = new int[nums.length - k + 1];
        for (int i = 0; i < nums.length; i++) {
            queue.push(nums[i]);
            // 窗口被填满后
            if (i >= k - 1) {
                int max = queue.max();
                int index = i - k + 1;
                res[index] = max;
                // 左窗口是弹出的数，需要移除
                if (max == nums[index]) {
                    queue.pop();
                }
            }
        }
        return res;
    }
}
