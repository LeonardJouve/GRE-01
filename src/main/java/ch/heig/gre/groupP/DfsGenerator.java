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

    Random rand = new Random();
    Stack<Integer> stack = new Stack<>();
    // TODO: change back to ArrayList
    Set<Integer> visitedVerticies = new HashSet<>();
    stack.push(from);
    visitedVerticies.add(from);

    while (!stack.isEmpty()) {
      Integer currentVertex = stack.peek();
      builder.progressions().setLabel(currentVertex, Progression.PROCESSING);
      List<Integer> neighbours = builder.topology().neighbors(currentVertex);
      // filtré
      neighbours.removeIf(visitedVerticies::contains);

      if (neighbours.isEmpty()) {
        stack.pop();
        builder.progressions().setLabel(currentVertex, Progression.PROCESSED);
        continue;
      }

      Integer neighbour = neighbours.get(rand.nextInt(neighbours.size()));
      stack.push(neighbour);
      visitedVerticies.add(neighbour);
      builder.removeWall(currentVertex, neighbour);
    }
    // Si besoin Collections.shuffle
  }

  @Override
  public boolean requireWalls() {
    return true;
  }
}
