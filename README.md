# Ex2

By: Eilon Barashi, Harel Giladi

# The project

In this project we have implemented a Directed Weighted Graph,and a GUI that represent a loaded graphs using a JSON file and algorithms. 
We were asked to be able to generate large graphs up to 10,000 nodes and  to be able to run algorithms efficiently on our graph.

# DWGraph class 
This class implement an directional weighted graph using HashMap data structure. 
It support a large number of nodes (over 100,000). 

DWGraph data structure:

nodes- HashMap data structure that represents the group of nodes of this graph.

edges- HashMap data structure that represents for each node a group of directed edges from him in this graph.

# DWGAlgo class 
This class represents a directed weighted Graph and implement algorithms.

**_Algorithms_:**


* isConnected --> Returns true if and only if  there is a valid path from each node to each other node

  see: https://en.wikipedia.org/wiki/Shortest_path_problem

* shortestPathDist --> Computes the the shortest path between src to dest 

  see: https://en.wikipedia.org/wiki/Shortest_path_problem
  
* shortestPath --> Computes the the shortest path between src to dest - as an ordered List of nodes: src--> n1-->n2-->...dest
  
  see: https://en.wikipedia.org/wiki/Shortest_path_problem
  
* center --> Finds the NodeData which minimizes the max distance to all the other nodes.
  
  see: https://en.wikipedia.org/wiki/Graph_center 
  
* tsp --> Computes a list of consecutive nodes which go over all the nodes in cities. the sum of the weights is the "cost" of the solution - the lower the better.
 
  see: https://en.wikipedia.org/wiki/Travelling_salesman_problem

 # Helper algo
+ BFS - an algorithm for traversing or searching graph data structures. The method checks whether or not the graph is strongly linked, in other words it checks whether there is a path between node to each other node. 
see: https://en.wikipedia.org/wiki/Breadth-first_search

+ Dijkstra's algorithm - an algorithm for finding the shortest paths between nodes in a graph. The method uses the weight of each node to update his current distance from the source node.
see: https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm

# The Algorythmes' Results 


| Json         | isConnected    | center        | tsp         |
| :---         |     :---:      |          ---: |        ---: |
| G1           | git status     | git status    |             |
| G2           | git diff       | git diff      |             |
| G3           |                |               |             |
| 1000         |                |               |             |
| 10000        |                |               |             |
