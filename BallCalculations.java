import java.awt.*;
import java.util.*;
import javax.swing.*;


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
    private int screenWidth;
    private int screenHeight;
    private boolean isLaunched = false;
    private boolean isPulledBack = false;
    private double originalY;
    private double launchX;
    private double launchY;

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
        this.originalY = y;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.radius = radius;
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
        velocityY += 0.8; //Gravity

        // Air resistance for damping
        velocityX *= 0.999;
        velocityY *= 0.998;

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
        if (!isLaunched) {
            this.launchX = x;
            this.launchY = y;
            // Calculate velocity based on displacement from original position
            calculateLaunchVelocity();
            isLaunched = true;
            isPulledBack = false;
        }
    }

    private void calculateLaunchVelocity() {
        // The further the ball is pulled back, the stronger the launch
        double displacementY = originalY - y;
        double powerFactor = 0.7; // Controls the launch power
        
        // Minimum X velocity plus a slight boost for pulling back furhter
        velocityX = 45.0 + (displacementY * 0.3);
        velocityY = displacementY * powerFactor;
    }

    public double getLaunchX() {
        return launchX;
    }

    public double getLaunchY() {
        return launchY;
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
            velocityX = -velocityX * 0.7; // Bounce with damping
        }
        // When it hits the roof
        if (y - radius < 0) {
            y = radius;
            velocityY = -velocityY * 0.8; //Damped bounce from the roof
        }
    
        // Ground stops the ball (make it reset later)
        if (y + radius > screenHeight) {
            y = screenHeight - radius;
            velocityX *= 0.8; // Reduces horizontal speed when hitting the ground
            velocityY = 0;    // Stops the ball from bouncing (maybe change later)
        }
    }
}
