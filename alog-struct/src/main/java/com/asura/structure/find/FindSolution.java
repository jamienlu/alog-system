package com.asura.structure.find;

import com.asura.structure.tree.TreeNode;

public class FindSolution {
    /**
     * 给出二叉 搜索 树的根节点，该树的节点值各不相同，请你将其转换为累加树（Greater Sum Tree），使每个节点 node的新值等于原树中大于或等于node.val的值之和。
     *
     * 提醒一下，二叉搜索树满足下列约束条件：
     *
     * 节点的左子树仅包含键 小于 节点键的节点。
     * 节点的右子树仅包含键 大于 节点键的节点。
     * 左右子树也必须是二叉搜索树。
     *
     *
     * @param root [4,1,6,0,2,5,7,null,null,null,3,null,null,null,8]
     * @return [30,36,21,36,35,26,15,null,null,null,33,null,null,null,8]
     */
    public TreeNode convertBST(TreeNode root) {
        dfs(root, 0);
        return root;
    }

    private int dfs(TreeNode root, int parentVal) {
        if (root == null) {
            return parentVal;
        }
        root.val += dfs(root.right, parentVal);
        return dfs(root.left, root.val);
    }

    /**
     * 给你一个 m x n 的矩阵 board ，由若干字符 'X' 和 'O' ，找到所有被 'X' 围绕的区域，并将这些区域里所有的 'O' 用 'X' 填充
     * @param board [["X","X","X","X"],["X","O","O","X"],["X","X","O","X"],["X","O","X","X"]]
     * [["X","X","X","X"],["X","X","X","X"],["X","X","X","X"],["X","O","X","X"]]
     */
    public void solve(char[][] board) {
        if (board == null || board.length == 0) {
            return;
        }
        int width = board.length;
        int high = board[0].length;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < high; j++) {
                boolean isEdge = i == 0 || j == 0 || i == width - 1 || j == high - 1;
                if (isEdge && board[i][j] == 'O') {
                    dfs(board, i, j);
                }
            }
        }

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < high; j++) {
                if (board[i][j] == 'O') {
                    board[i][j] = 'X';
                }
                if (board[i][j] == '*') {
                    board[i][j] = 'O';
                }
            }
        }
    }

    private void dfs(char[][] board, int i, int j) {
        if (i < 0 || j < 0 || i >= board.length  || j >= board[0].length
            || board[i][j] == 'X' || board[i][j] == '*') {
            return;
        }
        board[i][j] = '*';
        // 上
        dfs(board, i - 1, j);
        // 下
        dfs(board, i + 1, j);
        // 左
        dfs(board, i, j - 1);
        // 右
        dfs(board, i, j + 1);
    }

    /**
     *
     * 传送带上的包裹必须在 days 天内从一个港口运送到另一个港口。
     *
     * 传送带上的第 i个包裹的重量为weights[i]。每一天，我们都会按给出重量（weights）的顺序往传送带上装载包裹。我们装载的重量不会超过船的最大运载重量。
     *
     * 返回能在 days 天内将传送带上的所有包裹送达的船的最低运载能力。
     *
     * @param weights [1,2,3,4,5,6,7,8,9,10]
     * @param days 5
     * @return 15
     */
    public int shipWithinDays(int[] weights, int days) {
        // 取下界
        int left = 0;
        int right = 0;
        for (int weight : weights) {
            left = Math.max(left, weight);
            right += weight;
        }
        while (left < right) {
            int mid = left + ((right -left) >> 1);
            int need = getNeedDay(weights, mid);
            if (need <= days) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return right;
    }

    private int getNeedDay(int[] weights, int mid) {
        int curWeight = 0;
        int need = 1;
        for (int weight : weights) {
            if (curWeight + weight > mid) {
                need++;
                curWeight = 0;
            }
            curWeight += weight;
        }
        return need;
    }

    /**
     * 这里有N堆香蕉，第 i 堆中有piles[i]根香蕉。警卫已经离开了，将在H小时后回来。
     *
     * 珂珂可以决定她吃香蕉的速度K（单位：根/小时）。每个小时，她将会选择一堆香蕉，从中吃掉 K 根。如果这堆香蕉少于 K 根，她将吃掉这堆的所有香蕉，然后这一小时内不会再吃更多的香蕉。
     *
     * 珂珂喜欢慢慢吃，但仍然想在警卫回来前吃掉所有的香蕉。
     *
     * 返回她可以在 H 小时内吃掉所有香蕉的最小速度 K（K 为整数
     *
     * @param piles [30,11,23,4,20]
     * @param h 5
     * @return  30
     */
    public int minEatingSpeed(int[] piles, int h) {
        int max = 1;
        for (int pile : piles) {
            max = Math.max(max, pile);
        }
        int left = 1;
        int right = max;
        while (left < right) {
            int mid = left + ((right -left) >> 1);
            // 吃完了
            if (calculateTime(piles, mid) <= h) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return right;

    }

    private int calculateTime(int[] piles, int speed) {
        int time = 0;
        for (int pile : piles) {
            time += (pile + speed - 1) / speed;
        }
        return time;
    }





}
