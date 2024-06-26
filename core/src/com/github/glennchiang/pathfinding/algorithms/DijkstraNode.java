package com.github.glennchiang.pathfinding.algorithms;

public class DijkstraNode implements Node {
    public final int row;
    public final int col;
    public Node parent;
    public int cost; // Distance from start node

    public DijkstraNode(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public int getCost() {
        return cost;
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

}
