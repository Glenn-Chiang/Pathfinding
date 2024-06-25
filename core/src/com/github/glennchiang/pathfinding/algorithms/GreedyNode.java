package com.github.glennchiang.pathfinding.algorithms;

public class GreedyNode implements Node {
    public final int row;
    public final int col;
    public Node parent;
    public int gCost; // Distance from start node
    public int hCost; // Distance from target node

    public GreedyNode(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public int getRow() {
        return row;
    }

    @Override
    public int getCol() {
        return col;
    }

    @Override
    public Node getParent() {
        return parent;
    }

    @Override
    public void setParent(Node parent) {
        this.parent = parent;
    }

    public int fCost() {
        return gCost + hCost;
    }
}
