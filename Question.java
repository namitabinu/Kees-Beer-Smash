import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class Question implements Serializable {
    private static final long serialVersionUID = 1L;

    private String question;
    private String correctAnswer;
    private ArrayList<String> wrongAnswers;

    public Question(String question, String correctAnswer, ArrayList wrongAnswers) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.wrongAnswers = wrongAnswers;
    }

    public ArrayList getAllAnswersShuffled() {
        ArrayList<String> allAnswers = new ArrayList<String>();
        allAnswers.add(correctAnswer);
        allAnswers.addAll(wrongAnswers);
        Collections.shuffle(allAnswers);
        return allAnswers;
    }

    public String getQuestion() {
        return question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public ArrayList<String> getWrongAnswers() {
        return wrongAnswers;
    }
}
