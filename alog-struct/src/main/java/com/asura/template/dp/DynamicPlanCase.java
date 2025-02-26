package com.asura.template.dp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jamieLu
 * @create 2025-02-18
 */
public class DynamicPlanCase {
    /**
     * 53. 最大子数组和
     */
    public int maxSubArray(int[] nums) {
        // 对于每一个元素要么加入最大子数组中 要么重新成为数组头
        // dp[i] = Math.max(dp[i - 1] + nums[i], nums[i]);
        int n = nums.length;
        if (n == 0) {
            return 0;
        }
        int[] dp = new int[n];
        dp[0] = nums[0];
        for (int i = 1; i < n; i++) {
            dp[i] = Math.max(dp[i - 1] + nums[i], nums[i]);
        }

        return Arrays.stream(dp).max().getAsInt();
    }
    /**
     * 300. 最长递增子序列
     */
    public int lengthOfLIS(int[] nums) {
        // 最长递增公共子序列
        // 找到倒数第二长的公共子序列 如果最后一个数是递增的就 dp[n] = Math.max(dp[n], dp[n-1] + 1)
        int[] dp = new int[nums.length];
        Arrays.fill(dp, 1);
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }
        return Arrays.stream(dp).max().getAsInt();
    }

    public int maxEnvelopes(int[][] envelopes) {
        // 所有信封都要放进去 需要新的信封比之前所有的信封都大 dp[n] = Math.max(dp[n], dp[n-1] + 1)
        // 因为二维 1个维度升序 相同的化按另一个维度降序错开相同的不能包含
        // 寻找另一个维度的最长增子序列即可
        Arrays.sort(envelopes, (a, b) -> a[0] == b[0] ? b[1] - a[1] : a[0] - b[0]);
        int [] height = Arrays.stream(envelopes).mapToInt(x -> x[1]).toArray();
        int[] heap = new int[height.length];
        int heapSize = 0;
        for (int i = 0; i < height.length; i++) {
            int left = 0;
            int right = heapSize;
            while (left < right) {
                int mid = left + (right - left) / 2;
                if (heap[mid] >= height[i]) {
                    right = mid;
                } else {
                    left = mid + 1;
                }
            }
            if (left == heapSize) {
                heapSize++;
            }
            heap[left] = height[i];

        }
        return heapSize;
    }

    int[][] dp115;
    /**
     * 115. 不同的子序列
     */
    public int numDistinct(String s, String t) {
        // 在s的子串中找t
        // 遍历s 如果首字符匹配那么可以转化为 比较s{1..}t{0..} + s{1..}t{1..} 如果不匹配转化为 s{1..}t{1..}
        // 所以有2种状态方程分别为 dp[i][j] = dp[1+1][j] + dp[i+1][j+1] 和  dp[i][j] = dp[1+1][j]
        // 求值即可
        dp115 = new int[s.length()][t.length()];
        Arrays.stream(dp115).forEach(a -> Arrays.fill(a, -1));
        return numDistinct(s,t,0,0);
    }

    private int numDistinct(String s, String t, int i, int j) {
        if (j == t.length()) {
            return 1;
        }
        if (i == s.length()) {
            return 0;
        }
        if (dp115[i][j] != -1) {
            return dp115[i][j];
        }
        int res = 0;
        if (s.charAt(i) == t.charAt(j)) {
            res = numDistinct(s,t, i + 1, j + 1) + numDistinct(s,t, i + 1, j);
        } else {
            res = numDistinct(s,t, i + 1, j);
        }
        dp115[i][j] = res;
        return res;
    }
    /**
     * 1312. 让字符串成为回文串的最少插入次数
     */
    public int minInsertions(String s) {
        // 如果字符串本身是回文串 从｛0,end｝开始全部相等
        // 所以从又边界开始检查 如果相等则dp[i][j] = dp[i+1][j-1] 不等就要对2变字符遍历求最小次数 dp[i][j] = Math.min(dp[i][j - 1], dp[i + 1][j]) + 1;
        int m  = s.length();
        // 从左到右需要多少次编辑 单个字符为0
        int[][] dp = new int[m][m];
        for (int i = m - 1; i >= 0; i--) {
            for (int j = i + 1; j < m; j++) {
                // 左右2变字符相同不需要操作
                if (s.charAt(i) == s.charAt(j)) {
                    dp[i][j] = dp[i + 1][j - 1];
                } else {
                    // 可以选择在左边前插入 或者右边后插入 操作数为1
                    dp[i][j] = Math.min(dp[i][j - 1], dp[i + 1][j]) + 1;
                }
            }
        }
        return dp[0][m - 1];
    }
    /**
     * 494. 目标和
     */
    public int findTargetSumWays(int[] nums, int target) {
        // 添加符号得和等价于选负数使等式成立
        int sum = Arrays.stream(nums).sum();
        // sum < target 或者  (sum - neg) - neg = target(和为偶)
        if (sum < target || (sum - target) % 2 == 1) {
            return 0;
        }
        int n = nums.length;
        // 选负数和为neg
        int neg = (sum - target) / 2;
        // 总和对应的方案数
        int[][] dp = new int[n + 1][neg + 1];
        dp[0][0] = 1;
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= neg; j++) {
                // 数太能不能选
                if (j < nums[i - 1]) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    // 可以选可以不选
                    dp[i][j] = dp[i - 1][j] + dp[i - 1][j - nums[i - 1]];
                }
            }
        }
        return dp[n][neg];
    }
    /**
     * 174. 地下城游戏
     */
    public int calculateMinimumHP(int[][] dungeon) {
        // 最小血量为1 最小血量需满足血量减当前路径和大于0，后续每一个血量的扣减会影响前一次的决定
        // 所以从后往前走 满足当前血量大于等于1，获取最小路径和。定到终点前得血量等于1
        int m = dungeon.length;
        int n = dungeon[0].length;
        int[][] dp = new int[m + 1][n + 1];
        // 求最小值用最大值填充
        for (int i = 0; i <= m; i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE);
        }
        // 填充终点前的路径为最低1
        dp[m][n - 1] = 1;
        dp[m - 1][n] = 1;
        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
             // 到达该点的的最小路径为后一步的最小值减当前值
              int min = Math.min(dp[i + 1][j], dp[i][j + 1]) - dungeon[i][j];
              // 最低血量为当前最小值且必须大于等于1
              dp[i][j] = Math.max(min, 1);
            }
        }
        return dp[0][0];

    }


    /**
     * 514. 自由之路
     */
    public int findRotateSteps(String ring, String key) {
        // 在ring的环上 顺逆时针转动 匹配完key需要转动和记录每次字符的操作数的最小值
        // 首先需要记录ring所有字符字母对应的索引,才能在匹配后通过计算索引差计算转动次数
        // 对于重复字符前一次转动的结果会导致后一次是否更靠近下一次转动的结果所有要求整体最小值
        // 假设转动为x 则顺逆转动次数为 moveStep = min(x, ring.length - x)
        // dp[i][j] = min(dp[i][j], moveStep + 1 + 下一次转动的结果(重复字符在环上选的位置不同));
        int m = ring.length();
        int n = key.length();
        // 得先在模板上建立等值索引,才能在样本上找到匹配需要滑动的步数
        for (int i = 0; i < m; i++) {
            char c = ring.charAt(i);
            if (!charToIndex.containsKey(c)) {
                charToIndex.put(c, new ArrayList<>());
            }
            charToIndex.get(c).add(i);
        }
        dp514 = new int[m][n];
        return  findMinSteps(ring, 0, key, 0);
    }
    private final Map<Character, List<Integer>> charToIndex = new HashMap<>();
    private int [][] dp514;

    private int findMinSteps(String ring, int ringIndex, String key, int keyIndex) {
        if (keyIndex == key.length()) {
            return 0;
        }
        if (dp514[ringIndex][keyIndex] != 0) {
            return dp514[ringIndex][keyIndex];
        }
        char c = key.charAt(keyIndex);
        List<Integer> indexes = charToIndex.get(c);
        int minSteps = Integer.MAX_VALUE;
        for (int index : indexes) {
            int delta = Math.abs(index - ringIndex);
            int moveStep = Math.min(delta, ring.length() - delta);
            // 移动的步数 + 继续查询完毕的步数 选最小值
            int steps = moveStep + 1 + findMinSteps(ring, index, key, keyIndex + 1);
            minSteps = Math.min(minSteps, steps);
        }
        dp514[ringIndex][keyIndex] = minSteps;
        return minSteps;
    }
}
