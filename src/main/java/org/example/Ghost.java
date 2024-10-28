package org.example;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

import java.util.*;

public class Ghost extends Element {

    public enum Direction {
        UP, DOWN, LEFT, RIGHT, NONE
    }

    private Direction dir;

    public Ghost(int x, int y) {
        super(x, y);
        this.dir = Direction.NONE;
    }

    @Override
    public void draw(TextGraphics graphics) {
        graphics.setForegroundColor(TextColor.Factory.fromString("#FF0000"));
        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition(position.getX(), position.getY()), "ᗣ");
    }

    public Direction moveTowardsPacman(Position heroPosition, int width, int height, List<Wall> walls) {
        Position start = this.position;
        Position target = heroPosition;


        Position nextMove = findNextMoveAStar(start, target, width, height, walls);

        if (nextMove == null) {
            return Direction.NONE;
        }

        if (nextMove.getX() == position.getX() && nextMove.getY() == position.getY() - 1) return Direction.UP;
        if (nextMove.getX() == position.getX() && nextMove.getY() == position.getY() + 1) return Direction.DOWN;
        if (nextMove.getX() == position.getX() - 1 && nextMove.getY() == position.getY()) return Direction.LEFT;
        if (nextMove.getX() == position.getX() + 1 && nextMove.getY() == position.getY()) return Direction.RIGHT;

        return Direction.NONE;
    }

    private Position findNextMoveAStar(Position start, Position target, int width, int height, List<Wall> walls) {
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingDouble(node -> node.fCost));
        Set<Position> closedSet = new HashSet<>();
        Map<Position, Position> cameFrom = new HashMap<>();

        Node startNode = new Node(start, 0, calculateDistance(start, target));
        openSet.add(startNode);

        while (!openSet.isEmpty()) {
            Node currentNode = openSet.poll();
            Position currentPosition = currentNode.position;

            if (currentPosition.equals(target)) {
                return reconstructPath(cameFrom, currentPosition);
            }

            closedSet.add(currentPosition);

            for (Position neighbor : getNeighbors(currentPosition, width, height, walls)) {
                if (closedSet.contains(neighbor)) continue;

                double tentativeGCost = currentNode.gCost + 1;

                Node neighborNode = new Node(neighbor, tentativeGCost, calculateDistance(neighbor, target));

                if (!openSet.contains(neighborNode) || tentativeGCost < neighborNode.gCost) {
                    cameFrom.put(neighbor, currentPosition);
                    neighborNode.gCost = tentativeGCost;
                    neighborNode.fCost = tentativeGCost + neighborNode.hCost;

                    if (!openSet.contains(neighborNode)) {
                        openSet.add(neighborNode);
                    }
                }
            }
        }
        return null;
    }

    private Position reconstructPath(Map<Position, Position> cameFrom, Position current) {
        Position firstStep = current;
        while (cameFrom.containsKey(current)) {
            firstStep = current;
            current = cameFrom.get(current);
        }
        return firstStep;
    }

    private List<Position> getNeighbors(Position position, int width, int height, List<Wall> walls) {
        List<Position> neighbors = new ArrayList<>();
        int[][] directions = {{0, -1}, {1, 0}, {0, 1}, {-1, 0}}; // UP, RIGHT, DOWN, LEFT

        for (int[] dir : directions) {
            Position newPos = new Position(position.getX() + dir[0], position.getY() + dir[1]);
            if (isValidMove(newPos, width, height, walls)) {
                neighbors.add(newPos);
            }
        }
        return neighbors;
    }

    private boolean isValidMove(Position position, int width, int height, List<Wall> walls) {

        if (position.getX() < 0 || position.getX() >= width || position.getY() < 0 || position.getY() >= height) {
            return false;
        }

        for (Wall wall : walls) {
            if (wall.getPosition().equals(position)) {
                return false;
            }
        }
        return true;
    }

    private double calculateDistance(Position pos1, Position pos2) {
        int dx = pos1.getX() - pos2.getX();
        int dy = pos1.getY() - pos2.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }


    private static class Node {
        Position position;
        double gCost;
        double hCost;
        double fCost;

        Node(Position position, double gCost, double hCost) {
            this.position = position;
            this.gCost = gCost;
            this.hCost = hCost;
            this.fCost = gCost + hCost;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Node node = (Node) obj;
            return position.equals(node.position);
        }

        @Override
        public int hashCode() {
            return Objects.hash(position);
        }
    }
}
