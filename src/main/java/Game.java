import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.screen.Screen;


import java.io.IOException;

public class Game {
    private Screen screen;

    private void draw() throws IOException {
        this.screen.clear();
        this.screen.setCharacter(10, 10, TextCharacter.fromCharacter('X')[0]);
        this.screen.refresh();
    }

    public Game(Screen screen){
        this.screen = screen;
    }

    public void run() throws IOException {
        draw();
    }

}
