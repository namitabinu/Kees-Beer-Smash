import java.io.Serializable;
import java.util.*;

public class Question implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String question;
    private String correctAnswer;
    private List<String> wrongAnswers;
    private int difficulty; // 1 = Easy, 2 = Medium, 3 = Hard

    public Question(int id, String question, String correctAnswer,
            List<String> wrongAnswers, int difficulty) {
        this.id = id;
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.wrongAnswers = wrongAnswers;
        this.difficulty = difficulty;
    }

    public List<String> getAllAnswersShuffled() {
        List<String> allAnswers = new ArrayList<>();
        allAnswers.add(correctAnswer);
        allAnswers.addAll(wrongAnswers);
        Collections.shuffle(allAnswers);
        return allAnswers;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public List<String> getWrongAnswers() {
        return wrongAnswers;
    }

    public int getDifficulty() {
        return difficulty;
    }
}