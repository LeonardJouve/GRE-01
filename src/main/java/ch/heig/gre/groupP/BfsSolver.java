package ch.heig.gre.groupP;

import ch.heig.gre.graph.Graph;
import ch.heig.gre.graph.VertexLabelling;
import ch.heig.gre.maze.MazeSolver;

import java.util.*;

public final class BfsSolver implements MazeSolver {
  @Override
  public List<Integer> solve(Graph graph, int source, int destination, VertexLabelling<Integer> treatments) {
    if (graph == null || treatments == null) throw new NullPointerException("Graph or Treatments missing");
    if (!graph.vertexExists(source) || !graph.vertexExists(destination)) throw new IllegalArgumentException("Source or Destination vertex does not exist");

    // Instantiate a list of predecessors distances initialized with the amount of vertices elements assigned to null
    List<Integer> predecessors = new ArrayList<>(Collections.nCopies(graph.nbVertices(), null));
    Queue<Integer> queue = new LinkedList<>();
    queue.add(source);

    while (!queue.isEmpty()) {
      int currentVertex = queue.poll();

      // Either get vertex distance from start using predecessor distance or fallback to 0 for the first vertex
      int distance = 0;
      Integer predecessor = predecessors.get(currentVertex);
      if (predecessor != null) {
        distance = treatments.getLabel(predecessor) + 1;
      }

      // Update distance to the current vertex
      treatments.setLabel(currentVertex, distance);

      // Destination is reached
      if (currentVertex == destination) {
        // Backtrace path found until source
        List<Integer> path = new LinkedList<>();
        while (currentVertex != source) {
          path.add(currentVertex);
          currentVertex = predecessors.get(currentVertex);
        }
        path.add(source);

        return path;
      }

      // Get neighbors of current vertex
      List<Integer> neighbors = graph.neighbors(currentVertex);
      // Filter treated vertices
      neighbors.removeIf((neighbor) -> neighbor == source || predecessors.get(neighbor) != null);

      for (Integer neighbor : neighbors) {
        // Set neighbor predecessor
        predecessors.set(neighbor, currentVertex);
        // Enqueue neighbor
        queue.add(neighbor);
      }
    }

    throw new IllegalArgumentException("Could not reach destination from given source");
  }
}
