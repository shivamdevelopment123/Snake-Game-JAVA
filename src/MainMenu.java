import javax.swing.*;
import java.awt.*;

public class MainMenu extends JPanel {

        public MainMenu(JFrame frame, int windowWidth, int windowHeight) {
            setPreferredSize(new Dimension(windowWidth, windowHeight));
            setLayout(new GridBagLayout());
            setBackground(Color.BLACK);

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);

            JLabel titleLabel = new JLabel("Snake Mania");
            titleLabel.setForeground(Color.GREEN);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
            gbc.gridx = 0;
            gbc.gridy = 0;
            add(titleLabel, gbc);

            JButton playButton = new JButton("Play");
            playButton.setPreferredSize(new Dimension(100, 50));
            gbc.gridy = 1;
            add(playButton, gbc);

            JButton exitButton = new JButton("Exit");
            exitButton.setPreferredSize(new Dimension(100, 50));
            gbc.gridy = 2;
            add(exitButton, gbc);

            playButton.addActionListener(e -> {
                frame.getContentPane().removeAll();
                GamePanel gamePanel = new GamePanel( frame,windowWidth, windowHeight);
                frame.add(gamePanel);
                frame.revalidate();
                gamePanel.requestFocus();
            });

            exitButton.addActionListener(e -> System.exit(0));
        }
}
