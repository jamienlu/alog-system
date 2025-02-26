package com.asura.structure.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author jamieLu
 * @create 2025-01-31
 */
public class TableGraph {
    public int nodeLen;


    private Map<Integer, TableNode> nodes = new HashMap<>();
    private Map<Integer, List<TableEdge>> graph = new HashMap<>();

    public TableGraph(int n) {
        nodeLen = n;
        for (int i = 0; i < n; i++) {
            nodes.put(i, new TableNode(i,0));
            graph.put(i, new ArrayList<>());
        }
    }
    public void addNode(TableNode node) {
        nodeLen++;
        nodes.put(nodeLen, node);
    }
    public void setNode(int index, TableNode node) {
        if (index >= nodeLen) {
            throw new IllegalArgumentException("index is illegal");
        }
        nodes.put(index,node);
    }
    public void addEdge(int from, int to, int weight,int value) {
        if (from >= nodeLen) {
            throw new IllegalArgumentException("index is illegal");
        }
        graph.get(from).add(new TableEdge(weight, to,value));
    }
    public void removeEdge(int from, int to) {
        if (from >= nodeLen) {
            throw new IllegalArgumentException("index is illegal");
        }
        List<TableEdge> edges = graph.get(from);
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
        for (TableEdge e : graph.get(from)) {
            if (e.to == to) {
                return true;
            }
        }
        return false;
    }
    public List<TableNode> neighbors(int from) {
        if (from >= nodeLen) {
            throw new IllegalArgumentException("index is illegal");
        }
        return graph.get(from).stream().map(x -> nodes.get(x.to)).collect(Collectors.toList());
    }
}
