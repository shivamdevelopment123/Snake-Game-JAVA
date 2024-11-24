import javax.swing.*;

public class Main {
    public static void main(String[] args) throws Exception{

        int windowWidth = 700;
        int windowHeight = windowWidth;


        JFrame frame = new JFrame("Snake Mania");
        frame.setVisible(true);
        frame.setSize(windowWidth,windowHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        GamePanel gamePanel = new GamePanel(windowWidth, windowHeight);
        frame.add(gamePanel);
        frame.pack();
        gamePanel.requestFocus();
    }
}