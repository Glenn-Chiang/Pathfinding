package com.github.glennchiang.pathfinding;

public enum DistanceMetric {
    EUCLIDEAN() {
        @Override
        public int[][] getDirections() {
            return new int[][]{
                    {-1, -1}, {-1, 0}, {-1, 1},
                    {0, -1}, {0, 0}, {0, 1},
                    {1, -1}, {1, 0}, {1, 1}
            };
        }

        @Override
        public int getDistance(int rowA, int colA, int rowB, int colB) {
            return (int) (10 * Math.sqrt(Math.pow(rowA - rowB, 2) + Math.pow(colA - colB, 2)));
        }
    },
    MANHATTAN() {
        @Override
        public int[][] getDirections() {
            return new int[][]{{-1, 0}, {0, -1}, {0, 0}, {0, 1}, {1, 0},};
        }

        @Override
        public int getDistance(int rowA, int colA, int rowB, int colB) {
            return Math.abs(rowA - rowB) + Math.abs(colA - colB);
        }
    };

    public final int[][] directions;

    DistanceMetric() {
        directions = getDirections();
    }

    abstract int[][] getDirections();

    public abstract int getDistance(int rowA, int colA, int rowB, int colB);
}
