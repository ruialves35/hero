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
    private Arena arena;

    private void draw() throws IOException {
        this.screen.clear();
        this.arena.draw(screen);
        this.screen.refresh();
    }


    public Game(){
        this.arena = new Arena(40, 20);

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
            arena.processKey(key);
        }
    }

}
