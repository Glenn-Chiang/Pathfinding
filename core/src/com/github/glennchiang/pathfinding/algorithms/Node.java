package com.github.glennchiang.pathfinding.algorithms;

public interface Node {
    int getRow();
    int getCol();
    Node getParent();
    void setParent(Node parent);
    int getCost();
}
