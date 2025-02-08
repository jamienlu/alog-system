package com.asura.structure.graph;

/**
 * @author jamieLu
 * @create 2025-01-31
 */
public class VecorGraph {
    int vertex;
    private int[][] matrix;
    public static class Edge {
        int to;
        int weight;

        public Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    public VecorGraph(int n) {
        matrix = new int[n][n];
        vertex = n;
    }

    public void addEdge(int from, int to, int weight) {
        if (from >= vertex || to >= vertex) {
            throw new IllegalArgumentException("index is illegal");
        }
        matrix[from][to] = weight;
    }
    public void removeEdge(int from, int to) {
        if (from >= vertex || to >= vertex) {
            throw new IllegalArgumentException("index is illegal");
        }
        matrix[from][to] = 0;
    }

    public boolean isNeibor(int from, int to) {
        return matrix[from][to] != 0;
    }

    public int[] neibors(int from) {
        if (from >= vertex) {
            throw new IllegalArgumentException("index is illegal");
        }
        return matrix[from];
    }

}
