package org.example;

import com.googlecode.lanterna.graphics.TextGraphics;

import java.io.IOException;

public abstract class Element {
    protected Position position;


    public Element(int x, int y) {
        this.position = new Position(x, y);
    }


    public int getX() {
        return position.getX();
    }

    public int getY() {
        return position.getY();
    }

    public Position getPosition(){
        return this.position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public abstract void draw(TextGraphics graphics) throws IOException;
}
