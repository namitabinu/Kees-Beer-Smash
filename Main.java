import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Main {
    
    public static void main(String[] args) {
        int platformX = 50;
        int platformWidth = 100;
        int platformHeight = 20;
        double ballRadius = 30;
        double ballX = platformX + platformWidth / 2;
        //double ballY = platformHeight 
        BallCalculations ballCalculations = new BallCalculations(ballX, 500, 0, 0, ballRadius);
        AnimationsAndObjects panel = new AnimationsAndObjects(ballCalculations);
        
        JFrame frame = new JFrame("Pub Trivia Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.pack();
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