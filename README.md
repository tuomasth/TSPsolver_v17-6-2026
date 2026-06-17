Recommended Java JDK version: 17.0.12 or newer.
This TSPsolver is an updated version of "https://github.com/tuomasth/TSPsolver_v14-3-2017".

---

F2 NNH:                   Nearest neighbour as the simplest heuristic there exists.

F3 2MST:                  Using minimum spanning tree's doubled edges and Euler tour.

F4 CHH:                   Convex hull around everything and connect the (closest) inner nodes one by one.

F5 SOM-NN-OPT:            Calculate the convex hull so its nodes (or edge centroids) can be the input nodes and clusters, then
                          the inner nodes are movable neurons that perform the Kohonen Self-Organizing Map algorithm, finally
                          each cluster performs the NNH which chains everything and creates the Hamiltonian circuit. 
                          Extra nodes/clusters are possible. Has opt moves.

F6 Super-OPT:      	      F2 for a couple of times, F4 once, F5 once, choose the best and try to improve with quick opts.

F1:                       (About the Java application.)
