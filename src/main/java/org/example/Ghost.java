package org.example;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

import java.io.IOException;
import java.util.List;

public class Ghost extends Element {

    public enum Direction {
        UP, DOWN, LEFT, RIGHT, NONE
    }

    private final Direction dir;

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
        Position target = heroPosition;
        Direction bestDirection = Direction.NONE;
        double shortestDistance = Double.MAX_VALUE;

        Direction[] directions = {Direction.UP, Direction.RIGHT, Direction.DOWN, Direction.LEFT};
        Position[] possibleMoves = {
                new Position(position.getX(), position.getY() - 1),  // UP
                new Position(position.getX() + 1, position.getY()),  // RIGHT
                new Position(position.getX(), position.getY() + 1),  // DOWN
                new Position(position.getX() - 1, position.getY())   // LEFT
        };


        for (int i = 0; i < directions.length; i++) {
            Position newPosition = possibleMoves[i];


                double distance = calculateDistance(newPosition, target);
                if (distance < shortestDistance) {
                    shortestDistance = distance;
                    bestDirection = directions[i];

            }
        }

        return bestDirection;
    }

    private double calculateDistance(Position pos1, Position pos2) {
        int dx = pos1.getX() - pos2.getX();
        int dy = pos1.getY() - pos2.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }
}
