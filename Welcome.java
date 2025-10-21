import java.awt.*;
import javax.swing.*;

public class Welcome extends JFrame {

    public Welcome() {
        setTitle("Kees' Beer Smash");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JLayeredPane layeredPane = new JLayeredPane();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();
        layeredPane.setPreferredSize(new Dimension(screenWidth, screenHeight));

        // Background panel
        ImageIcon originalIcon = new ImageIcon("welcome page.png");
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(originalIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setBounds(0, 0, screenWidth, screenHeight);
        layeredPane.add(backgroundPanel, JLayeredPane.DEFAULT_LAYER);

        // Create transparent buttons, so buttons on image can be seen
        JButton easyButton = createMinimalTransparentButton("EASY", 0.25);
        JButton mediumButton = createMinimalTransparentButton("MEDIUM", 0.5);
        JButton hardButton = createMinimalTransparentButton("HARD", 0.75);

        easyButton.addActionListener(e -> startGame("easy"));
        mediumButton.addActionListener(e -> startGame("medium"));
        hardButton.addActionListener(e -> startGame("hard"));

        layeredPane.add(easyButton, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(mediumButton, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(hardButton, JLayeredPane.PALETTE_LAYER);

        add(layeredPane);
        pack();
        setLocationRelativeTo(null);
    }

    private JButton createMinimalTransparentButton(String text, double xPosition) {
        JButton button = new JButton(text);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();

        int buttonWidth = screenWidth / 6;
        int buttonHeight = screenHeight / 12;
        int x = (int) (screenWidth * xPosition) - (buttonWidth / 2);
        int y = screenHeight * 2 / 3;

        button.setBounds(x, y, buttonWidth, buttonHeight);

        // Complete transparency
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);

        // White text
        int fontSize = Math.max(24, screenWidth / 40);
        button.setFont(new Font("Arial", Font.BOLD, fontSize));
        button.setForeground(Color.WHITE);

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });

        return button;
    }

    private void startGame(String difficulty) {
        this.dispose();
        Main_two.startGame(difficulty);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Welcome().setVisible(true);
        });
    }
}

