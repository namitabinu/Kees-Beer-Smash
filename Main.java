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
        Targets target = new Targets(screenWidth, 300, 200, 250);
        BallCalculations ballCalculations = new BallCalculations(ballX, 530, 0, 0, ballRadius, 
                target);
        AnimationsAndObjects panel = new AnimationsAndObjects(ballCalculations, target);
        
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
