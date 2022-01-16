package com.asura.structure.recur;

import java.util.*;

public class Rank {
    public List<List<Integer>> permuteUnique(int[] nums) {
        int len = nums.length;
        List<List<Integer>> ans = new ArrayList<>();
        if (len == 0) {
            return ans;
        }
        // 排序
        Arrays.sort(nums);
        // 是否选择该节点
        boolean[] used = new boolean[len];
        Deque<Integer> path = new ArrayDeque<>(len);
        // 深度搜索
        dfs(nums, len, 0, used, path, ans);
        return ans;
    }

    private void dfs(int[] nums, int len, int depth, boolean[] used, Deque<Integer> path, List<List<Integer>> ans) {
        // 达到搜索的深度退出
        if (depth == len) {
            ans.add(new ArrayList<>(path));
            return;
        }
        for (int i = 0; i < len; ++i) {
            // 已经选过该节点
            if (used[i]) {
                continue;
            }
            // 前一个节点和这一个节点相同 并且前一个节点没被选 同样不用选现在这个节点
            if (i > 0 && nums[i] == nums[i - 1] && !used[i - 1]) {
                continue;
            }
            // 使用该节点
            path.addLast(nums[i]);
            used[i] = true;

            dfs(nums, len, depth + 1, used, path, ans);
            // 还原现场
            used[i] = false;
            path.removeLast();
        }
    }

}
