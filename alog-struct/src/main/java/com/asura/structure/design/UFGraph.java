package com.asura.structure.design;

/**
 * @author jamieLu
 * @create 2025-02-12
 */
public class UFGraph {
    private int count;

    private int[] parent;

    public UFGraph(int count) {
        this.count = count;
        parent = new int[count];
        for (int i = 0; i < count; i++) {
            parent[i] = i;
        }
    }

    public void union(int p, int q) {
        int pRoot = find(p);
        int qRoot = find(q);
        if (pRoot == qRoot) {
            return;
        }
        parent[pRoot] = qRoot;
        count--;
    }

    public boolean connect(int p, int q) {
        int pRoot = find(p);
        int qRoot = find(q);
        return pRoot == qRoot;
    }

    public int find(int p) {
        if (p != parent[p]) {
            // 压缩树高 到1
            parent[p] = find(parent[p]);
        }
        return parent[p];
    }

    public int count() {
        return count;
    }
}
