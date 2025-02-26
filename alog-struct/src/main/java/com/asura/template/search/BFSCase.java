package com.asura.template.search;

import com.asura.structure.tree.TreeNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * @author jamieLu
 * @create 2025-02-11
 */
public class BFSCase {
    public void traverse(TreeNode root) {
        if (root == null) return;
        Queue<TreeNode> queue = new LinkedList();
        queue.add(root);
        // 层序遍历
        while (!queue.isEmpty()) {
            TreeNode cur = queue.poll();
            if (cur.left != null) {
                queue.add(cur.left);
            }
            if (cur.right != null) {
                queue.add(cur.right);
            }
        }
    }

    // bfs dijkstra算法###
    /**
     * 743. 网络延迟时间
     */
    public int networkDelayTime(int[][] times, int n, int k) {
        // dijkstra 求每个节点到其他节点的最短距离
        // 根据边构建图 从1开始
        List<int[]>[] graph = new LinkedList[n+1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new LinkedList<>();
        }
        for (int[] time : times) {
            int from = time[0];
            int to = time[1];
            int weight = time[2];
            graph[from].add(new int[]{to, weight});
        }
        // 构建起点到每个节点的路径集合
        int[] dist = new int[graph.length];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[k] = 0;
        // bfs遍历图 寻找到所有节点的最短路径
        Queue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        queue.offer(new int[]{k, 0});
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int index = cur[0];
            int weight = cur[1];
            if (weight > dist[index]) {
                continue;
            }
            for (int[] next : graph[index]) {
                int nextIndex = next[0];
                int nextWeight = next[1];
                if (dist[index] + nextWeight < dist[nextIndex]) {
                    dist[nextIndex] = dist[index] + nextWeight;
                    queue.offer(new int[]{nextIndex,  dist[nextIndex]});
                }
            }
        }
        // 只有每个节点都有路径才能经过所有节点
        // 最短路径中的最大值就是能经过所有节点
        int max = -1;
        for (int i = 1; i < dist.length; i++) {
            if (dist[i] == Integer.MAX_VALUE) {
                return -1;
            } else {
                max = Math.max(max, dist[i]);
            }
        }
        return max;
    }
    /**
     * 1631. 最小体力消耗路径
     */
    public int minimumEffortPath(int[][] heights) {
        // 转化为寻找表格内1个点到其他点的最短路径
        // 图相邻节点定义为上下左右
        // 路劲计算油2个节点的绝对值 和之前的绝对值的最大值决定

        int len = heights.length;
        int width = heights[0].length;
        // 构造最短路径表
        int[][] dist = new int[len][width];
        for (int i = 0; i < len; i++) {
            Arrays.fill(dist[i], Integer.MAX_VALUE);
        }
        dist[0][0] = 0;
        Queue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a[2]));
        queue.offer(new int[]{0, 0, 0});
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int x = cur[0];
            int y = cur[1];
            int weight = cur[2];
            // 当前路径长度大于最短路径跳过
            if (weight > dist[x][y]) {
                continue;
            }
            // 上下左右相邻节点获取 选择更短的路径
            for (int[] direction : new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}}) {
                int nextX = x + direction[0];
                int nextY = y + direction[1];
                // 边界检查
                if (nextX < 0 || nextX >= len || nextY < 0 || nextY >= width) {
                    continue;
                }
                // 最短路径计算公式
                int nextWeight =  Math.max(weight, Math.abs(heights[x][y] - heights[nextX][nextY]));
                // 更短的路径放入队列从他开始进行下一步路径计算
                if (nextWeight < dist[nextX][nextY]) {
                    dist[nextX][nextY] = nextWeight;
                    queue.offer(new int[]{nextX, nextY, dist[nextX][nextY]});
                }
            }
        }
        return dist[len - 1][width - 1];
    }
    /**
     * 1514. 概率最大的路径
     */
    public double maxProbability(int n, int[][] edges, double[] succProb, int start_node, int end_node) {
        //  转化为dijkstra最短距离
        // 求最小路径变为最大概率排序方法和比大小相反
        // 构造图
        List<State1514>[] graph = new LinkedList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new LinkedList<>();
        }
        for (int i = 0; i < edges.length; i++) {
            int from = edges[i][0];
            int to = edges[i][1];
            double weight = succProb[i];
            graph[from].add(new State1514(to, weight));
            graph[to].add(new State1514(from, weight));
        }
        double[] dict = new double[n];
        Arrays.fill(dict, -1.0);
        Queue<State1514> queue = new PriorityQueue<>(Comparator.comparingDouble(a -> -a.weight));
        queue.offer(new State1514(start_node, 1.0));
        // 查询下一个节点计算概率
        while (!queue.isEmpty()) {
            State1514 cur = queue.poll();
            double weight = cur.weight;
            // 到达指定节点退出
            if (cur.index == end_node) {
                return weight;
            }
            if (weight < dict[cur.index]) {
                continue;
            }
            for (State1514 nexNode : graph[cur.index]) {
                int nextIndex = nexNode.index;
                double nextWeight = nexNode.weight;
                // 选择概率更大的节点继续遍历
                if (weight * nexNode.weight > dict[nextIndex]) {
                    dict[nextIndex] = weight * nextWeight;
                    queue.offer(new State1514(nextIndex, dict[nextIndex]));
                }
            }
        }
        return 0.0;
    }
    private class State1514 {
        public int index;
        public double weight;
        public State1514(int index, double weight) {
            this.index = index;
            this.weight = weight;
        }
    }
    /**
     * 773. 滑动谜题
     */
    public int slidingPuzzle(int[][] board) {
        // 定位起点和终点
        // 起点每一次遍历都会寻找邻节点 层序遍历邻节点的结果直到到终点

        int m = board.length;
        int n = board[0].length;
        // 图转字符串用于灭一次遍历后识别重复路径
        String start = Arrays.stream(board).map(x -> Arrays.stream(x)
            .mapToObj(String::valueOf).reduce(String::concat).orElse(""))
            .reduce(String::concat).orElse("");
        // 构造邻接点
        int [][] neighbors = buildNeighbors(m, n);
        String end = "123450";
        if (start.equals(end)) {
            return 0;
        }
        Queue<String> queue = new LinkedList<>();
        queue.offer(start);
        Set<String> visited = new HashSet<>();
        int step = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                String cur = queue.poll();
                // 比对邻接点和结果
                if (end.equals(cur)) {
                    return step;
                }
                // 遍历所有邻接点
                for (String neighbor: convertNeighbors(cur, neighbors)) {
                    if (!visited.contains(neighbor)) {
                        queue.offer(neighbor);
                        visited.add(neighbor);
                    }
                }
            }
            step++;
        }
        return -1;
    }

    private int[][] buildNeighbors(int m, int n) {
        int [][] neighbors = new int[m * n][4];
        for (int i = 0; i < m * n; i++) {
            List<Integer> neighbor = new ArrayList<>();
            if (i % n != 0) {
                neighbor.add(i - 1);
            }
            if (i % n != n - 1) {
                neighbor.add(i + 1);
            }
            if (i - n >= 0) {
                neighbor.add(i - n);
            }
            if (i + n < m * n) {
                neighbor.add(i + n);
            }
            neighbors[i] = neighbor.stream().mapToInt(Integer::intValue).toArray();
        }
        return neighbors;
    }
    private List<String> convertNeighbors(String board, int[][] neighbors) {
        List<String> result = new ArrayList<>();
        int index = board.indexOf('0');
        for (int i = 0; i < neighbors[index].length; i++) {
            StringBuilder sb = new StringBuilder(board);
            sb.setCharAt(index, board.charAt(neighbors[index][i]));
            sb.setCharAt(neighbors[index][i], '0');
            result.add(sb.toString());
        }

        return result;
    }

    public int openLock(String[] deadends, String target) {
        Set<String> deads = new HashSet<>(Arrays.asList(deadends));
        if (deads.contains("0000")) {
            return -1;
        }
        if (target.equals("0000")) {
            return 0;
        }
        // 构造起点和终点集合
        Set<String> start = new HashSet<>();
        Set<String> end = new HashSet<>();
        start.add("0000");
        end.add(target);
        Set<String> visited = new HashSet<>();
        int step = 0;
        while (!start.isEmpty() && !end.isEmpty()) {
            step++;
            Set<String> temp = new HashSet<>();
            // 没走一步 查询起点的下一部是否在终点
            for (String cur : start) {
                for (String neighbor : neighbors(cur)) {
                    if (end.contains(neighbor)) {
                        return step;
                    }
                    if (visited.contains(neighbor) || deads.contains(neighbor)) {
                        continue;
                    }
                    temp.add(neighbor);
                    visited.add(neighbor);
                }
            }
            // 当前节点转为下一部的所有节点
            start = temp;
            // 从少的开始遍历减少遍历时间
            if (start.size() > end.size()) {
                Set<String> temp2 = start;
                start = end;
                end = temp2;
            }
        }
        return -1;
    }
    private List<String> neighbors(String target) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < target.length(); i++) {
            result.add(up(target,i));
            result.add(down(target,i));
        }
        return result;
    }
    private String up(String s, int j) {
        char[] ch = s.toCharArray();
        if (ch[j] == '9') {
            ch[j] = '0';
        } else {
            ch[j] += 1;
        }

        return new String(ch);
    }
    // 将 s[i] 向下拨动一次
    private String down(String s, int j) {
        char[] ch = s.toCharArray();
        if (ch[j] == '0') {
            ch[j] = '9';
        } else {
            ch[j] -= 1;
        }

        return new String(ch);
    }

    public static int minMutation(String startGene, String endGene, String[] bank) {
        Set<String> bankSet = new HashSet<>(Arrays.asList(bank));
        if (!bankSet.contains(endGene)) {
            return -1;
        }
        char[] demo = new char[]{'A','C','G','T'};
        Set<String> startSet = new HashSet<>();
        startSet.add(startGene);
        Set<String> endSet = new HashSet<>();
        endSet.add(endGene);
        Set<String> visited = new HashSet<>();
        int step = 0;
        while (!startSet.isEmpty() && !endSet.isEmpty()) {
            Set<String> temp = new HashSet<>();
            for (String cur : startSet) {
                for (int i = 0; i < cur.length(); i++) {
                    char[] chars = cur.toCharArray();
                    for (char change : demo) {
                        chars[i] = change;
                        String target = new String(chars);
                        if (endSet.contains(cur)) {
                            return step;
                        }
                        if (bankSet.contains(target) && !visited.contains(target)) {
                            temp.add(target);
                            visited.add(target);
                        }
                    }

                }
            }
            startSet = temp;
            if (startSet.size() > endSet.size()) {
                temp = endSet;
                endSet = startSet;
                startSet = temp;
            }
            step++;
        }
        return -1;
    }

    public static void main(String[] args) {
        minMutation("AAAAAAAA","CCCCCCCC", new String[]{"AAAAAAAA","AAAAAAAC","AAAAAACC","AAAAACCC","AAAACCCC","AACACCCC","ACCACCCC","ACCCCCCC","CCCCCCCA","CCCCCCCC"});

    }
}
