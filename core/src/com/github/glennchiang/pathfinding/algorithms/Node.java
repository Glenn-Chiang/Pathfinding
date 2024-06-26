package com.github.glennchiang.pathfinding.algorithms;

public class Node {
    public final int row;
    public final int col;
    Node parent;
    int distanceFromStart;

    public Node(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int distanceFromStart() {
        return distanceFromStart;
    }
}
