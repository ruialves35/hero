import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.screen.Screen;

public class Hero {
    private int x;
    private int y;

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int setX(int x){
        return x;
    }

    public int setY(int y){
        return y;
    }

    public Hero(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void moveUp(){
        this.y -= 1;
    }

    public void moveDown(){
        this.y += 1;
    }

    public void moveLeft(){
        this.x -= 1;
    }

    public void moveRight(){
        this.x += 1;
    }

    public void draw(Screen screen){
        screen.setCharacter(this.x, this.y, TextCharacter.fromCharacter('X')[0]);
    }
}
