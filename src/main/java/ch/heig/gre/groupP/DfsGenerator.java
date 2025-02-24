package ch.heig.gre.groupP;

import ch.heig.gre.maze.MazeBuilder;
import ch.heig.gre.maze.MazeGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import java.util.Random;

public final class DfsGenerator implements MazeGenerator {
  @Override
  public void generate(MazeBuilder builder, int from) {
    if (builder == null) throw new NullPointerException("Builder missing");
    if (!builder.topology().vertexExists(from)) throw new IllegalArgumentException("Non-existent source node");

    Random rand = new Random();
    Stack<Integer> stack = new Stack<>();
    ArrayList<Boolean> visitedVerticies = new ArrayList<>(builder.topology().nbVertices());
    stack.push(from);
    visitedVerticies.set(from, true);

    while (!stack.isEmpty()){
      Integer currentVertex = stack.peek();
      List<Integer> neighbours = builder.topology().neighbors(currentVertex);
      // filtré
      neighbours.removeIf(visitedVerticies::get);

      if (neighbours.isEmpty()) {
        stack.pop();
        continue;
      }

      Integer neighbour = neighbours.get(rand.nextInt(neighbours.size()));
      stack.push(neighbour);
      visitedVerticies.set(neighbour, true);
      builder.removeWall(currentVertex, neighbour);
    }
    // Si besoin Collections.shuffle
  }

  @Override
  public boolean requireWalls() {
    new UnsupportedOperationException("Not implemented yet");
    return false;
  }
}
