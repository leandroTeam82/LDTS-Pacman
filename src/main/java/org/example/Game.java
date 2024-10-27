package org.example;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class Game {

    private Screen screen;
    private Arena arena;

    public void run() {
        try {
            TerminalSize terminalSize = new TerminalSize(100, 90);
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory().setInitialTerminalSize(terminalSize);
            Terminal terminal = terminalFactory.createTerminal();

            screen = new TerminalScreen(terminal);
            screen.setCursorPosition(null);
            screen.startScreen();
            screen.doResizeIfNecessary();

            arena = new Arena("/Users/leandrosampaio/IdeaProjects/semana3/src/maps/map.txt");


            while (true) {
                draw();
                processInput();
                arena.moveHero();

                Thread.sleep(100);

                if (screen.getTerminalSize().getColumns() <= 0) break;
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Error in game loop: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void processInput() throws IOException {
        KeyStroke key = screen.pollInput(); // Non-blocking input check
        if (key != null) {
            arena.processKey(key);          // Change direction based on key press
            if (key.getCharacter() != null && key.getCharacter() == 'q') {
                screen.close();
            }
        }
    }

    private void draw() throws IOException {
        screen.clear();
        arena.draw(screen.newTextGraphics());
        screen.refresh();
    }
}
