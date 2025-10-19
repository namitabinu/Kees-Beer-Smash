import java.awt.*;

public class Targets {
    private double x;
    private double y;
    private double width;
    private double height;
    private boolean isCorrect = false;
    private String letter;

    public Targets(double screenWidth, double targetY, double width, double height, String letter) {
        this.width = width;
        this.height = height;
        this.x = screenWidth - width - 250;
        this.y = targetY;
        this.letter = letter;
    }
    //Getters
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
    public String getLetter() {
        return letter;
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