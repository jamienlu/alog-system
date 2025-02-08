package com.asura.structure.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jamieLu
 * @create 2025-01-31
 */
public class TableGraph {
    int nodeLen;
    private static class Node {
        String data;
    }
    private static class Edge {
        int weight;
        int to;
        int value;

        public Edge(int weight, int to, int value) {
            this.weight = weight;
            this.to = to;
            this.value = value;
        }
    }
    private Map<Integer, Node> node = new HashMap<>();
    private Map<Integer, List<Edge>> graph = new HashMap<>();

    public TableGraph(int n) {
        nodeLen = n;
        for (int i = 0; i < n; i++) {
            node.put(i, new Node());
            graph.put(i, new ArrayList<>());
        }
    }
    public void addNode(int index) {
        nodeLen++;
        node.put(nodeLen, new Node());
    }
    public void setNode(int index, String msg) {
        if (index >= nodeLen) {
            throw new IllegalArgumentException("index is illegal");
        }
        node.get(index).data = msg;
    }
    public void addEdge(int from, int to, int weight,int value) {
        if (from >= nodeLen) {
            throw new IllegalArgumentException("index is illegal");
        }
        graph.get(from).add(new Edge(weight, to,value));
    }
    public void removeEdge(int from, int to) {
        if (from >= nodeLen) {
            throw new IllegalArgumentException("index is illegal");
        }
        List<Edge> edges = graph.get(from);
        for (int i = 0; i < edges.size(); i++) {
            if (edges.get(i).to == to) {
                edges.remove(i);
                break;
            }
        }
    }

    public boolean isNeighbor(int from, int to) {
        if (from >= nodeLen) {
            throw new IllegalArgumentException("index is illegal");
        }
        for (Edge e : graph.get(from)) {
            if (e.to == to) {
                return true;
            }
        }
        return false;
    }
    public List<Edge> neighbors(int from) {
        if (from >= nodeLen) {
            throw new IllegalArgumentException("index is illegal");
        }
        return graph.get(from);
    }
}
