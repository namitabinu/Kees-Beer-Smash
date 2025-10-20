import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class QuestionService {
    private ArrayList allQuestions;
    private ArrayList currentGameQuestions;
    private Random random;
    private int score;
    private int selectedDifficulty;
    private int questionsAnswered;
    private int correctAnswers;
    private int totalQuestions;
    private static final String QUESTIONS_FILE = "questions.dat";

    public QuestionService() {
        this.allQuestions = new ArrayList();
        this.currentGameQuestions = new ArrayList();
        this.random = new Random();
        this.score = 0;
        this.questionsAnswered = 0;
        this.correctAnswers = 0;
        this.totalQuestions = 0;
        loadQuestions();
    }

    private void loadQuestions() {
        try {
            File file = new File(QUESTIONS_FILE);
            if (file.exists()) {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                allQuestions = (ArrayList) ois.readObject();
                ois.close();
                System.out.println("Loaded " + allQuestions.size() + " questions");
            } else {
                createAllQuestions();
                saveQuestions();
            }
        } catch (Exception e) {
            System.err.println("Error loading questions: " + e.getMessage());
            createAllQuestions();
        }
    }

    private void saveQuestions() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(QUESTIONS_FILE));
            oos.writeObject(allQuestions);
            oos.close();
            System.out.println("Saved questions to file");
        } catch (Exception e) {
            System.err.println("Error saving questions: " + e.getMessage());
        }
    }

    public void startNewGame(int difficulty) {
        this.selectedDifficulty = difficulty;
        this.score = 0;
        this.questionsAnswered = 0;
        this.correctAnswers = 0;
        this.currentGameQuestions.clear();

        ArrayList shuffledQuestions = new ArrayList(allQuestions);
        Collections.shuffle(shuffledQuestions);

        this.currentGameQuestions = shuffledQuestions;
        this.totalQuestions = currentGameQuestions.size();

        System.out.println("Started new game with " + totalQuestions + " random questions");
    }

    public Question getCurrentQuestion() {
        if (questionsAnswered < totalQuestions) {
            return (Question) currentGameQuestions.get(questionsAnswered);
        }
        return null;
    }

    public boolean checkAnswer(String selectedAnswer) {
        if (questionsAnswered >= totalQuestions)
            return false;

        Question current = (Question) currentGameQuestions.get(questionsAnswered);
        boolean isCorrect = selectedAnswer.equals(current.getCorrectAnswer());

        if (isCorrect) {
            score += 10;
            correctAnswers++;
        }

        questionsAnswered++;
        return isCorrect;
    }

    public int getTimeLimit() {
        switch (selectedDifficulty) {
            case 1:
                return 90;
            case 2:
                return 60;
            case 3:
                return 30;
            default:
                return 90;
        }
    }

    public boolean isGameOver() {
        return questionsAnswered >= totalQuestions;
    }

    public int getScore() {
        return score;
    }

    public int getQuestionsAnswered() {
        return questionsAnswered;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public double getPercentage() {
        return totalQuestions > 0 ? (double) correctAnswers / totalQuestions * 100 : 0;
    }

    private void createAllQuestions() {
        allQuestions.add(new Question("Which planet is known as the Red Planet?", "Mars",
                createArrayList("Venus", "Earth", "Mercury")));
        allQuestions.add(
                new Question("What type of beer is Guinness?", "Stout", createArrayList("Ale", "Porter", "Pilsner")));
        allQuestions.add(
                new Question("What is hummus made from?", "Chickpeas", createArrayList("Lentils", "Soybeans", "Peas")));
        allQuestions.add(
                new Question("How many times did Ross get divorced in Friends?", "3", createArrayList("4", "2", "5")));
        allQuestions.add(new Question("What spirit is in a Screwdriver cocktail?", "Vodka",
                createArrayList("Rum", "Gin", "Tequila")));
        allQuestions.add(new Question("Which city takes center stage in La La Land?", "Los Angeles",
                createArrayList("Chicago", "Seattle", "Boston")));
        allQuestions
                .add(new Question("What is Japanese Sake made from?", "Rice", createArrayList("Corn", "Apple", "Sap")));
        allQuestions.add(new Question("Which alcohol is in a Manhattan?", "Whiskey",
                createArrayList("Vodka", "Tequila", "Rum")));
        allQuestions.add(new Question("How many time zones are there?", "24", createArrayList("12", "26", "25")));
        allQuestions.add(
                new Question("What's the rarest color of rose?", "Blue", createArrayList("Red", "Yellow", "Green")));
        allQuestions.add(new Question("Which British band features Chris Martin?", "Coldplay",
                createArrayList("Oasis", "Pink Floyd", "Queen")));
        allQuestions.add(new Question("What is the best hand in poker?", "Royal Flush",
                createArrayList("Flush", "Straight", "Full House")));
        allQuestions.add(
                new Question("What plant is Tequila made from?", "Agave", createArrayList("Wheat", "Barley", "Grape")));
        allQuestions.add(new Question("Where does the Mojito cocktail come from?", "Cuba",
                createArrayList("Austria", "Czechia", "France")));
        allQuestions.add(new Question("What is the most expensive spice?", "Saffron",
                createArrayList("Clove", "Ginger", "Oregano")));
        allQuestions.add(new Question("What was the first foreign language film to win Best Picture?", "Parasite",
                createArrayList("The Artist", "Amélie", "Life is Beautiful")));
        allQuestions.add(
                new Question("What animal is on Levi's logo?", "Horse", createArrayList("Puma", "Leopard", "Dog")));
        allQuestions.add(new Question("On a UK Monopoly board, which is the most expensive?", "Mayfair",
                createArrayList("Strand", "Piccadilly", "Whitehall")));
        allQuestions.add(new Question("Which country has the largest population in Europe?", "Russia",
                createArrayList("Turkey", "United Kingdom", "Germany")));
        allQuestions.add(new Question("How many stars are on the EU flag?", "12", createArrayList("15", "10", "11")));
        allQuestions.add(new Question("Which Dutch city is called the key city?", "Leiden",
                createArrayList("Zwolle", "Utrecht", "Haarlem")));
        allQuestions.add(new Question("A Bodhrán is a drum from what country?", "Ireland",
                createArrayList("Scotland", "Greenland", "Iceland")));
        allQuestions.add(new Question("A bellini calls for white peaches and?", "Prosecco",
                createArrayList("Champagne", "Gin", "Brandy")));
        allQuestions.add(new Question("What is a group of flamingos called?", "Flamboyance",
                createArrayList("Murder", "Flock", "School")));
        allQuestions.add(new Question("Where is a shrimp's heart?", "Head", createArrayList("Tail", "Legs", "Chest")));
        allQuestions.add(new Question("Which country has the most pyramids?", "Sudan",
                createArrayList("Egypt", "Nigeria", "Ethiopia")));
        allQuestions.add(new Question("From which flower does vanilla come?", "Orchid",
                createArrayList("Lily", "Rose", "Tulip")));
        allQuestions.add(new Question("Who in Greek mythology is the god of wine?", "Dionysus",
                createArrayList("Zeus", "Apollo", "Hermes")));
        allQuestions.add(new Question("What makes a Martini 'Dirty'?", "Olive Brine",
                createArrayList("Syrup", "Lemon", "Bitters")));
        allQuestions.add(new Question("What is the largest nut in the world?", "Coconut",
                createArrayList("Walnut", "Macadamia", "Almond")));

        System.out.println("Created " + allQuestions.size() + " questions");
    }

    private ArrayList createArrayList(String... items) {
        ArrayList list = new ArrayList();
        for (String item : items) {
            list.add(item);
        }
        return list;
    }
}
