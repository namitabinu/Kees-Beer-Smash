/** Represents a target object in the game, either a beer mug or bomb.
 * 
 */
public class Targets {
    private double x;
    private double y;
    private double width;
    private double height;
    private boolean isCorrect = false;
    private boolean hit = false;

    /** Constructs a target object with specified positions and dimensions.
     * 
     * @param screenWidth width of game screen
     * @param targetY initial y-coordinate of the target
     * @param width width of target object
     * @param height height of target object
     */
    public Targets(double screenWidth, double targetY, double width, double height) {
        this.width = width;
        this.height = height;
        this.x = screenWidth - width - 250;
        this.y = targetY;
    }

    /** Updates the position of the target to the specified coordinates.
     * 
     * @param newX new x-coordinate position
     * @param newY new y-coordinate position
     */
    public void setPosition(double newX, double newY) {
        this.x = newX;
        this.y = newY;
        System.out.println("Target position set to: (" + newX + ", " + newY + ")");
    }

    // Getters
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public boolean isHit() {
        return hit;
    }

    /** Checks for collision between this target and the ball.
     * 
     * @param ballX x-coordinate of the ball's center
     * @param ballY y-coordinate of the ball's center
     * @param ballRadius radius of ball
     * @return true if ball collides with target, false otherwise
     */
    public boolean checkCollision(double ballX, double ballY, double ballRadius) {
        double closestX = Math.max(x, Math.min(ballX, x + width));
        double closestY = Math.max(y, Math.min(ballY, y + height));

        double distanceX = ballX - closestX;
        double distanceY = ballY - closestY;

        double distanceSquared = (distanceX * distanceX) + (distanceY * distanceY);
        return distanceSquared < (ballRadius * ballRadius);
    }

}