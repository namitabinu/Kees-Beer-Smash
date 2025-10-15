import java.awt.*;
import java.awt.event.KeyEvent;
import javax.swing.*;
/**
 * This class handles all animations and drawings of objects in the game.
 */

public class AnimationsAndObjects extends JPanel {
    private BallCalculations ballCalculations;
    private Image backgroundImage;
    private int platformWidth = 500;
    private int platformHeight = 100;
    private Color platformColor = Color.decode("#FF0000");
    private Image slingshotImage;

    /**
     * A constructor to initialize the panel and load the images and background.
     */
    public AnimationsAndObjects(BallCalculations ballCalculations) {
        this.ballCalculations = ballCalculations;
        this.setPreferredSize(new Dimension(800, 600));
        backgroundImage = new ImageIcon("Pub_Interior_Image.jpeg").getImage();
        slingshotImage = new ImageIcon("Slingshot.png").getImage();

        setupKeyControls();
    }

    private void setupKeyControls() {
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        ballCalculations.pullBack(0, -5);
                        repaint();
                        break;
                    case KeyEvent.VK_DOWN:
                        ballCalculations.pullBack(0, 5);
                        repaint();
                        break;
                    case KeyEvent.VK_SPACE:
                        ballCalculations.launchBall(); 
                        repaint();
                        break;
                    default:
                        break;
                }
            }
        });
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

            drawSlingshotBand(g, slingshotX, slingshotY);
        }
    }

    private void drawSlingshotBand(Graphics g, int slingshotX, int slingshotY) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(5)); //thickness of the band
        double targetX = ballCalculations.isLaunched() 
                ? ballCalculations.getLaunchX() : ballCalculations.getX();
        double targetY = ballCalculations.isLaunched() 
                ? ballCalculations.getLaunchY() : ballCalculations.getY();

        int leftBandX = slingshotX + 20; //Left band attachment point
        int rightBandX = slingshotX + 80; //Right band attachment point
        int bandY = slingshotY + 30; //Band attachment height

        g2d.drawLine(leftBandX, bandY, (int) targetX, (int) targetY);
        g2d.drawLine(rightBandX, bandY, (int) targetX, (int) targetY);
    }
    
    private void drawBall(Graphics g) {
        g.setColor(Color.decode("#FF5C00")); //Sets the ball color to orange
        double x = ballCalculations.getX();
        double y = ballCalculations.getY();
        double radius = ballCalculations.getRadius();
    

        g.fillOval((int) (x - radius), (int) (y - radius), 
                   (int) (radius * 2), (int) (radius * 2));
        
    }
}
