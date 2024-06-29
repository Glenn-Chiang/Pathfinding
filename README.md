# pathfinding-visualizer
A desktop program that generates visualizations of classic pathfinding algorithms

https://github.com/Glenn-Chiang/pathfinding-visualizer/assets/122365492/53057283-adbb-467e-bf71-f00a11f714c9

## Features
The program allows the user to generate visualizations of pathfinding algorithms as they navigate a randomly generated 2D grid.

### Algorithms
The user can select from the following algorithms to run on the grid:
- A Star
- Greedy best-first-search
- Dijkstra's algorithm

### Grid generation
The grid can be regenerated to randomly place obstacles as well as the start and target cells  
Start and target cells can also be manually set by the user by left-clicking and right-clicking respectively

### Distance metrics
The user can select between 2 distance metrics to control the directions in which paths can be taken
- Manhattan: Paths can only move up, down, left and right
- Euclidean: Paths can move up, down, left, right and diagonally
  
### Run, pause, reset
The user can seamlessly run, pause and reset the visualizer at any time by toggling the corresponding buttons

### Metrics display
As the selected algorithm runs, 2 key metrics are updated in real time:
- Distance: The length of the path between the start node and the current node
- Visited: The number of nodes explored by the algorithm so far

A lower distance and visited count indicate a more optimal and efficient path.

## Concepts
### Pathfinding algorithms
### A Star
### Greedy
### Dijkstra
### Random walker (grid generation)

## Installation
### JAR file
1. Clone this repo and navigate to the project root directory
2. Run `./gradlew desktop:dist`
3. The above command will generate a JAR file in the `desktop/build/libs/ folder`
4. Run the JAR file by double-clicking it or with the command java -jar `path/to/jar-file.jar`
