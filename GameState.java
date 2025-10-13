import java.io.Serializable;
import java.util.*;

public class GameState implements Serializable {
    private static final long serialVersionUID = 1L;

    private int score;
    private int selectedDifficulty; // 1=Easy, 2=Medium, 3=Hard
    private int currentQuestionNumber;
    private List<Integer> usedQuestionIds;
    private boolean gameCompleted;

    public GameState() {
        this.score = 0;
        this.selectedDifficulty = 1;
        this.currentQuestionNumber = 1;
        this.usedQuestionIds = new ArrayList<>();
        this.gameCompleted = false;
    }

    // Getters and setters
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getSelectedDifficulty() {
        return selectedDifficulty;
    }

    public void setSelectedDifficulty(int difficulty) {
        this.selectedDifficulty = difficulty;
    }

    public int getCurrentQuestionNumber() {
        return currentQuestionNumber;
    }

    public void setCurrentQuestionNumber(int number) {
        this.currentQuestionNumber = number;
    }

    public void incrementQuestionNumber() {
        this.currentQuestionNumber++;
    }

    public List<Integer> getUsedQuestionIds() {
        return usedQuestionIds;
    }

    public void addUsedQuestionId(int id) {
        usedQuestionIds.add(id);
    }

    public boolean isGameCompleted() {
        return gameCompleted;
    }

    public void setGameCompleted(boolean completed) {
        this.gameCompleted = completed;
    }

    // Get time limit based on selected difficulty
    public int getTimeLimit() {
        switch (selectedDifficulty) {
            case 1:
                return 30; // Easy: 30 seconds
            case 2:
                return 20; // Medium: 20 seconds
            case 3:
                return 15; // Hard: 15 seconds
            default:
                return 30;
        }
    }

    public String getDifficultyName() {
        switch (selectedDifficulty) {
            case 1:
                return "Easy";
            case 2:
                return "Medium";
            case 3:
                return "Hard";
            default:
                return "Easy";
        }
    }
}