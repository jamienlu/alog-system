package com.asura.structure.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jamieLu
 * @create 2025-02-08
 */
public class BstArrayCase {
    /**
     * 235二叉搜索树中第 K 小的元素
     */
    public int kthSmallest(TreeNode root, int k) {
        // 中序遍历有序 是否可以不遍历完
        List<Integer> result = new ArrayList<>();
        foreach235(root, result, k);
        return result.get(k - 1);
    }
    private void foreach235(TreeNode root, List<Integer> nums, int k) {
        if (root == null || nums.size() == k) {
            return;
        }
        foreach235(root.left, nums, k);
        nums.add(root.val);
        foreach235(root.right, nums,k);
    }
    /**
     * 538 把二叉搜索树转换为累加树
     */
    public TreeNode convertBST(TreeNode root) {
        // 相当于从右边子树累加
        // 中序遍历拿到节点值对右节点求和即可
        // 从右遍历保证大到小的顺序
        foreach538(root);
        return root;
    }
    private int sum538 = 0;
    public void foreach538(TreeNode root) {
        if (root == null) {
            return;
        }
        foreach538(root.right);
        sum538 += root.val;
        root.val = sum538;
        foreach538(root.left);
    }
    /**
     * 450 删除二叉搜索树中的节点
     */
    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) {
            return null;
        }
        if (root.val == key) {
            // 找到啦，进行删除
            if (root.left == null && root.right == null) {
                return null;
            } else if (root.left == null) {
                // 右子树顶替
                return root.right;
            } else if (root.right == null) {
                // 左子树
                return root.left;
            } else {
                // 寻找左子树最大的节点替换
                TreeNode leftMax = getLeftMax(root.left);
                // 删除该节点
                root.left = deleteNode(root.left, leftMax.val);
                // 替换root节点
                leftMax.right = root.right;
                leftMax.left = root.left;
                root = leftMax;
            }
        } else if (root.val > key) {
            // 去左子树找
            root.left = deleteNode(root.left, key);
        } else {
            // 去右子树找
            root.right = deleteNode(root.right, key);
        }
        return root;
    }
    private TreeNode getLeftMax(TreeNode root) {
        while (root.right != null) {
            root = root.right;
        }
        return root;
    }
    /**
     * 701 二叉搜索树中的插入操作
     */
    public TreeNode insertIntoBST(TreeNode root, int val) {
        // 根据大小选择左右2边插入即可
        if (root == null) {
            return new TreeNode(val);
        }
        if (root.val > val) {
            root.left = insertIntoBST(root.left, val);
        } else {
            root.right = insertIntoBST(root.right, val);
        }
        return root;

    }
    /**
     * 98 验证二叉搜索树
     */
    public boolean isValidBST(TreeNode root) {
        // 当前节点值大于左节点值 小于右节点值
        // 当前节点是右节点 大于根节点
        // 当前节点是左节点 小于根节点
        return foreach(root, null, null);

    }
    public boolean foreach(TreeNode root, TreeNode min, TreeNode max) {
        if (root == null) {
            return true;
        }
        if (min != null && root.val <= min.val) {
            return false;
        }
        if (max != null && root.val >= max.val) {
            return false;
        }
        boolean left = foreach(root.left, min, root);
        boolean right = foreach(root.right, root, max);
        return  left && right ;
    }
    /**
     * 96 不同的二叉搜索树
     */
    public int numTrees(int n) {
        nums = new int[n+1][n+1];
        return count(1,n);
    }
    int [][] nums;

    private int count(int min,int max) {
        if (min > max) {
            return 1;
        }
        if (nums[min][max] != 0) {
            return nums[min][max];
        }
        int res = 0;
        for (int i = min; i <= max; i++) {
            int left = count(min, i - 1);
            int right = count(i + 1, max);
            res += left * right;
        }
        nums[min][max] = res;
        return res;
    }


}
