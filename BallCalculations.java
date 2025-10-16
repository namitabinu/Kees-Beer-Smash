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
    private boolean isPulledBack = false;
    private double originalX;
    private double originalY;

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
        this.originalX = x;
        this.originalY = y;
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

    public void pullBack(double deltaX, double deltaY) {
        if (!isLaunched) {
            x += deltaX;
            y += deltaY;
            isPulledBack = true;
        }
    }

    public void launchBall() {
        if (isPulledBack && !isLaunched) {
            // Calculate velocity based on displacement from original position
            calculateLaunchVelocity();
            isLaunched = true;
            isPulledBack = false;
        }
    }

    private void calculateLaunchVelocity() {
        // The further the ball is pulled back, the stronger the launch
        double displacementX = originalX - x;
        double displacementY = originalY - y;
        double powerFactor = 0.3; // Adjust this value to control launch strength
        
        velocityX = 18.0;
        velocityY = displacementY * powerFactor;
    }

    public double getLaunchX() {
        return originalX;
    }

    public double getLaunchY() {
        return originalY;
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
