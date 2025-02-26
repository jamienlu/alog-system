package com.asura.template.slidewindow;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author jamieLu
 * @create 2025-02-11
 */
public class SlideWindowCase {
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
    /**
     * 76 最小覆盖子串
     */
    public String minWindow(String s, String t) {
        if (s == null || t == null || s.length() < t.length()) {
            return "";
        }
        Map<Character, Integer> need = t.chars().mapToObj(x -> (char) x).collect(Collectors.groupingBy(c -> c, Collectors.summingInt(c -> 1)));
        Map<Character, Integer> window = new HashMap<>();

        int left = 0, right = 0;
        int valid = 0;
        // 记录最小覆盖子串的起始索引及长度
        int start = 0, len = Integer.MAX_VALUE;
        while (right < s.length()) {
            // c 是将移入窗口的字符
            char c = s.charAt(right);
            // 扩大窗口
            right++;
            // 进行窗口内数据的一系列更新
            if (need.containsKey(c)) {
                window.put(c, window.getOrDefault(c, 0) + 1);
                if (window.get(c).equals(need.get(c))) {
                    valid++;
                }
            }
            // 判断左侧窗口是否要收缩
            while (valid == need.size()) {
                // 更新最小覆盖子串
                if (right - left < len) {
                    start = left;
                    len = right - left;
                }
                // d 是将移出窗口的字符
                char d = s.charAt(left);
                // 缩小窗口
                left++;
                // 进行窗口内数据的一系列更新
                if (need.containsKey(d)) {
                    if (window.get(d).equals(need.get(d))) {
                        valid--;
                    }
                    window.put(d, window.get(d) - 1);
                }
            }
        }
        // 返回最小覆盖子串
        return len == Integer.MAX_VALUE ? "" : s.substring(start, start + len);
    }
}
