package org.example;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

import java.util.HashMap;
import java.util.Map;

public class Hero extends Element {

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    public Map<Direction, String> directionSymbols = new HashMap<>() {{
        put(Direction.UP, "ᗢ");
        put(Direction.DOWN, "ᗣ");
        put(Direction.LEFT, "ᗤ");
        put(Direction.RIGHT, "ᗧ");
    }};

    private Direction direction;

    public Hero(int x, int y) {
        super(x, y);
        this.direction = Direction.RIGHT;
    }

    @Override
    public void draw(TextGraphics graphics) {
        graphics.setForegroundColor(TextColor.Factory.fromString("#FFFF00"));
        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition(position.getX(), position.getY()), directionSymbols.get(this.direction));
    }

    public Position move(int width, int height) {
        Position newPosition;

        switch (direction) {
            case UP:
                newPosition = new Position(position.getX(), position.getY() - 1);
                break;
            case DOWN:
                newPosition = new Position(position.getX(), position.getY() + 1);
                break;
            case LEFT:
                newPosition = new Position(position.getX() - 1, position.getY());
                break;
            case RIGHT:
                newPosition = new Position(position.getX() + 1, position.getY());
                break;
            default:
                return position;
        }


        if (newPosition.getX() < 0) {
            newPosition = new Position(width - 1, newPosition.getY());
        } else if (newPosition.getX() >= width) {
            newPosition = new Position(0, newPosition.getY());
        }

        if (newPosition.getY() < 0) {
            newPosition = new Position(newPosition.getX(), height - 1);
        } else if (newPosition.getY() >= height) {
            newPosition = new Position(newPosition.getX(), 0);
        }

        return newPosition;
    }


    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }
}
