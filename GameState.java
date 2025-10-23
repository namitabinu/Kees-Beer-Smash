import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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

    // Default constructor for Jackson
    public GameState() {
    }

    // Getters and setters
    public double[] getTargetX() {
        return targetX;
    }

    public void setTargetX(double[] targetX) {
        this.targetX = targetX;
    }

    public double[] getTargetY() {
        return targetY;
    }

    public void setTargetY(double[] targetY) {
        this.targetY = targetY;
    }

    public double[] getBombX() {
        return bombX;
    }

    public void setBombX(double[] bombX) {
        this.bombX = bombX;
    }

    public double[] getBombY() {
        return bombY;
    }

    public void setBombY(double[] bombY) {
        this.bombY = bombY;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTimeRemaining() {
        return timeRemaining;
    }

    public void setTimeRemaining(int timeRemaining) {
        this.timeRemaining = timeRemaining;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    // Getters and setters for ball state
    public double getBallX() {
        return ballX;
    }

    public void setBallX(double ballX) {
        this.ballX = ballX;
    }

    public double getBallY() {
        return ballY;
    }

    public void setBallY(double ballY) {
        this.ballY = ballY;
    }

    public double getBallVelocityX() {
        return ballVelocityX;
    }

    public void setBallVelocityX(double ballVelocityX) {
        this.ballVelocityX = ballVelocityX;
    }

    public double getBallVelocityY() {
        return ballVelocityY;
    }

    public void setBallVelocityY(double ballVelocityY) {
        this.ballVelocityY = ballVelocityY;
    }

    public boolean isBallLaunched() {
        return ballLaunched;
    }

    public void setBallLaunched(boolean ballLaunched) {
        this.ballLaunched = ballLaunched;
    }

}
