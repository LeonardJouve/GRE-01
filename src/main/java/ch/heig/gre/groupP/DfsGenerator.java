package ch.heig.gre.groupP;

import ch.heig.gre.maze.MazeBuilder;
import ch.heig.gre.maze.MazeGenerator;
import ch.heig.gre.maze.Progression;

import java.util.*;

public final class DfsGenerator implements MazeGenerator {
  @Override
  public void generate(MazeBuilder builder, int from) {
    if (builder == null) throw new NullPointerException("Builder missing");
    if (!builder.topology().vertexExists(from)) throw new IllegalArgumentException("Non-existent source node");

    // initiate a random number generator
    Random rand = new Random();
    Stack<Integer> stack = new Stack<>();
    List<Boolean> visitedVerticies = new ArrayList<>(Collections.nCopies(builder.topology().nbVertices(), false));
    stack.push(from);
    visitedVerticies.set(from, true);

    while (!stack.isEmpty()) {
      Integer currentVertex = stack.peek();
      builder.progressions().setLabel(currentVertex, Progression.PROCESSING);
      List<Integer> neighbours = builder.topology().neighbors(currentVertex);
      // filtré
      neighbours.removeIf(visitedVerticies::get);

      if (neighbours.isEmpty()) {
        stack.pop();
        builder.progressions().setLabel(currentVertex, Progression.PROCESSED);
        continue;
      }

      Integer neighbour = neighbours.get(rand.nextInt(neighbours.size()));
      stack.push(neighbour);
      visitedVerticies.set(neighbour, true);
      builder.removeWall(currentVertex, neighbour);
    }
  }

  @Override
  public boolean requireWalls() {
    // We choose to destroy walls, so we ask for a builder full of walls
    return true;
  }
}
