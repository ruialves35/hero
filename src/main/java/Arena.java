import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Arena {
    private int width;
    private int height;
    private Hero hero;
    private List<Wall> walls;


    private void moveHero(Position position) {
        if (canHeroMove(position)) {
            this.hero.setPosition(position);
        }
    }

    private boolean canHeroMove(Position position){
        for (Wall wall : walls){
            if (wall.getPosition().equals(position)) {
                return false;
            }
        }
        if (position.getX() < 0 || position.getX() >= width)
            return false;
        else if ( position.getY() < 0 || position.getY() >= height)
            return false;
        return true;
    }

    private List<Wall> createWalls() {
        List<Wall> walls = new ArrayList<>();

        for (int c = 0; c < width; c++) {
            walls.add(new Wall(c, 0));
            walls.add(new Wall(c, height - 1));
        }

        for (int r = 1; r < height - 1; r++) {
            walls.add(new Wall(0, r));
            walls.add(new Wall(width - 1, r));
        }

        return walls;
    }


    public Arena(int width, int height) {
        this.width = width;
        this.height = height;
        this.hero = new Hero(10, 10);
        this.walls = createWalls();

    }


    public void processKey(KeyStroke key) throws IOException {
        switch (key.getKeyType()){
            case ArrowDown:
                moveHero(this.hero.moveDown());
                break;
            case ArrowUp:
                moveHero(this.hero.moveUp());
                break;
            case ArrowLeft:
                moveHero(this.hero.moveLeft());
                break;
            case ArrowRight:
                moveHero(this.hero.moveRight());
                break;
        }
    }

    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#336699"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');

        this.hero.draw(graphics);
        for (Wall wall : walls)
            wall.draw(graphics);
    }

}
