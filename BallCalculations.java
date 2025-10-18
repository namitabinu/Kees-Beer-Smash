import java.awt.Point;
import java.util.ArrayList;

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
    private double originalY;
    private double launchX;
    private double launchY;
    public ArrayList<Point> trajectoryPoints; // Stores trajectory points
    public boolean showTrajectory;
    private Targets target;

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
        double radius, Targets target) {
        this.x = x;
        this.y = y;
        this.originalY = y;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.radius = radius;
        this.target = target;
        this.trajectoryPoints = new ArrayList<>();
        this.showTrajectory = true;
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

    public ArrayList<Point> getTrajectoryPoints() {
        return trajectoryPoints;
    }

    public boolean isShowTrajectory() {
        return showTrajectory;
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
        checkTargetCollision();
    }

    private void checkTargetCollision() {
        if (target != null && target.checkCollision(x, y, radius)) {
            System.out.println("Hit!");
        }
    }

    public void pullBack(double deltaX, double deltaY) {
        if (!isLaunched) {
            x += deltaX;
            y += deltaY;

            calculateTrajectory();
            showTrajectory = true;
        }
    }

    private void calculateTrajectory() {
        trajectoryPoints = new ArrayList<>();

        double displacementY = originalY - y;
        double powerFactor = 0.7;
        double predVelX = 45.0 + (displacementY * 0.3); //Predicted x velocity
        double predVelY = displacementY * powerFactor; //Predicted y velocity
        double predX = x;
        double predY = y;
 

        double timeStep = 1.1;
        double maxTime = 50.0;
        for (double t = 0; t < maxTime; t += timeStep) {
            predVelY += 0.98 * 0.5; // Gravity effect (Fine tuned for accuracy)
            predVelX *= Math.pow(0.999, timeStep / 0.1); // Air resistance
            predVelY *= Math.pow(0.998, timeStep / 0.1); // Air resistance

            predX += predVelX * timeStep;
            predY += predVelY * timeStep;

            trajectoryPoints.add(new Point((int) predX, (int) predY));

            // Stop if it hits the ground
            if (predY > screenHeight
                    || predX < 0 || predX > screenWidth) {
                break;
            }
        }
    }

    public void launchBall() {
        if (!isLaunched) {
            this.launchX = x;
            this.launchY = y;
            // Calculate velocity based on displacement from original position
            calculateLaunchVelocity();
            isLaunched = true;
            showTrajectory = false; // Hides trajectory after launch
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
