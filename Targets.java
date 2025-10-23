import java.awt.*;

public class Targets {
    private double x;
    private double y;
    private double width;
    private double height;
    private boolean isCorrect = false;
    private boolean hit = false;

    public Targets(double screenWidth, double targetY, double width, double height) {
        this.width = width;
        this.height = height;
        this.x = screenWidth - width - 250;
        this.y = targetY;
    }

    // Update position
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

    public boolean checkCollision(double ballX, double ballY, double ballRadius) {
        double closestX = Math.max(x, Math.min(ballX, x + width));
        double closestY = Math.max(y, Math.min(ballY, y + height));

        double distanceX = ballX - closestX;
        double distanceY = ballY - closestY;

        double distanceSquared = (distanceX * distanceX) + (distanceY * distanceY);
        return distanceSquared < (ballRadius * ballRadius);
    }

}