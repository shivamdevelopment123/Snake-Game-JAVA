import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    private class Tile{
        int x;
        int y;

        Tile(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
    Tile snakeHead;
    Tile food;

    int windowWidth;
    int windowHeight;
    int tileSize = 15;

    GamePanel(int windowWidth, int windowHeight){
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        setPreferredSize(new Dimension(this.windowWidth,this.windowHeight));
        setBackground(Color.BLACK);
        snakeHead = new Tile(5, 5);
        food = new Tile(10,10);
    }

    public  void  paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        g.setColor(Color.GREEN);
        g.fillRect(snakeHead.x*tileSize, snakeHead.y*tileSize,tileSize,tileSize);

        g.setColor(Color.RED);
        g.fillRect(food.x*tileSize,food.y*tileSize,tileSize,tileSize);
    }
}
