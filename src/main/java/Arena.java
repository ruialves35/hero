import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;

public class Arena {
    private int width;
    private int height;
    private Hero hero;

    private void moveHero(Position position) {
        if (canHeroMove(position)) {
            this.hero.setPosition(position);
        }
    }

    private boolean canHeroMove(Position position){
        if (position.getX() < 0 || position.getX() >= width)
            return false;
        else if ( position.getY() < 0 || position.getY() >= height)
            return false;
        return true;
    }

    public Arena(int width, int height) {
        this.width = width;
        this.height = height;
        this.hero = new Hero(10, 10);
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

    public void draw(Screen screen) {
        this.hero.draw(screen);
    }
}
