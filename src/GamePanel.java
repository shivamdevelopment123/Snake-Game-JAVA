import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener, KeyListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (gameOver){
            gameLoop.stop();
        }
    }

    private void move() {

        if (collision(snakeHead,food)){
            snakeBody.add(new Tile(food.x, food.y));
            placeFood();
        }

        for (int i = snakeBody.size()-1; i>=0; i--){
            Tile snakePart = snakeBody.get(i);
            if (i==0){
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            }else {
                Tile preSnakePart = snakeBody.get(i-1);
                snakePart.x = preSnakePart.x;
                snakePart.y = preSnakePart.y;
            }
        }

        snakeHead.x += velocityX;
        snakeHead.y += velocityY;

        for (int i = 0; i<snakeBody.size(); i++){
            Tile snakePart = snakeBody.get(i);
            if (collision(snakeHead, snakePart)){
                gameOver = true;
            }
        }
        if (snakeHead.x*tileSize<0 || snakeHead.x*tileSize > windowWidth||
        snakeHead.y*tileSize < 0 || snakeHead.y*tileSize > windowHeight){
            gameOver = true;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode()==KeyEvent.VK_UP && velocityY!=1){
            velocityX=0;
            velocityY=-1;
        } else if (e.getKeyCode()==KeyEvent.VK_DOWN && velocityY!=-1) {
            velocityX=0;
            velocityY=1;
        } else if (e.getKeyCode()==KeyEvent.VK_LEFT && velocityX!=1) {
            velocityX=-1;
            velocityY=0;
        }else if (e.getKeyCode()==KeyEvent.VK_RIGHT && velocityX!=-1){
            velocityX=1;
            velocityY=0;
        }
    }

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
    Random random;

    //logic
    Timer gameLoop;
    int velocityX;
    int velocityY;
    boolean gameOver = false;

    //size
    int windowWidth;
    int windowHeight;
    int tileSize = 15;
    ArrayList<Tile> snakeBody;



    GamePanel(int windowWidth, int windowHeight){
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        setPreferredSize(new Dimension(this.windowWidth,this.windowHeight));
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);

        //place snakehead initially
        snakeHead = new Tile(5, 5);
        snakeBody = new ArrayList<Tile>();

        //placefood initially
        food = new Tile(10,10);

        //randomly change food position
        random = new Random();
        placeFood();

        gameLoop = new Timer(100,this);
        gameLoop.start();
    }

    private void placeFood() {
        food.x = random.nextInt(windowWidth/tileSize);
        food.y= random.nextInt(windowHeight/tileSize);
    }

    private boolean collision(Tile tile1, Tile tile2){
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    public  void  paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        g.setColor(Color.GREEN);
        g.fill3DRect(snakeHead.x*tileSize, snakeHead.y*tileSize,tileSize,tileSize,true);

        g.setColor(Color.RED);
        g.fill3DRect(food.x*tileSize,food.y*tileSize,tileSize,tileSize,true);

        //Draw snake body
        for(int i=0;i<snakeBody.size();i++){
            g.setColor(Color.GREEN);
            Tile snakePart = snakeBody.get(i);
            g.fill3DRect(snakePart.x*tileSize, snakePart.y*tileSize, tileSize, tileSize,true);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }

}
