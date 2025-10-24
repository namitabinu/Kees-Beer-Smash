import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/** This class handles all game timing and scoring logic.
 */
public class TimerAndScore {
    private int score = 0;
    private String lastAction = "";
    private long lastActionTime = 0;
    private int timeRemaining;
    private Timer gameTimer;
    private String difficulty;
    
    // Game state callbacks
    private Runnable timeUpCallback;
    private Runnable scoreUpdateCallback;

    /** Constructor to initialize the game timer and score system.
     * 
     * @param difficulty game difficulty level
     * @param timeUpCallback Callback function when time runs out
     * @param scoreUpdateCallback Callback function when score updates
     */
    public TimerAndScore(String difficulty, Runnable timeUpCallback, Runnable scoreUpdateCallback) {
        this.difficulty = difficulty;
        this.timeUpCallback = timeUpCallback;
        this.scoreUpdateCallback = scoreUpdateCallback;
        initializeTimer();
    }

    /** Initializes the timer based on difficulty level.
     */
    private void initializeTimer() {
        switch (difficulty.toLowerCase()) {
            case "easy":
                timeRemaining = 90;
                break;
            case "medium":
                timeRemaining = 60;
                break;
            case "hard":
                timeRemaining = 30;
                break;
            default:
                timeRemaining = 60;
        }
    }

    /** Starts the game timer.
     */
    public void startTimer() {
        gameTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeRemaining--;
                
                // Check if time is up
                if (timeRemaining <= 0) {
                    gameTimer.stop();
                    if (timeUpCallback != null) {
                        timeUpCallback.run();
                    }
                }
                
                if (scoreUpdateCallback != null) {
                    scoreUpdateCallback.run();
                }
            }
        });
        gameTimer.start();
    }

    /** Stops the game timer.
     */
    public void stopTimer() {
        if (gameTimer != null) {
            gameTimer.stop();
        }
    }

    public int getTimeRemaining() {
        return timeRemaining;
    }

    public int getScore() {
        return score;
    }

    public String getLastAction() {
        return lastAction;
    }

    public long getLastActionTime() {
        return lastActionTime;
    }

    /** Sets a custom action message.
     * 
     * @param action action message to set
     */
    public void setLastAction(String action) {
        this.lastAction = action;
        this.lastActionTime = System.currentTimeMillis();
    }

    /** Updates the display text for timer and score.
     * 
     * @return Formatted display text
     */
    public String getDisplayText() {
        String displayText = "Score: " + score;

        // Show temporary message for 2 seconds after hit
        if (!lastAction.isEmpty() && (System.currentTimeMillis() - lastActionTime) < 2000) {
            displayText = lastAction + " | Score: " + score;
        }

        return displayText;
    }

    public String getTimerText() {
        return "Time: " + timeRemaining + "s";
    }

    public boolean isTimeRunningLow() {
        return timeRemaining <= 10;
    }

    public boolean isTimeWarning() {
        return timeRemaining <= 30;
    }

    /** Increases score when target is hit.
     * 
     */
    public void targetHit() {
        score += 10;
        lastAction = "Score! You get 10 points";
        lastActionTime = System.currentTimeMillis();
    }

    /** Decreases score when bomb is hit.
     */
    public void bombHit() {
        score -= 5;
        lastAction = "Oh no! You lose 5 points";
        lastActionTime = System.currentTimeMillis();
    }

    public void setTimeRemaining(int timeRemaining) {
        this.timeRemaining = timeRemaining;
    }

    public void setScore(int score) {
        this.score = score;
    }
}