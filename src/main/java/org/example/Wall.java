package org.example;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.TextColor;

public class Wall extends Element {

    public Wall(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw(TextGraphics graphics) {

        graphics.setForegroundColor(TextColor.ANSI.BLUE);
        graphics.putString(position.getX(), position.getY(), "□");
    }
}
