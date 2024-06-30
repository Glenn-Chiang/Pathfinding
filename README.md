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

## Download
1. Download the zip file from the [latest release](https://github.com/Glenn-Chiang/pathfinding-visualizer/releases)
2. After extracting all files, the application can be run with `pathfinding.exe`

## Build from source
1. Clone this repo and navigate to the project root directory
2. Run `./gradlew desktop:dist`
3. The above command will generate a JAR file in the `desktop/build/libs/ folder`
4. Run the JAR file by double-clicking it or with the command `java -jar path/to/jar-file.jar`

## Concepts
### Pathfinding algorithms
Pathfinding algorithms generally implement the following logic:
- Maintain a set of open nodes (nodes to be explored)
- Maintain a set of closed nodes (nodes that have been explored)
- Add the start node to the set of open nodes
- While there are open nodes:
  - Use some kind of cost function to select the best node from the set of open nodes
  - Set the selected node as the current node and add it to the set of closed nodes
  - If the current node is the target node, the path has been found
  - Otherwise, iterate over the neighbors of the current node. For each neighbor:
    - If neighbor is closed, skip it
    - Find the distance of the neighbor from the start node based on the distance of the current node from the start node
    - If this distance is less than the neighbor's previous recorded distance from the start node:
      - Update its distance from the start node to reflect the updated path
    - If the neighbor is not yet added to the set of open nodes or if the neighbor's distance from its start node was updated:
      - Set the current node as the neighbor's parent to reflect the updated path
      - Add the neighbor to the set of open nodes, if not already added

The key difference between the pathfinding algorithms lies in the heuristic function they use to evaluate the "cost" of a node, where a lower cost indicates a more optimal path.

### Greedy BFS
The Greedy best-first-search algorithm always selects the neighbor with the shortest heuristic distance to the target node. This approach allows it to often find paths faster than the other algorithms, but the path found may not be the optimal one.

### Dijkstra's algorithm
Dijkstra's algorithm always selects the neighbor with the shortest path so far from the start node. This approach guarantees that the optimal path will be found, but it tends to perform more slowly than the other algorithms as it explores every neighbor at each iteration.

### A Star
The A Star algorithm determines the cost of a node based on the sum of its heuristic distance to the target node and the distance of its shortest path from the start node. By combining the approaches of the Greedy algorithm and Dijkstra's algorithm, the A Star algorithm can find the optimal path (which Greedy may fail to) in a shorter time (compared to Dijkstra's algorithm). 

