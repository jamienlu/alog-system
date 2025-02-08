package com.asura.structure.method;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;


/**
 * @author jamieLu
 * @create 2025-02-03
 */
public class SlidingWindow {
    public int lengthOfLongestSubstring(String s) {
        // 滑动窗口扫描字符串
        // 进入1个字符计数+1，退出一个字符计数-1
        // 当计数大于1就是有重复的需要左窗口右移
        // 排除的后还大于1就是之前重复的需要右窗口右移
        Map<Character, Integer> windows = new HashMap<>();
        int left = 0, right = 0;
        int res = 0;
        while (right < s.length()) {
            char c = s.charAt(right);
            right++;
            windows.put(c, windows.getOrDefault(c, 0) + 1);
            while (windows.get(c) > 1) {
                char d = s.charAt(left);
                left++;
                windows.put(d, windows.getOrDefault(d, 0) - 1);
            }
            res = Math.max(res, right - left);
        }
        return res;
    }
    public List<Integer> findAnagrams(String s, String p) {
        // 思路滑动窗口 子串字符计数
        // 窗口右移 主串计数 主子串字符数相同匹配度+1
        // 窗口范围是否能覆盖子串长度 不能继续右移,能左移直到满足条件不满足继续右移

        Map<Character, Integer> need = p.chars().mapToObj(x -> (char) x)
                .collect(Collectors.groupingBy(c -> c, Collectors.summingInt(c -> 1)));
        Map<Character, Integer> window = new HashMap<>();
        int left = 0, right = 0;
        int valid = 0;
        List<Integer> res = new ArrayList<>();
        while (right < s.length()) {
            char c = s.charAt(right);
            right++;
            if (need.containsKey(c)) {
                window.put(c, window.getOrDefault(c, 0) + 1);
                // 匹配成功
                if (window.get(c).equals(need.get(c))) {
                    valid++;
                }
            }
            // 是否需要左侧窗口移动
            while (right - left >= p.length()) {
                // 满足异味词
                if (valid == need.size()) {
                    res.add(left);
                }
                // 收缩左侧
                char d = s.charAt(left);
                left++;
                if (need.containsKey(d)) {
                    if (window.get(d).equals(need.get(d))) {
                        valid--;
                    }
                    window.put(d, window.get(d) - 1);
                }
            }
        }
        return res;
    }
    public String minWindow(String s, String t) {
        if (s == null || t == null || s.length() < t.length()) {
            return "";
        }
        Map<Character, Integer> need = t.chars().mapToObj(x -> (char) x)
            .collect(Collectors.groupingBy(c -> c, Collectors.summingInt(c -> 1)));
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

    public int longestOnes(int[] nums, int k) {
        // 思路 转化为最多包含K个0的最长序列
        // 滑动窗口对1计数 有1个1就+1, 窗口数是1的个数+K就是该窗口的最大子序列
        // 收缩窗口 收缩的是1就计数-1
        int res = 0;
        int left = 0, right = 0;
        int maxCount = 0;
        while (right < nums.length) {
            // 1 计数
            if (nums[right] == 1) {
                maxCount++;
            }
            right++;
            // 收缩寻找窗口的最大子序列
            while (right - left > maxCount + k) {
                if (nums[left] == 1) {
                    maxCount--;
                }
                left++;
            }
            // 选历史最大的一个
            res = Math.max(res, right - left);
        }
        return res;
    }

    public int characterReplacement(String s, int k) {
        int left = 0, right = 0;
        // 大写字母长度编码
        int[] windowCharCount = new int[26];
        int windowMaxCount = 0;
        int res = 0;

        // 滑动窗口模板
        while (right < s.length()) {
            // 计算数组位置
            int c = s.charAt(right) - 'A';
            windowCharCount[c]++;
            windowMaxCount = Math.max(windowMaxCount, windowCharCount[c]);
            right++;
            // 缩小窗口 对应计算-1
            while (right - left - windowMaxCount > k) {
                windowCharCount[s.charAt(left) - 'A']--;
                left++;
            }
            // 经过收缩后，此时一定是一个合法的窗口
            res = Math.max(res, right - left);
        }
        return res;
    }
    public boolean containsNearbyAlmostDuplicate(int[] nums, int indexDiff, int valueDiff) {

        // 1.滑动窗口 窗口小于indexDiff扩展 大于收缩
        // 窗口范围内存在2个值差为valueDiff的返回true
        // treeset可获取目标值最接近的上下限值
        // 2. 桶排序
        Map<Integer,Integer> buck = new HashMap<>();
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < nums.length; i++) {
            min = Math.min(min, nums[i]);
        }
        int bucketSize = valueDiff + 1;
        for (int i = 0; i < nums.length; i++) {
            int id = (nums[i] - min) / bucketSize;
            if (buck.containsKey(id)) {
                return true;
            }
            if (buck.containsKey(id - 1) && nums[i] - buck.get(id -1) <= valueDiff) {
                return true;
            }
            if (buck.containsKey(id + 1)  && buck.get(id + 1) - nums[i] <= valueDiff) {
                return true;
            }
            buck.put(id, nums[i]);
            // 新的元素窗口最左边的元素被移除
            if (i >= indexDiff) {
                buck.remove((nums[i - indexDiff] - min) / bucketSize);
            }
        }
        return false;
    }

}
