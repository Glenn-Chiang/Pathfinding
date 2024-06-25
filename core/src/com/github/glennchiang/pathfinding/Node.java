package com.github.glennchiang.pathfinding;

public class Node {
    public final int row;
    public final int col;
    public Node parent;
    public int gCost; // Distance from start node
    public int hCost; // Distance from target node

    public Node(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int fCost() {
        return gCost + hCost;
    }
}
