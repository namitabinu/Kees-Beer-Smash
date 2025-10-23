import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

/**
 * This is the code for all the calculations regarding the following:
 * 1. The modelling of the ping pong ball.
 * 2. The calculations of the ball's trajectory.
 * 3. The calculations of the ball's interaction with the targets and other
 * objects.
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
    private double originalX;
    private double originalY;
    private double launchX;
    private double launchY;
    public ArrayList<Point> trajectoryPoints; // Stores trajectory points
    public boolean showTrajectory;
    private Targets[] targets;
    private Targets[] bombs;
    private boolean collided = false;
    private Random random = new Random();

    // Constructor to initialize the ball's model
    /**
     * Constructor to initialize the ball's model.
     * 
     * @param x         X-coordinate of the ball
     * @param y         Y-coordinate of the ball
     * @param velocityX X-velocity of the ball
     * @param velocityY Y-velocity of the ball
     * @param radius    Radius of the ball
     */
    public BallCalculations(double x, double y, double velocityX, double velocityY,
            double radius, Targets[] targets, Targets[] bombs) {
        this.x = x;
        this.y = y;
        this.originalX = x;
        this.originalY = y;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.radius = radius;
        this.targets = targets;
        this.bombs = bombs;
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

    public double getLaunchX() {
        return launchX;
    }

    public double getLaunchY() {
        return launchY;
    }

    public boolean isLaunched() {
        return isLaunched;
    }

    public ArrayList<Point> getTrajectoryPoints() {
        return trajectoryPoints;
    }

    public boolean isShowTrajectory() {
        return showTrajectory;
    }

    // Add these methods to your BallCalculations class
    public double getVelocityX() {
        return velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setVelocity(double velocityX, double velocityY) {
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }

    public void setLaunched(boolean launched) {
        this.isLaunched = launched;
        this.showTrajectory = !launched;
    }

    public void updatePosition() {
        if (!isLaunched) {
            return;
        }
        velocityY += 0.8; // Gravity

        // Air resistance for damping
        velocityX *= 0.999;
        velocityY *= 0.998;

        x += velocityX;
        y += velocityY;

        checkBoundaries();
        // checkTargetCollision();

        if (shouldReset()) {
            resetBall();
        }
    }

    public void checkCollisions(AnimationsAndObjects panel) {
        // Check target collisions

        if (targets != null) {
            for (Targets target : targets) {
                if (target.checkCollision(x, y, radius) && !target.isHit()) {
                    panel.targetHit();
                    target.setHit(true);
                    collided = true;
                    System.out.println("Target hit! +10 points");
                }
            }
        }

        // Check bomb collisions
        if (bombs != null) {
            for (Targets bomb : bombs) {
                if (bomb.checkCollision(x, y, radius) && !bomb.isHit()) {
                    panel.bombHit();
                    bomb.setHit(true);
                    collided = true;
                    System.out.println("Bomb hit! -5 points");
                }
            }
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
        double predVelX = 45.0 + (displacementY * 0.3); // Predicted x velocity
        double predVelY = displacementY * powerFactor; // Predicted y velocity
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

    public void setScreenSize(int width, int height) {
        this.screenWidth = width;
        this.screenHeight = height;
    }

    public boolean shouldReset() {
        boolean tooSlow = Math.abs(velocityX) < 1.0 && Math.abs(velocityY) < 1.0
                && isLaunched;
        boolean hitGround = y + radius >= screenHeight && isLaunched;
        return tooSlow || hitGround || collided;
    }

    public void resetBall() {
        this.x = originalX;
        this.y = originalY;
        this.velocityX = 0;
        this.velocityY = 0;
        this.isLaunched = false;
        this.showTrajectory = true;
        this.trajectoryPoints.clear();

        randomizeTargetsAndBombs();

        // Resets hit status of ALL targets and bombs
        if (targets != null) {
            for (Targets target : targets) {
                target.setHit(false);
            }
        }
        if (bombs != null) {
            for (Targets bomb : bombs) {
                bomb.setHit(false);
            }
        }

        collided = false;
        calculateTrajectory();
    }

    // randomizes position of targets and bombs
    private void randomizeTargetsAndBombs() {
        if (screenWidth == 0 || screenHeight == 0) {
            System.out.println("Screen size not set yet, cannot randomize positions");
            return;
        }

        // safe area (right half of screen, below top label area)
        int labelAreaHeight = 100;
        int minX = screenWidth / 2;
        int maxX = screenWidth - 200;
        int minY = labelAreaHeight + 50;
        int maxY = screenHeight - 400;

        // ALL objects that will be on screen (targets and bombs)
        java.util.List<Targets> allScreenObjects = new ArrayList<>();
        if (targets != null) {
            allScreenObjects.addAll(java.util.Arrays.asList(targets));
        }
        if (bombs != null) {
            allScreenObjects.addAll(java.util.Arrays.asList(bombs));
        }

        // Randomize all objects with collision checking against current positions
        for (Targets currentObject : allScreenObjects) {
            double newX, newY;
            boolean positionValid;
            int attempts = 0;
            final int MAX_ATTEMPTS = 200;

            do {
                newX = minX + random.nextInt(maxX - minX);
                newY = minY + random.nextInt(maxY - minY);

                // Check if this new position overlaps with any other object on screen
                positionValid = true;
                for (Targets otherObject : allScreenObjects) {
                    // Skip checking against itself
                    if (otherObject == currentObject) {
                        continue;
                    }

                    // Check if this new position would overlap with another object's current
                    // position
                    if (rectanglesOverlap(newX, newY, currentObject.getWidth(), currentObject.getHeight(),
                            otherObject.getX(), otherObject.getY(), otherObject.getWidth(), otherObject.getHeight())) {
                        positionValid = false;
                        break;
                    }
                }

                attempts++;
                if (attempts > MAX_ATTEMPTS) {
                    System.out.println("Warning: Could not find non-overlapping position for object");
                    // Use edge position as fallback
                    newX = maxX - currentObject.getWidth() - 10;
                    newY = maxY - currentObject.getHeight() - 10;
                    break;
                }

            } while (!positionValid);

            // Set the new position
            currentObject.setPosition(newX, newY);
            System.out.println("Object moved to: (" + newX + ", " + newY + ")");
        }
    }

    // Helper method to check rectangle overlap
    private boolean rectanglesOverlap(double x1, double y1, double w1, double h1,
            double x2, double y2, double w2, double h2) {
        return x1 < x2 + w2 && x1 + w1 > x2 && y1 < y2 + h2 && y1 + h1 > y2;
    }

    public void checkBoundaries() {
        if (x - radius < 0 || x + radius > screenWidth) {
            velocityX = -velocityX * 0.7; // Bounce with damping
        }
        // When it hits the roof
        if (y - radius < 0) {
            y = radius;
            velocityY = -velocityY * 0.8; // Damped bounce from the roof
        }

        // Ground stops the ball (make it reset later)
        if (y + radius > screenHeight) {
            y = screenHeight - radius;
            velocityX *= 0.8; // Reduces horizontal speed when hitting the ground
            velocityY = 0; // Stops the ball from bouncing (maybe change later)
        }
    }
}