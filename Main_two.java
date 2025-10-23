import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.*;
import javax.swing.*;

/**
 * The Main class serves as the entry point for the Pub Trivia Game application.
 */
public class Main_two {

    public static void main(String[] args) {

        // Launch the welcome screen first
        SwingUtilities.invokeLater(() -> {
            Welcome welcomeScreen = new Welcome();
            welcomeScreen.setVisible(true);
        });

    }

    // Method to start the actual game with the selected difficulty
    public static void startGame(String difficulty) {
        System.out.println("Starting game with difficulty: " + difficulty);

        int platformX = 50;
        int platformWidth = 100;
        double ballRadius = 30;
        double ballX = platformX + platformWidth / 2;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();

        Targets[] bombs = new Targets[3];
        bombs[0] = new Targets(screenWidth - 400, 400, 100, 110);
        bombs[1] = new Targets(screenWidth - 600, 600, 100, 110);
        bombs[2] = new Targets(screenWidth - 200, 200, 100, 110);

        Targets[] targets = new Targets[4];
        targets[0] = new Targets(screenWidth, 700, 180, 230);
        targets[1] = new Targets(screenWidth - 250, 500, 180, 230);
        targets[2] = new Targets(screenWidth, 300, 180, 230);
        targets[3] = new Targets(screenWidth - 250, 100, 180, 230);
        BallCalculations ballCalculations = new BallCalculations(ballX, 530, 0, 0, ballRadius,
                targets, bombs);
        AnimationsAndObjects panel = new AnimationsAndObjects(ballCalculations, targets, bombs, difficulty);

        JFrame frame = new JFrame("Pub Trivia Game - " + difficulty.toUpperCase() + " Mode");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);

        Timer timer = new Timer(8, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ballCalculations.updatePosition();
                ballCalculations.checkCollisions(panel); 
                panel.repaint();
            }
        });
        timer.start();
    }
}
