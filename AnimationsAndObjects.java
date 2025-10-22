import java.awt.*;
import java.awt.event.KeyEvent;
import javax.swing.*;

/**
 * This class handles all animations and drawings of objects in the game.
 */

public class AnimationsAndObjects extends JPanel {
    private BallCalculations ballCalculations;
    private Image backgroundImage;
    private Targets[] targets;
    private Targets[] bombs;
    private int platformWidth = 500;
    private int platformHeight = 100;
    private Color platformColor = Color.decode("#FF0000");
    private Image slingshotImage;
    private Image cupImage;
    private Image bombImage;

    /**
     * A constructor to initialize the panel and load the images and background.
     */
    public AnimationsAndObjects(BallCalculations ballCalculations, Targets[] targets, Targets[] bombs) {
        this.ballCalculations = ballCalculations;
        this.targets = targets;
        this.bombs = bombs;
        this.setPreferredSize(new Dimension(800, 600));
        backgroundImage = new ImageIcon("Pub_Interior_Image.jpeg").getImage();
        slingshotImage = new ImageIcon("sling.png").getImage();
        cupImage = new ImageIcon("beer.png").getImage();
        bombImage = new ImageIcon("bomb.png").getImage();
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
        super.paintComponent(g); // Clears the bg
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        } else {
            g.setColor(Color.BLUE);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
        ballCalculations.setScreenSize(getWidth(), getHeight()); // Sets screen size
        drawPlatform(g); // Draws the platform
        drawSlingshot(g); // Draws the slingshot
        drawBall(g); // Draws the ball
        drawTrajectory(g); // Draws the trajectory
        drawTargets(g); // Draws the targets
        drawBombs(g); // Draws the bombs
    }

    private void drawPlatform(Graphics g) {
        g.setColor(platformColor); // Sets the platform color to brown
        int platformX = 50;
        int platformY = getHeight() - 250; // Positions the platform near the bottom
        g.fillRect(platformX, platformY, platformWidth, platformHeight);
    }

    private void drawSlingshot(Graphics g) {
        if (slingshotImage != null) {
            int slingshotX = 150; // X position of the slingshot
            int slingshotY = getHeight() - platformHeight - 350; // Y position of the slingshot
            g.drawImage(slingshotImage, slingshotX, slingshotY, 200, 250, this);

            drawSlingshotBand(g, slingshotX, slingshotY);
        }
    }

    private void drawSlingshotBand(Graphics g, int slingshotX, int slingshotY) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(18)); // thickness of the band
        double targetX = ballCalculations.isLaunched()
                ? ballCalculations.getLaunchX()
                : ballCalculations.getX();
        double targetY = ballCalculations.isLaunched()
                ? ballCalculations.getLaunchY()
                : ballCalculations.getY();

        int leftBandX = slingshotX + 50; // Left band attachment point
        int rightBandX = slingshotX + 120; // Right band attachment point
        int bandY = slingshotY + 80; // Band attachment height

        g2d.drawLine(leftBandX, bandY, (int) targetX, (int) targetY);
        g2d.drawLine(rightBandX, bandY, (int) targetX, (int) targetY);
    }

    private void drawBall(Graphics g) {
        g.setColor(Color.decode("#FF5C00")); // Sets the ball color to orange
        double x = ballCalculations.getX();
        double y = ballCalculations.getY();
        double radius = ballCalculations.getRadius();

        g.fillOval((int) (x - radius), (int) (y - radius),
                (int) (radius * 2), (int) (radius * 2));

    }

    private void drawTargets(Graphics g) {
        for (Targets target : targets) {
            int x = (int) target.getX();
            int y = (int) target.getY();
            int width = (int) target.getWidth();
            int height = (int) target.getHeight();

            if (cupImage != null) {
                g.drawImage(cupImage, x, y, width, height, this);
            } else {
                // Fallback drawing if targets image isn't loaded
                g.setColor(Color.RED);
                g.fillRect(x, y, width, height);
            }
            // drawLetter(g, target, x, y, width, height);
        }
    }

    private void drawBombs(Graphics g) {
        for (Targets bomb : bombs) {
            int x = (int) bomb.getX();
            int y = (int) bomb.getY();
            int width = (int) bomb.getWidth();
            int height = (int) bomb.getHeight();

            if (bombImage != null) {
                g.drawImage(bombImage, x, y, width, height, this);
            } else {
                // Fallback drawing if bomb image isn't loaded
                g.setColor(Color.BLACK);
                g.fillOval(x, y, width, height);
                g.setColor(Color.RED);
                g.fillOval(x + width / 4, y + height / 4, width / 2, height / 2);
            }
        }
    }

    /*
     * private void drawLetter(Graphics g, Targets target, int x, int y, int width,
     * int height) {
     * g.setColor(Color.WHITE);
     * g.setFont(new Font("Arial", Font.BOLD, 40));
     * FontMetrics fm = g.getFontMetrics();
     * String letter = target.getLetter();
     * int textWidth = fm.stringWidth(letter);
     * int textHeight = fm.getAscent();
     * int textX = x + (width - textWidth) / 2;
     * int textY = y + (height + textHeight) / 2 - 10;
     * g.drawString(letter, textX, textY);
     * }
     */

    private void drawTrajectory(Graphics g) {
        if (ballCalculations.showTrajectory) {
            g.setColor(Color.WHITE);
            for (Point dot : ballCalculations.trajectoryPoints) {
                g.fillOval(dot.x, dot.y, 8, 8);
            }
        }
    }
}
