import javax.swing.*;
import java.awt.*;

public class AnimationsAndObjects extends JPanel{
    private BallCalculations ballCalculations;
    private Image backgroundImage;
    private int platformWidth = 500;
    private int platformHeight = 100;
    private Color platformColor = Color.decode("#FF0000");
    private Image slingshotImage;

    //Constructor
    public AnimationsAndObjects(BallCalculations ballCalculations) {
        this.ballCalculations = ballCalculations;
        this.setPreferredSize(new Dimension(800, 600));
        backgroundImage = new ImageIcon("Pub_Interior_Image.jpeg").getImage();
        slingshotImage = new ImageIcon("Slingshot.png").getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); //Clears the bg
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        } else {
            g.setColor(Color.BLUE);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
        ballCalculations.setScreenSize(getWidth(), getHeight()); //Sets screen size
        drawPlatform(g); //Draws the platform
        drawSlingshot(g); //Draws the slingshot
        drawBall(g); //Draws the ball

    }

    private void drawPlatform(Graphics g) {
        g.setColor(platformColor); //Sets the platform color to brown
        int platformX = 50;
        int platformY = getHeight() - 250; //Positions the platform near the bottom
        g.fillRect(platformX, platformY, platformWidth, platformHeight);
    }

    private void drawSlingshot(Graphics g) {
        if (slingshotImage != null) {
            int slingshotX = 150; //X position of the slingshot
            int slingshotY = getHeight() - platformHeight - 300; //Y position of the slingshot
            g.drawImage(slingshotImage, slingshotX, slingshotY, 100, 150, this);
        }
    }
    
    private void drawBall(Graphics g) {
        g.setColor(Color.ORANGE); //Sets the ball color to orange
        double x = ballCalculations.getX();
        double y = ballCalculations.getY();
        double radius = ballCalculations.getRadius();
    

        g.fillOval((int) (x - radius), (int) (y - radius), 
                   (int) (radius * 2), (int) (radius * 2));
        
    }
}