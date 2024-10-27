package org.example;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class Bouble extends Element{
    private int points;
    private boolean isVisible = true;


    public Bouble(int x, int y) {
        super(x, y);
    }


    public void disappear() {
        isVisible = false;
    }


    public int getPoints() {
        if (isVisible) {
            return points;
        }
        return 0;
    }


    public boolean isVisible() {
        return isVisible;
    }


    public void reset(int newPoints) {
        this.points = newPoints;
        this.isVisible = true;
    }

    @Override
    public void draw(TextGraphics graphics) {

        graphics.setForegroundColor(TextColor.ANSI.WHITE);
        graphics.putString(position.getX(), position.getY(), ".");
    }

    @Override
    public String toString() {
        return "Bouble{" +
                "points=" + points +
                ", isVisible=" + isVisible +
                '}';
    }
}
