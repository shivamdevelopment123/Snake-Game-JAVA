import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

class GamePanel extends JPanel implements ActionListener, KeyListener {
    private JFrame frame;
    private Tile snakeHead;
    private Tile food;
    private Random random;

    private Timer gameLoop;
    private int velocityX;
    private int velocityY;
    private boolean gameOver = false;

    private int windowWidth;
    private int windowHeight;
    private int tileSize = 15;
    private ArrayList<Tile> snakeBody;
    private int score = 0;

    public GamePanel(JFrame frame, int windowWidth, int windowHeight) {
        this.frame = frame;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;

        setPreferredSize(new Dimension(this.windowWidth, this.windowHeight));
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);

        snakeHead = new Tile(5, 5);
        snakeBody = new ArrayList<>();
        food = new Tile(10, 10);

        random = new Random();
        placeFood();

        gameLoop = new Timer(100, this);
        gameLoop.start();
    }

    private void placeFood() {
        food.x = random.nextInt(windowWidth / tileSize);
        food.y = random.nextInt(windowHeight / tileSize);
    }

    private boolean collision(Tile tile1, Tile tile2) {
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    private void move() {
        if (collision(snakeHead, food)) {
            snakeBody.add(new Tile(food.x, food.y));
            placeFood();
            score++;
        }

        for (int i = snakeBody.size() - 1; i >= 0; i--) {
            Tile snakePart = snakeBody.get(i);
            if (i == 0) {
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            } else {
                Tile preSnakePart = snakeBody.get(i - 1);
                snakePart.x = preSnakePart.x;
                snakePart.y = preSnakePart.y;
            }
        }

        snakeHead.x += velocityX;
        snakeHead.y += velocityY;

        for (int i = 0; i < snakeBody.size(); i++) {
            Tile snakePart = snakeBody.get(i);
            if (collision(snakeHead, snakePart)) {
                gameOver = true;
            }
        }
        if (snakeHead.x * tileSize < 0 || snakeHead.x * tileSize >= windowWidth ||
                snakeHead.y * tileSize < 0 || snakeHead.y * tileSize >= windowHeight) {
            gameOver = true;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            move();
            repaint();
        } else {
            gameLoop.stop();
            showGameOverScreen();
        }
    }

    private void showGameOverScreen() {
        JPanel gameOverPanel = new JPanel();
        gameOverPanel.setLayout(new GridBagLayout());
        gameOverPanel.setOpaque(false);
        gameOverPanel.setBackground(new Color(0, 0, 0, 150));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel gameOverLabel = new JLabel("Game Over");
        gameOverLabel.setForeground(Color.RED);
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 36));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gameOverPanel.add(gameOverLabel, gbc);

        JLabel scoreLabel = new JLabel("Score: " + score);
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        gbc.gridy = 1;
        gameOverPanel.add(scoreLabel, gbc);

        JButton replayButton = new JButton("Replay");
        replayButton.setPreferredSize(new Dimension(100, 50));
        gbc.gridy = 2;
        gameOverPanel.add(replayButton, gbc);

        replayButton.addActionListener(e -> {
            frame.getContentPane().removeAll();
            GamePanel newGamePanel = new GamePanel(frame, windowWidth, windowHeight);
            frame.add(newGamePanel);
            frame.revalidate();
            newGamePanel.requestFocus();
        });

        setLayout(new OverlayLayout(this));
        add(gameOverPanel);
        revalidate();
        repaint();
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    private void draw(Graphics g) {
        g.setColor(Color.GREEN);
        g.fill3DRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize, true);

        g.setColor(Color.RED);
        g.fill3DRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize, true);

        for (Tile snakePart : snakeBody) {
            g.setColor(Color.GREEN);
            g.fill3DRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize, true);
        }

        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, 10, 10);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1) {
            velocityX = 0;
            velocityY = -1;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1) {
            velocityX = 0;
            velocityY = 1;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1) {
            velocityX = -1;
            velocityY = 0;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1) {
            velocityX = 1;
            velocityY = 0;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    private class Tile {
        int x, y;

        Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}