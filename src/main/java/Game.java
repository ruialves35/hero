import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;


import java.io.IOException;

public class Game {
    private Screen screen;
    private Hero hero;

    private void draw() throws IOException {
        this.screen.clear();
        this.hero.draw(screen);
        this.screen.refresh();
    }

    private void processKey(KeyStroke key) throws IOException {
        switch (key.getKeyType()){
            case ArrowDown:
                this.hero.moveDown();
                break;
            case ArrowUp:
                this.hero.moveUp();
                break;
            case ArrowLeft:
                this.hero.moveLeft();
                break;
            case ArrowRight:
                this.hero.moveRight();
                break;
            case Character:
                screen.close(); //next keystroke will be EOF because screen was closed
        }

    }

    public Game(){
        this.hero = new Hero(10, 10);

        try {
            TerminalSize terminalSize = new TerminalSize(40, 20);
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory().setInitialTerminalSize(terminalSize);
            Terminal terminal = terminalFactory.createTerminal();

            this.screen = new TerminalScreen(terminal);

            this.screen.setCursorPosition(null);   // we don't need a cursor
            this.screen.startScreen();             // screens must be started
            this.screen.doResizeIfNecessary();     // resize screen if necessary

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() throws IOException {
        while(true) {
            draw();
            KeyStroke key = screen.readInput();
            if (key.getKeyType() == KeyType.EOF) {
                System.out.println("EOF");
                break;
            }
            processKey(key);
        }
    }

}
