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
import java.util.Random;

public class Arena {
    private int width;
    private int height;
    private Hero hero;
    private List<Wall> walls;
    private List<Coin> coins;
    private List<Monster> monsters;

    private void moveHero(Position position) {
        if (canHeroMove(position)) {
            this.hero.setPosition(position);
        }
    }

    private boolean canMove(Position position){
        if (position.getX() < 0 || position.getX() >= width)
            return false;
        else if ( position.getY() < 0 || position.getY() >= height)
            return false;
        for (Monster monster: monsters){
            if (monster.getPosition().equals(position))
                return false;
        }
        for (Wall wall : walls){
            if (wall.getPosition().equals(position))
                return false;
        }
        for (Coin coin : coins){
            if (coin.getPosition().equals(position)){
                return false;
            }
        }
        return true;
    }

    private boolean canHeroMove(Position position){
        if (position.getX() < 0 || position.getX() >= width)
            return false;
        else if ( position.getY() < 0 || position.getY() >= height)
            return false;

        for (Wall wall : walls){
            if (wall.getPosition().equals(position)) {
                return false;
            }
        }

        for (Coin coin : coins){
            if (coin.getPosition().equals(position)){
                retrieveCoins(coin);
                return true;
            }
        }

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

    private List<Coin> createCoins() {
        Random random = new Random();
        ArrayList<Coin> coins = new ArrayList<>();
        int i = 0;
        while( i < 5){
                Coin coin = new Coin(random.nextInt(width - 2) + 1, random.nextInt(height - 2) + 1);
                boolean already = false;
                for (int j = 0; j < coins.size(); j++) {
                    if (coin.getPosition().equals(coins.get(j).getPosition())) {
                        already = true;
                        break;
                    }
                }
                if (!already) {
                    coins.add(coin);
                    i++;
                }
        }
        return coins;
    }

    private List<Monster> createMonsters(){
        Random random = new Random();
        ArrayList<Monster> monsters = new ArrayList<>();
        int i = 0;

        while (i < 5){
                Monster monster = new Monster(random.nextInt(width - 2) + 1, random.nextInt(height - 2) + 1);
                boolean already = false;
                for (int j = 0; j < coins.size(); j++) {
                    if (monster.getPosition().equals(coins.get(j).getPosition())) {
                        already = true;
                        break;
                    }
                }
                for (int j = 0; j < monsters.size(); j++){
                    if (monster.getPosition().equals(monsters.get(j).getPosition())){
                        already = true;
                        break;
                    }
                }
                if (!already) {
                    monsters.add(monster);
                    i++;
                }
        }
        return monsters;
    }

    private void retrieveCoins(Coin rmCoin){
        this.coins.remove(rmCoin);
    }

    public Arena(int width, int height) {
        this.width = width;
        this.height = height;
        this.hero = new Hero(10, 10);
        this.walls = createWalls();
        this.coins = createCoins();
        this.monsters = createMonsters();
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
        moveMonsters();
    }

    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#336699"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');

        this.hero.draw(graphics);

        for (Wall wall : walls)
            wall.draw(graphics);

        for (Coin coin : coins)
            coin.draw(graphics);

        for (Monster monster : monsters)
            monster.draw(graphics);
    }

    public void moveMonsters(){
        for (Monster monster : monsters){
            Position position = monster.move();
            if (canMove(position)){
                monster.setPosition(position);
            }
        }
    }

    public boolean verifyMonsterCollisions(){
        for (Monster monster : monsters){
            if (monster.position.equals(hero.position))
                return false;
        }
        return true;
    }

}
