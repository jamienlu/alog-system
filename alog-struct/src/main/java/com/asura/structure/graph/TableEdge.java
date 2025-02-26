package com.asura.structure.graph;

/**
 * @author jamieLu
 * @create 2025-02-13
 */
public class TableEdge {
   public int weight;
    public int to;
    public int value;

    public TableEdge(int weight, int to, int value) {
        this.weight = weight;
        this.to = to;
        this.value = value;
    }
}
