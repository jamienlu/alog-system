package com.asura.structure.tree;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @author jamieLu
 * @create 2025-02-07
 */
public class TwoTreeCase {
    /**
     * 297. 二叉树的序列化与反序列化
     */
    private class Codec {
        private final String SEP = ",";
        private final String NULL = "#";

        // Encodes a tree to a single string.
        public String serialize(TreeNode root) {
            StringBuilder sb = new StringBuilder();
            analysisTree(root, sb);
            return sb.toString();
        }
        private void analysisTree(TreeNode root, StringBuilder sb) {
            if (root == null) {
                sb.append(NULL).append(SEP);
                return;
            }
            sb.append(root.val).append(SEP);
            analysisTree(root.left, sb);
            analysisTree(root.right, sb);
        }

        // Decodes your encoded data to tree.
        public TreeNode deserialize(String data) {
            // 前序遍历转化成链表左边取完右边在取
            LinkedList<String> nodes = new LinkedList<>();
            for (String s : data.split(SEP)) {
                nodes.addLast(s);
            }
            return buildTree(nodes);
        }

        private TreeNode buildTree(LinkedList<String> nodes) {
            if (nodes.isEmpty()) {
                return null;
            }
            String first = nodes.removeFirst();
            if (first.equals(NULL)) {
                return null;
            }
            TreeNode root = new TreeNode(Integer.parseInt(first));
            root.left = buildTree(nodes);
            root.right = buildTree(nodes);
            return root;
        }
    }
    // 输入一棵二叉树的根节点，层序遍历这棵二叉树
    public void traverse(TreeNode root) {
        if (root == null) return;
        // 遍历过的每个节点的值加一
        System.out.println(root.val);
        traverse(root.left);
        traverse(root.right);
    }
    public void levelTraverse(TreeNode root) {
        if (root == null) return;
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        // 从上到下遍历二叉树的每一层
        while (!q.isEmpty()) {
            int sz = q.size();
            // 从左到右遍历每一层的每个节点
            for (int i = 0; i < sz; i++) {
                TreeNode cur = q.poll();
                // 将下一层节点放入队列
                if (cur.left != null) {
                    q.offer(cur.left);
                }
                if (cur.right != null) {
                    q.offer(cur.right);
                }
            }
        }
    }

    /**
     * 116. 填充每个节点的下一个右侧节点指针
     */
    public Node connect(Node root) {
        // 遍历解决  1.连接相邻节点 (直接采用层序遍历连接 需存储一个额外的节点) 2.dfs 多叉树
        // 邻节点子节点 相连 等同于多开个树杈  后序遍历连接即可
        if (root == null) {
            return null;
        }
        connectTraverse(root.left,root.right);
        return root;
    }
    private void connectTraverse(Node left, Node right) {
        if (left == null || right == null) {
            return;
        }
        left.next = right;
        connectTraverse(left.left, left.right);
        connectTraverse(right.left, right.right);
        // 子节点右节点  =>  领节点子节点左
        connectTraverse(left.right, right.left);
    }


    /**
     * 114. 二叉树展开为链表
     */
    public void flatten(TreeNode root) {
        if (root == null) {
            return;
        }

        flatten(root.left);
        flatten(root.right);
        // 修改节点
        TreeNode left = root.left;
        TreeNode right = root.right;

        // 右子树 变  左子树
        root.left = null;
        root.right = left;

        // 原右子树拼接到当前右子树
        TreeNode next = root;
        while (next.right != null) {
            next = next.right;
        }
        next.right = right;
    }

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        for (int i = 0; i < inorder.length; i++) {
            buildTreeIndexValues.put(inorder[i], i);
        }
        return buildTree(preorder, 0, preorder.length - 1, 0);
    }
    // 存中序遍历元素下标用于递归遍历范围
    private Map<Integer, Integer> buildTreeIndexValues = new HashMap<>();
    private TreeNode buildTree(int[] preorder, int preStart, int preEnd, int inStart) {
        if (preStart > preEnd) {
            return null;
        }
        int rootVal = preorder[preStart];
        TreeNode root = new TreeNode(rootVal);
        int index = buildTreeIndexValues.get(rootVal);
        int size = index - inStart;
        root.left =  buildTree(preorder, preStart + 1, preStart + size, inStart);
        root.right =  buildTree(preorder, preStart + size + 1 , preEnd, index + 1);
        return root;
    }
    /**
     * 236. 二叉树的最近公共祖先
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // 遍历左右节点, 2边都同时找到目标值 当前节点就是LCA
        // 一边是继续往查找的那边查找
        return search236(root, p.val, q.val);
    }

    private TreeNode search236(TreeNode root, int val1, int val2) {
        if (root == null) {
            return null;
        }
        if (root.val == val1 || root.val == val2) {
            return root;
        }
        TreeNode left = search236(root.left, val1, val2);
        TreeNode right = search236(root.right, val1, val2);
        if (left != null && right != null) {
            // 当前节点是 LCA 节点
            return root;
        }
        return left != null ? left : right;
    }
    /**
     * 235. 二叉树的最近公共祖先  2叉搜索树
     */
    public TreeNode lowestCommonAncestor2(TreeNode root, TreeNode p, TreeNode q) {
        // 遍历左右节点, 2边都同时找到目标值 当前节点就是LCA
        // 一边是继续往查找的那边查找
        int min = Math.min(p.val, q.val);
        int max = Math.max(p.val, q.val);
        return search235(root, min, max);
    }
    private TreeNode search235(TreeNode root, int min, int max) {
        if (root == null) {
            return null;
        }
        if (root.val == min || root.val == max) {
            return root;
        }
        if (root.val > max) {
            return search235(root.left, min, max);
        } else if (root.val < min) {
            return search235(root.right, min, max);
        } else {
            return root;
        }
    }
    /**
     * 654. 最大二叉树
     */
    public TreeNode constructMaximumBinaryTree(int[] nums) {
        // 分解问题 构造左右子树
        // 查询根节点 递归设置左右子树
        return foreach(nums , 0, nums.length - 1);
    }
    public TreeNode foreach(int[] nums, int start, int end) {
        if (start > end) {
            return null;
        }
        int maxValue = -1;
        int index = -1;
        for (int i = start; i <= end; i++) {
            if (nums[i] > maxValue)  {
                maxValue = nums[i];
                index = i;
            }
        }
        TreeNode root = new TreeNode(maxValue);
        root.left = foreach(nums, start, index - 1);
        root.right = foreach(nums, index + 1, end);
        return root;
    }
}


