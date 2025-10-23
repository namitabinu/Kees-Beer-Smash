import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GameState {
    private double[] targetX;
    private double[] targetY;
    private double[] bombX;
    private double[] bombY;
    private int score;
    private String difficulty;
    private int timeRemaining;

    // ADD THESE: Ball state properties
    private double ballX;
    private double ballY;
    private double ballVelocityX;
    private double ballVelocityY;
    private boolean ballLaunched;
    private List<Point> ballTrajectory;
    private double[] trajectoryPointsX;
    private double[] trajectoryPointsY;

    // Default constructor for Jackson
    public GameState() {
        this.ballTrajectory = new ArrayList<>();
    }
    
    // Getters and setters
    public double[] getTargetX() { return targetX; }
    public void setTargetX(double[] targetX) { this.targetX = targetX; }
    
    public double[] getTargetY() { return targetY; }
    public void setTargetY(double[] targetY) { this.targetY = targetY; }
    
    public double[] getBombX() { return bombX; }
    public void setBombX(double[] bombX) { this.bombX = bombX; }
    
    public double[] getBombY() { return bombY; }
    public void setBombY(double[] bombY) { this.bombY = bombY; }
    
    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }
    
    public int getTimeRemaining() { return timeRemaining; }
    public void setTimeRemaining(int timeRemaining) { this.timeRemaining = timeRemaining; }
    
    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }


    
     // ADD THESE: Getters and setters for ball state
    public double getBallX() { return ballX; }
    public void setBallX(double ballX) { this.ballX = ballX; }
    
    public double getBallY() { return ballY; }
    public void setBallY(double ballY) { this.ballY = ballY; }
    
    public double getBallVelocityX() { return ballVelocityX; }
    public void setBallVelocityX(double ballVelocityX) { this.ballVelocityX = ballVelocityX; }
    
    public double getBallVelocityY() { return ballVelocityY; }
    public void setBallVelocityY(double ballVelocityY) { this.ballVelocityY = ballVelocityY; }
    
    public boolean isBallLaunched() { return ballLaunched; }
    public void setBallLaunched(boolean ballLaunched) { this.ballLaunched = ballLaunched; }
    
    public List<Point> getBallTrajectory() { return ballTrajectory; }
    public void setBallTrajectory(List<Point> ballTrajectory) { this.ballTrajectory = ballTrajectory; }
    
    // Helper methods for trajectory serialization
    public double[] getTrajectoryPointsX() { 
        if (ballTrajectory == null) return new double[0];
        double[] xPoints = new double[ballTrajectory.size()];
        for (int i = 0; i < ballTrajectory.size(); i++) {
            xPoints[i] = ballTrajectory.get(i).x;
        }
        return xPoints;
    }
    
    public double[] getTrajectoryPointsY() { 
        if (ballTrajectory == null) return new double[0];
        double[] yPoints = new double[ballTrajectory.size()];
        for (int i = 0; i < ballTrajectory.size(); i++) {
            yPoints[i] = ballTrajectory.get(i).y;
        }
        return yPoints;
    }
    
    public void setTrajectoryPointsX(double[] trajectoryPointsX) {
        // This is for deserialization - we'll handle it in setTrajectoryPointsY
    }
    
    public void setTrajectoryPointsY(double[] trajectoryPointsY) {
        // This is for deserialization - we'll handle trajectory reconstruction elsewhere
    }

}
