package org.example;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Arena {

    private Hero hero;
    private int width;
    private int height;
    private List<Wall> walls;
    private List<Bouble> boubles;
    private PointLabel pointLabel;

    public Arena(String mapFilePath) {
        this.walls = new ArrayList<>();
        this.boubles = new ArrayList<>();
        this.pointLabel = new PointLabel(0, 0);
        loadMap(mapFilePath);
    }

    private void loadMap(String mapFilePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(mapFilePath))) {
            String line;
            int row = 0;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                height++;
                width = line.length();
                for (int col = 0; col < line.length(); col++) {
                    char c = line.charAt(col);
                    if (c == '#') {
                        walls.add(new Wall(col, row));
                    } else if (c == 'H') {
                        hero = new Hero(col, row);
                    }
                    else if (c == '.') {
                        boubles.add(new Bouble(col, row));
                    }
                }
                row++;
            }
        } catch (IOException e) {
            System.err.println("Error loading map: " + e.getMessage());
        }
    }

    public void draw(TextGraphics graphics) {
        pointLabel.setPosition(new Position(0, height + 2));
        graphics.setBackgroundColor(TextColor.Factory.fromString("#FFC0CB"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');

        for (Wall wall : walls) {
            wall.draw(graphics);
        }
        for(Bouble bouble : boubles){
            if(bouble.isVisible()){
                bouble.draw(graphics);
            }
        }
        pointLabel.draw(graphics);
        hero.draw(graphics);
    }

    public void moveHero() {
        Position newPosition = hero.move(width, height);
        if (canHeroMove(newPosition)) {
            if (!getPoint(newPosition)) {
                pointLabel.incrementPoints();
                System.out.println(pointLabel.getPoints());
            }
            hero.setPosition(newPosition);
        }
    }

    private boolean canHeroMove(Position position) {
        if (position.getX() < 0 || position.getX() >= width || position.getY() < 0 || position.getY() >= height) {
            return false;
        }

        for (Wall wall : walls) {
            if (wall.getX() == position.getX() && wall.getY() == position.getY()) {
                return false;
            }
        }

        return true;
    }

    private boolean getPoint(Position position) {
        for (Bouble bouble : boubles) {
            if(bouble.isVisible()){
                if (bouble.getX() == position.getX() && bouble.getY() == position.getY()) {
                    bouble.disappear();
                    return false;
                }
            }
        }
        return true;
    }

    public void processKey(KeyStroke key) {
        if (key.getKeyType() == KeyType.ArrowUp) {
            hero.setDirection(Hero.Direction.UP);
        } else if (key.getKeyType() == KeyType.ArrowDown) {
            hero.setDirection(Hero.Direction.DOWN);
        } else if (key.getKeyType() == KeyType.ArrowLeft) {
            hero.setDirection(Hero.Direction.LEFT);
        } else if (key.getKeyType() == KeyType.ArrowRight) {
            hero.setDirection(Hero.Direction.RIGHT);
        }
    }
}
