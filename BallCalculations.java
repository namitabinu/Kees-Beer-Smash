import javax.swing.*;
import java.util.*;
import java.awt.*;


/** This is the code for all the calculations regarding the following:
 * 1. The modelling of the ping pong ball.
 * 2. The calculations of the ball's trajectory.
 * 3. The calculations of the ball's interaction with the targets and other objects.
 * 4. The calculation of the placement of the targets.
 * 
 */
public class BallCalculations {
    // Attributes of the ball
    private double x;
    private double y;
    private double velocityX;
    private double velocityY;
    private double radius;
    private Color color;
    private int screenWidth;
    private int screenHeight;
    private boolean isLaunched = false;

    // Constructor to initialize the ball's model
    /**
     * Constructor to initialize the ball's model.
     * @param x X-coordinate of the ball
     * @param y Y-coordinate of the ball
     * @param velocityX X-velocity of the ball
     * @param velocityY Y-velocity of the ball
     * @param radius Radius of the ball
     */
    public BallCalculations(double x, double y, double velocityX, double velocityY, 
        double radius) {
        this.x = x;
        this.y = y;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.radius = radius;
        this.color = Color.ORANGE;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getRadius() {
        return radius;
    }

    public void updatePosition() {
        if (!isLaunched) {
            return;
        }
        velocityY += 0.5; //Gravity
        x += velocityX;
        y += velocityY;
        
        checkBoundaries();
    }

    public void launchBall(double velX, double velY) {
        this.velocityX = velX;
        this.velocityY = velY;
        this.isLaunched = true;
    }

    public boolean isLaunched() {
        return isLaunched;
    }

    public void setScreenSize(int width, int height) {
        this.screenWidth = width;
        this.screenHeight = height;
    }

    public void checkBoundaries() {
        if (x - radius < 0 || x + radius > screenWidth) {
            velocityX = -velocityX; // Reverse X velocity
        }
        if (y - radius < 0 || y + radius > screenHeight) {
            velocityY = -velocityY; // Reverse Y velocity
        }
    }

}