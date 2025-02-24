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
    stack.push(from);
    ArrayList<Boolean> visitedVerticies = new ArrayList<>(builder.topology().nbVertices());
    visitedVerticies.set(from, true);

    while (!stack.isEmpty()){
      Integer currentVertex = stack.peek();
      List<Integer> neighbours = builder.topology().neighbors(currentVertex);

      // filtré
      neighbours.removeIf(visitedVerticies::get);

      while (!neighbours.isEmpty()) {
        Integer neighbourIndex = rand.nextInt(neighbours.size());
        Integer neighbour = neighbours.get(neighbourIndex);
        neighbours.remove(neighbourIndex);

        stack.push(neighbour);
        builder.removeWall(currentVertex, neighbour);

        visitedVerticies.set(neighbour, true);
      }

      stack.pop();
    }

  // Si besoin Collections.shuffle
  }

  @Override
  public boolean requireWalls() {
    new UnsupportedOperationException("Not implemented yet");
    return false;
  }
}
