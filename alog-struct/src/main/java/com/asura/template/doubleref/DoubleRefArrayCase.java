package com.asura.template.doubleref;

/**
 * @author jamieLu
 * @create 2025-02-02
 */
public class DoubleRefArrayCase {
    /**
     * 26. 删除有序数组中的重复项
     */
    public int removeDuplicates(int[] nums) {
        // 思路双指针单向前进，重复元素左边不前进，不重复赋值
        if (nums.length == 0) {
            return 0;
        }
        int start = 0;
        int end = 0;
        while (end < nums.length) {
            if (nums[start] != nums[end]) {
                start++;
                nums[start] = nums[end];
            }
            end++;
        }
        return start + 1;
    }
    /**
     * 167. 两数之和 II - 输入有序数组
     */
    public int[] twoSum(int[] numbers, int target) {
        // 思路1 hash key = 存目标数-当前数的值 value = index,遍历查需要的
        // 思路2 双指针 左右走中间，值小坐前进值大右前进
        int left = 0;
        int right = numbers.length - 1;
        while (left < right) {
            int sum = numbers[left] + numbers[right];
            if (sum == target) {
                return new int[]{left + 1, right + 1};
            } else if (sum < target) {
                left++;
            } else {
                right--;
            }
        }
        return new int[]{-1,-1};
    }
    /**
     * 5. 最长回文子串
     */
    public String longestPalindrome(String s) {
        // 思路 找中心点2边扩展相同是回文
        // 1个中心和2个中心 依次遍历找到最长的
        String res ="";
        for (int i = 0; i < s.length(); i++) {
            String res1 = longestPalindromeCenter(s, i, i);
            String res2 = longestPalindromeCenter(s, i, i+1);
            res = res.length() >= res1.length() ? res : res1;
            res = res.length() >= res2.length() ? res : res2;
        }
        return res;
    }
    private String longestPalindromeCenter(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        return s.substring(left + 1, right);
    }
}
