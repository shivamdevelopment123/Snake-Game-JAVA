import javax.swing.*;

public class Main {
    public static void main(String[] args) throws Exception{

        int windowWidth = 700;
        int windowHeight = windowWidth;


        JFrame frame = new JFrame("Snake Mania");
        frame.setSize(windowWidth,windowHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        MainMenu mainMenu = new MainMenu(frame,windowWidth,windowHeight);
        frame.add(mainMenu);
        frame.setVisible(true);

    }
}