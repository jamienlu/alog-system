package com.asura.alog.structure.tree;

public class TreeSolution {
	/**
	 * 二叉树展开为右树 - 从小到大
	 *
	 * @param root
	 */
	public static void flatten(TreeNode root) {
		/**
		 * 1.
		 *
		 *
		 */
		TreeNode curr = root;
		while (curr != null) {
			if (curr.left != null) {
				TreeNode next = curr.left;
				TreeNode predecessor = next;
				while (predecessor.right != null) {
					predecessor = predecessor.right;
				}
				// 右子树放在最左右子数
				predecessor.right = curr.right;
				curr.left = null;
				// 向右伸展，右节点接左节点
				curr.right = next;
			}
			// 当前节点跳到之前的左节点重复操作
			curr = curr.right;
		}
	}
}
