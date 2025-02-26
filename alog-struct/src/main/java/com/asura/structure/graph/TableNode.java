package com.asura.structure.graph;

/**
 * @author jamieLu
 * @create 2025-02-13
 */
public class TableNode {
    public int index;
    public int distFromStart;
    public String data;

    public TableNode(int index, int distFromStart) {
        this.index = index;
        this.distFromStart = distFromStart;
    }
}
