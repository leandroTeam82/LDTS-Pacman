package org.example;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.TextColor;

import java.io.IOException;

public class PointLabel extends Element {
    private int points;

    public PointLabel(int width, int height) {
        super(width, height);
        this.points = 0;
    }

    public void incrementPoints(){
        this.points += 100;
    }

    public int getPoints(){return this.points;}

    @Override
    public void draw(TextGraphics graphics) {
        graphics.setForegroundColor(TextColor.ANSI.YELLOW);
        graphics.putString(position.getX(), position.getY(), "Points: " + this.points + " ");
    }
}
