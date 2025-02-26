package com.asura.structure.graph;

import com.asura.structure.design.UFGraph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author jamieLu
 * @create 2025-02-11
 */
public class GraphCase {
    /**
     * 207. 课程表
     */
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        // 先修课程等于 从先修指向后修课程的边
        // 制作有向图  遍历 成环表示条件不满足无法修完
        List<Integer>[] graph =  new LinkedList[numCourses];
        path = new boolean[numCourses];
        visited = new boolean[numCourses];
        for (int i = 0; i < numCourses; i++) {
            // 有向边链表
            graph[i] = new LinkedList<>();
        }
        for (int i = 0; i < prerequisites.length; i++) {
            // 先修右边
            graph[prerequisites[i][1]].add(prerequisites[i][0]);
        }
        for (int i = 0; i < numCourses; i++) {
            checkCycle(graph,i);
        }
        return !hasCycle;
    }
    private boolean[] path;
    private boolean[] visited;
    private boolean hasCycle;
    private void checkCycle(List<Integer>[] graph, int index) {
        if (hasCycle) {
            return;
        }
        if (path[index]) {
            hasCycle = true;
            return;
        }
        // 该起点已经遍历过 未找到环跳过
        if (visited[index]) {
            return;
        }
        path[index] = true;
        visited[index] = true;
        for (int edge : graph[index]) {
            checkCycle(graph, edge);
        }
        path[index] = false;
    }

    /**
     * 210. 课程表 II
     */
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        // 图拓扑排序  记录后置课程需要多少前置课程 节点入度
        // 入度为0是根节点可以往下遍历 然后依次减少遍历节点的入度
        // 入度减为0就可以当作根节点继续遍历
        // 遍历头节点计数，当该路径遍历完 节点经过路径和预定长度一致即成功
        List<Integer>[] graph =  new LinkedList[numCourses];
        for (int i = 0; i < numCourses; i++) {
            graph[i] = new LinkedList<>();
        }
        // 生成图和 节点的入度
        int[] indegree = new int[numCourses];
        for (int i = 0; i < prerequisites.length; i++) {
            graph[prerequisites[i][1]].add(prerequisites[i][0]);
            indegree[prerequisites[i][0]]++;
        }
        LinkedList<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            // 头节点
            if (indegree[i] == 0) {
                queue.offer(i);
            }
        }
        int[] res = new int[numCourses];
        int count = 0;
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            res[count] = cur;
            count++;
            for (int next: graph[cur]) {
                indegree[next]--;
                if (indegree[next] == 0) {
                    queue.offer(next);
                }
            }
        }
        if (count == numCourses) {
            return res;
        } else {
            return new int[]{};
        }

    }
    /**
     * 261. 以图判树
     */
    public boolean isBipartite(int[][] graph) {
        // 2分图
        // 遍历每个节点 存储节点颜色和节点访问状态
        // 染色完毕没有出现相邻同色ok

        color261 = new boolean[graph.length];
        visited261 = new boolean[graph.length];
        colorOk = true;
        for (int i = 0; i < graph.length; i++) {
            if (!visited261[i]) {
                dfs261(graph, i);
            }
        }
        return colorOk;
    }
    private boolean[] color261;
    private boolean[] visited261;
    private boolean colorOk;
    private void dfs261(int[][] graph, int c) {
        if (!colorOk) {
            return;
        }
        visited261[c] = true;
        for (int next : graph[c]) {
            if (!visited261[next]) {
                color261[next] = !color261[c];
                dfs261(graph, next);
            } else {
                if (color261[next] == color261[c]) {
                    colorOk = false;
                    return;
                }
            }
        }
    }

    /**
     * 886. 可能的二分法
     */
    public boolean possibleBipartition(int n, int[][] dislikes) {
        // 相当于二分图给了边的信息
        List<Integer>[] graphs = buildGraph(n, dislikes);
        color886 = new boolean[n + 1];
        visited886 = new boolean[n + 1];
        canSplit = true;
        for (int i = 1; i < graphs.length; i++) {
            if (!visited261[i]) {
                dfs886(graphs, i);
            }
        }
        return canSplit;
    }
    private boolean[] color886;
    private boolean[] visited886;
    private boolean canSplit;
    private void dfs886(List<Integer>[] graph, int c) {
        if (!canSplit) {
            return;
        }
        visited886[c] = true;
        for (int next : graph[c]) {
            if (!visited886[next]) {
                color886[next] = !color886[c];
                dfs886(graph, next);
            } else {
                if (color886[next] == color886[c]) {
                    canSplit = false;
                    return;
                }
            }
        }
    }
    private List<Integer>[] buildGraph(int n, int[][] dislikes) {
        List<Integer>[] graph = new LinkedList[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new LinkedList<>();
        }
        // 无向图
        for (int i = 0; i < dislikes.length; i++) {
            graph[dislikes[i][0]].add(dislikes[i][1]);
            graph[dislikes[i][1]].add(dislikes[i][0]);
        }
        return graph;
    }

    /**
     *  990. 等式方程的可满足性
     */
    public boolean equationsPossible(String[] equations) {
        // UF  等式连通不等式 如果也是连通的就是不满足
        UFGraph ufGraph = new UFGraph(26);
        for (String equation : equations) {
            if (equation.charAt(1) == '=') {
                ufGraph.union(equation.charAt(0) - 'a', equation.charAt(3) - 'a');
            }
        }
        for (String equation : equations) {
            if (equation.charAt(1) == '!') {
                if (ufGraph.connect(equation.charAt(0) - 'a', equation.charAt(3) - 'a')){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 684 冗余连接
     */
    public int[] findRedundantConnection(int[][] edges) {
        // 任意2个节点之间只有1条路径
        int n = edges.length;
        UFGraph ufGraph = new UFGraph(n + 1);
        for (int i= 0; i < n; i++) {
            int p = edges[i][0];
            int q = edges[i][1];
            if (ufGraph.connect(p,q)) {
                return edges[i];
            } else {
                ufGraph.union(p,q);
            }
        }
        return new int[0];
    }

    public int minCostConnectPoints(int[][] points) {
        // 构造所有点任意连接的边
        // 根据边的长度为权重排序
        // 依次连接 待全部连通完的距离就是连通最短路径和
        int len = points.length;
        List<int[]> edges = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                int x1 = points[i][0];
                int y1 = points[i][1];
                int x2 = points[j][0];
                int y2 = points[j][1];
                edges.add(new int[]{i,j, Math.abs(x2-x1) + Math.abs(y2- y1)});
            }
        }
        int sum = 0;
        edges.sort(Comparator.comparingInt(v -> v[2]));
        UFGraph ufGraph = new UFGraph(len);
        for (int[] edge: edges) {
            if (ufGraph.connect(edge[0], edge[1])) {
                continue;
            }
            sum += edge[2];
            ufGraph.union(edge[0], edge[1]);
        }
        return sum;
    }


}
