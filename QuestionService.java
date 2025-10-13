import java.io.*;
import java.util.*;

public class QuestionService {
    private List<Question> questions;
    private Random random;
    private GameState gameState;
    private static final String SAVE_FILE = "game_save.ser";
    private static final String QUESTIONS_FILE = "questions.ser";

    public QuestionService() {
        this.questions = new ArrayList<>();
        this.random = new Random();
        this.gameState = new GameState();
        loadQuestions();
        loadGameState();
    }

    // Serialization methods
    @SuppressWarnings("unchecked")
    private void loadQuestions() {
        try {
            File file = new File(QUESTIONS_FILE);
            if (file.exists()) {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                    questions = (List<Question>) ois.readObject();
                    System.out.println("Loaded " + questions.size() + " questions from file");
                }
            } else {
                createInitialQuestions();
                saveQuestions();
            }
        } catch (Exception e) {
            System.err.println("Error loading questions: " + e.getMessage());
            createInitialQuestions();
        }
    }

    private void saveQuestions() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(QUESTIONS_FILE))) {
            oos.writeObject(questions);
        } catch (Exception e) {
            System.err.println("Error saving questions: " + e.getMessage());
        }
    }

    public void saveGameState() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_FILE))) {
            oos.writeObject(gameState);
        } catch (Exception e) {
            System.err.println("Error saving game: " + e.getMessage());
        }
    }

    private void loadGameState() {
        try {
            File file = new File(SAVE_FILE);
            if (file.exists()) {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                    gameState = (GameState) ois.readObject();
                }
            }
        } catch (Exception e) {
            System.err.println("Error loading game: " + e.getMessage());
        }
    }

    // Get a random question for the selected difficulty
    public Question getRandomQuestionForCurrentDifficulty() {
        int difficulty = gameState.getSelectedDifficulty();
        List<Question> difficultyQuestions = getQuestionsByDifficulty(difficulty);
        List<Question> availableQuestions = new ArrayList<>();

        // Filter out used questions
        for (Question q : difficultyQuestions) {
            if (!gameState.getUsedQuestionIds().contains(q.getId())) {
                availableQuestions.add(q);
            }
        }

        // If all questions used, reset for this session
        if (availableQuestions.isEmpty()) {
            resetUsedQuestionsForDifficulty(difficulty);
            availableQuestions = new ArrayList<>(difficultyQuestions);
        }

        if (availableQuestions.isEmpty()) {
            return null;
        }

        Question selected = availableQuestions.get(random.nextInt(availableQuestions.size()));
        gameState.addUsedQuestionId(selected.getId());
        return selected;
    }

    private List<Question> getQuestionsByDifficulty(int difficulty) {
        List<Question> result = new ArrayList<>();
        for (Question q : questions) {
            if (q.getDifficulty() == difficulty) {
                result.add(q);
            }
        }
        return result;
    }

    private void resetUsedQuestionsForDifficulty(int difficulty) {
        List<Integer> toRemove = new ArrayList<>();
        for (Integer id : gameState.getUsedQuestionIds()) {
            for (Question q : questions) {
                if (q.getId() == id && q.getDifficulty() == difficulty) {
                    toRemove.add(id);
                    break;
                }
            }
        }
        gameState.getUsedQuestionIds().removeAll(toRemove);
    }

    // Game actions
    public void startNewGame(int difficulty) {
        gameState = new GameState();
        gameState.setSelectedDifficulty(difficulty);
        gameState.getUsedQuestionIds().clear();
        saveGameState();
    }

    public void correctAnswer() {
        gameState.setScore(gameState.getScore() + 10);
        gameState.incrementQuestionNumber();

        if (gameState.getCurrentQuestionNumber() > 10) {
            gameState.setGameCompleted(true);
        }

        saveGameState();
    }

    public void wrongAnswer() {
        gameState.incrementQuestionNumber();

        if (gameState.getCurrentQuestionNumber() > 10) {
            gameState.setGameCompleted(true);
        }

        saveGameState();
    }

    // Getters
    public GameState getGameState() {
        return gameState;
    }

    public boolean isGameActive() {
        return !gameState.isGameCompleted();
    }

    // Initial questions - 10 questions per difficulty level
    private void createInitialQuestions() {
        // Difficulty 1 (Easy) - 10 questions
        questions.add(new Question(1, "Which planet is known as the Red Planet?", "Mars",
                Arrays.asList("Venus", "Earth", "Mercury"), 1));
        questions.add(new Question(2, "What type of beer is Guinness?", "Stout",
                Arrays.asList("Ale", "Porter", "Pilsner"), 1));
        questions.add(new Question(3, "What is hummus made from?", "Chickpeas",
                Arrays.asList("Lentils", "Soybeans", "Peas"), 1));
        questions.add(new Question(4, "How many times did Ross get divorced in Friends?", "3",
                Arrays.asList("4", "2", "5"), 1));
        questions.add(new Question(5, "What spirit is in a Screwdriver cocktail?", "Vodka",
                Arrays.asList("Rum", "Gin", "Tequila"), 1));
        questions.add(new Question(6, "Which city takes center stage in La La Land?", "Los Angeles",
                Arrays.asList("Chicago", "Seattle", "Boston"), 1));
        questions.add(
                new Question(7, "What is Japanese Sake made from?", "Rice", Arrays.asList("Corn", "Apple", "Sap"), 1));
        questions.add(new Question(8, "Which alcohol is in a Manhattan?", "Whiskey",
                Arrays.asList("Vodka", "Tequila", "Rum"), 1));
        questions.add(new Question(9, "How many time zones are there?", "24", Arrays.asList("12", "26", "25"), 1));
        questions.add(new Question(10, "What's the rarest color of rose?", "Blue",
                Arrays.asList("Red", "Yellow", "Green"), 1));

        // Difficulty 2 (Medium) - 10 questions
        questions.add(new Question(11, "Which British band features Chris Martin?", "Coldplay",
                Arrays.asList("Oasis", "Pink Floyd", "Queen"), 2));
        questions.add(new Question(12, "What is the best hand in poker?", "Royal Flush",
                Arrays.asList("Flush", "Straight", "Full House"), 2));
        questions.add(new Question(13, "What plant is Tequila made from?", "Agave",
                Arrays.asList("Wheat", "Barley", "Grape"), 2));
        questions.add(new Question(14, "Where does the Mojito cocktail come from?", "Cuba",
                Arrays.asList("Austria", "Czechia", "France"), 2));
        questions.add(new Question(15, "What is the most expensive spice?", "Saffron",
                Arrays.asList("Clove", "Ginger", "Oregano"), 2));
        questions.add(new Question(16, "What was the first foreign language film to win Best Picture?", "Parasite",
                Arrays.asList("The Artist", "Amélie", "Life is Beautiful"), 2));
        questions.add(new Question(17, "What animal is on Levi's logo?", "Horse",
                Arrays.asList("Puma", "Leopard", "Dog"), 2));
        questions.add(new Question(18, "On a UK Monopoly board, which is the most expensive?", "Mayfair",
                Arrays.asList("Strand", "Piccadilly", "Whitehall"), 2));
        questions.add(new Question(19, "Which country has the largest population in Europe?", "Russia",
                Arrays.asList("Turkey", "United Kingdom", "Germany"), 2));
        questions.add(new Question(20, "How many stars are on the EU flag?", "12", Arrays.asList("15", "10", "11"), 2));

        // Difficulty 3 (Hard) - 10 questions
        questions.add(new Question(21, "Which Dutch city is called the key city?", "Leiden",
                Arrays.asList("Zwolle", "Utrecht", "Haarlem"), 3));
        questions.add(new Question(22, "A Bodhrán is a drum from what country?", "Ireland",
                Arrays.asList("Scotland", "Greenland", "Iceland"), 3));
        questions.add(new Question(23, "A bellini calls for white peaches and?", "Prosecco",
                Arrays.asList("Champagne", "Gin", "Brandy"), 3));
        questions.add(new Question(24, "What is a group of flamingos called?", "Flamboyance",
                Arrays.asList("Murder", "Flock", "School"), 3));
        questions
                .add(new Question(25, "Where is a shrimp's heart?", "Head", Arrays.asList("Tail", "Legs", "Chest"), 3));
        questions.add(new Question(26, "Which country has the most pyramids?", "Sudan",
                Arrays.asList("Egypt", "Nigeria", "Ethiopia"), 3));
        questions.add(new Question(27, "From which flower does vanilla come?", "Orchid",
                Arrays.asList("Lily", "Rose", "Tulip"), 3));
        questions.add(new Question(28, "Who in Greek mythology is the god of wine?", "Dionysus",
                Arrays.asList("Zeus", "Apollo", "Hermes"), 3));
        questions.add(new Question(29, "What makes a Martini 'Dirty'?", "Olive Brine",
                Arrays.asList("Syrup", "Lemon", "Bitters"), 3));
        questions.add(new Question(30, "What is the largest nut in the world?", "Coconut",
                Arrays.asList("Walnut", "Macadamia", "Almond"), 3));

        System.out.println("Created " + questions.size() + " questions (10 per difficulty level)");
    }
}