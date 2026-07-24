Recommended Java JDK version: 17.0.12 or newer.
This TSPsolver is an updated version of "https://github.com/tuomasth/TSPsolver_v14-3-2017".

Repository version: 17.6.2026, its newest version is 2.4 (24.7.2026).

---

F2 NNH:                   Nearest neighbour as the simplest heuristic there exists.

F3 2MST:                  Using minimum spanning tree's doubled edges and Euler tour.

F4 CHH:                   Convex hull around everything and connect the (closest) inner nodes one by one.

F5 SOM-NN-OPT:            Calculate the convex hull so its nodes (or edge centroids) can be the input nodes and clusters, then
                          the inner nodes are movable neurons that perform the Kohonen Self-Organizing Map algorithm, finally
                          each cluster performs the NNH which chains everything and creates the Hamiltonian circuit. 
                          Extra input nodes/clusters are possible. Has opt moves. Only the node copies move temporarily, not the originals.

F6 Super-OPT:      	      F2 for a couple of times, F4 once, F5 once, choose the best and try to improve with quick opts.

F1:                       (About the Java application.)

---

Java must be installed, obviously, before executing the JAR file in the "dist" folder. "https://www.oracle.com/java/technologies/downloads" (21.6.2026)

For editing the application, Apache NetBeans is recommended: "https://netbeans.apache.org/front/main/index.html" (21.6.2026)
