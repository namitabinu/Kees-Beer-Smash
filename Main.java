import java.awt.*;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.*;
import javax.swing.*;


/**
 * The Main class serves as the entry point for the Pub Trivia Game application.
 */
public class Main {
    
    public static void main(String[] args) {
        int platformX = 50;
        int platformWidth = 100;
        double ballRadius = 30;
        double ballX = platformX + platformWidth / 2;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        Targets[] targets = new Targets[4];
        targets[0] = new Targets(screenWidth, 700, 140, 180, "D");
        targets[1] = new Targets(screenWidth - 250, 500, 140, 180, "C");
        targets[2] = new Targets(screenWidth, 300, 140, 180, "B");
        targets[3] = new Targets(screenWidth - 250, 100, 140, 180, "A");
        BallCalculations ballCalculations = new BallCalculations(ballX, 530, 0, 0, ballRadius, 
                targets);
        AnimationsAndObjects panel = new AnimationsAndObjects(ballCalculations, targets);
        
        JFrame frame = new JFrame("Pub Trivia Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);

        Timer timer = new Timer(16, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ballCalculations.updatePosition();
                panel.repaint();
            }
        });
        timer.start();
    }
}
