import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import javax.swing.*;

/**
 * This class handles all animations and drawings of objects in the game.
 */

public class AnimationsAndObjects extends JPanel {
    private BallCalculations ballCalculations;
    private Image backgroundImage;
    private Targets[] targets;
    private Targets[] bombs;
    private int platformHeight = 100;
    private Image slingshotImage;
    private Image cupImage;
    private Image bombImage;
    private JLabel timerLabel;
    private JLabel scoreLabel;
    private TimerAndScore gameTimerAndScore;
    private String difficulty;
    private GameStateManager gameStateManager;

    /**
     * Constructor to initialize the panel, load images and background.
     * 
     * @param ballCalculations Object for ball calculations
     * @param targets          Array of target objects
     * @param bombs            Array of bomb objects
     * @param difficulty       Stores the game difficulty level
     */
    public AnimationsAndObjects(BallCalculations ballCalculations, Targets[] targets,
            Targets[] bombs, String difficulty) {
        this.ballCalculations = ballCalculations;
        this.targets = targets;
        this.bombs = bombs;
        this.difficulty = difficulty;
        this.setPreferredSize(new Dimension(800, 600));
        this.setLayout(new BorderLayout()); // changed layout to BorderLayout
        this.gameStateManager = new GameStateManager();
        this.gameTimerAndScore = new TimerAndScore(difficulty, this::timeUp, this::updateDisplay);

        backgroundImage = new ImageIcon("Pictures/Pub_Interior_Image.jpeg").getImage();
        slingshotImage = new ImageIcon("Pictures/sling.png").getImage();
        cupImage = new ImageIcon("Pictures/beer.png").getImage();
        bombImage = new ImageIcon("Pictures/bomb.png").getImage();

        setupLabels();
        setupKeyControls();
        startTimer();
    }

    private void setupLabels() {
        // Create a panel to hold both labels
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.setBackground(new Color(0, 0, 0, 180)); // Semi-transparent black
        topPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        // Timer label at the top
        timerLabel = new JLabel("", JLabel.CENTER);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 28));
        timerLabel.setForeground(Color.WHITE);
        timerLabel.setOpaque(false);

        // Score label below timer
        scoreLabel = new JLabel("Score: 0", JLabel.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setOpaque(false);

        topPanel.add(timerLabel, BorderLayout.NORTH);
        topPanel.add(scoreLabel, BorderLayout.SOUTH);

        this.add(topPanel, BorderLayout.NORTH);
        updateDisplay();
    }

    private void startTimer() {
        gameTimerAndScore.startTimer();
    }

    private void updateDisplay() {
        // Update timer display with color coding
        String timeText = gameTimerAndScore.getTimerText();
        if (gameTimerAndScore.isTimeRunningLow()) {
            timerLabel.setForeground(Color.RED);
        } else if (gameTimerAndScore.isTimeWarning()) {
            timerLabel.setForeground(Color.YELLOW);
        } else {
            timerLabel.setForeground(Color.WHITE);
        }
        timerLabel.setText(timeText);

        // Update score display
        scoreLabel.setText(gameTimerAndScore.getDisplayText());
    }

    /**
     * Called when a target is hit.
     */
    public void targetHit() {
        gameTimerAndScore.targetHit();
        updateDisplay();
    }

    /**
     * Called when a bomb is hit.
     */
    public void bombHit() {
        gameTimerAndScore.bombHit();
        updateDisplay();
    }

    @SuppressWarnings("unused")
    private void timeUp() {
        // Game over logic
        timerLabel.setText("TIME UP!");
        timerLabel.setForeground(Color.RED);
        scoreLabel.setText("Final Score: " + gameTimerAndScore.getScore());

        // Show game over dialog
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(this,
                    "Time's up! Final Score: " + gameTimerAndScore.getScore(),
                    "Game Over",
                    JOptionPane.INFORMATION_MESSAGE);
        });
    }

    /**
     * Saves the game state and throws error if unable to.
     */
    public void saveGame() {
        try {
            GameState gameState = createGameState();
            gameStateManager.saveGame(gameState);

            // Show confirmation
            gameTimerAndScore.setLastAction("Game Saved!");
            updateDisplay();

        } catch (IOException e) {
            System.err.println("Failed to save game: " + e.getMessage());
            gameTimerAndScore.setLastAction("Save Failed!");
            updateDisplay();
        }
    }

    /**
     * Loads the game state and throws error if unable to.
     */
    public boolean loadGame() {
        try {
            GameState gameState = gameStateManager.loadGame();
            if (gameState != null) {
                restoreGameState(gameState);

                // Show confirmation
                gameTimerAndScore.setLastAction("Game Loaded!");
                updateDisplay();
                repaint();

                return true;
            } else {
                gameTimerAndScore.setLastAction("No Save File Found");
                updateDisplay();
            }
        } catch (IOException e) {
            System.err.println("Failed to load game: " + e.getMessage());
            gameTimerAndScore.setLastAction("Load Failed!");
            updateDisplay();
        }
        return false;
    }

    private GameState createGameState() {
        GameState state = new GameState();
        state.setScore(gameTimerAndScore.getScore());
        state.setTimeRemaining(gameTimerAndScore.getTimeRemaining());
        state.setDifficulty(difficulty);

        // Save ball state
        state.setBallX(ballCalculations.getX());
        state.setBallY(ballCalculations.getY());
        state.setBallVelocityX(ballCalculations.getVelocityX());
        state.setBallVelocityY(ballCalculations.getVelocityY());
        state.setBallLaunched(ballCalculations.isLaunched());

        // Save target positions
        double[] targetX = new double[targets.length];
        double[] targetY = new double[targets.length];
        for (int i = 0; i < targets.length; i++) {
            targetX[i] = targets[i].getX();
            targetY[i] = targets[i].getY();
        }
        state.setTargetX(targetX);
        state.setTargetY(targetY);

        // Save bomb positions
        double[] bombX = new double[bombs.length];
        double[] bombY = new double[bombs.length];
        for (int i = 0; i < bombs.length; i++) {
            bombX[i] = bombs[i].getX();
            bombY[i] = bombs[i].getY();
        }
        state.setBombX(bombX);
        state.setBombY(bombY);

        return state;
    }

    private void restoreGameState(GameState state) {
        // Restore game progress
        gameTimerAndScore.setScore(state.getScore());
        gameTimerAndScore.setTimeRemaining(state.getTimeRemaining());

        // Restore ball state
        ballCalculations.setPosition(state.getBallX(), state.getBallY());
        ballCalculations.setVelocity(state.getBallVelocityX(), state.getBallVelocityY());
        ballCalculations.setLaunched(state.isBallLaunched());

        ballCalculations.showTrajectory = !state.isBallLaunched();

        // Restore target positions
        double[] targetX = state.getTargetX();
        double[] targetY = state.getTargetY();
        for (int i = 0; i < targets.length && i < targetX.length; i++) {
            targets[i].setPosition(targetX[i], targetY[i]);
        }

        // Restore bomb positions
        double[] bombX = state.getBombX();
        double[] bombY = state.getBombY();
        for (int i = 0; i < bombs.length && i < bombX.length; i++) {
            bombs[i].setPosition(bombX[i], bombY[i]);
        }

        updateDisplay();
    }

    // Getter for score
    public int getScore() {
        return gameTimerAndScore.getScore();
    }

    // Getter for time remaining
    public int getTimeRemaining() {
        return gameTimerAndScore.getTimeRemaining();
    }

    // Method to stop timer
    public void stopTimer() {
        gameTimerAndScore.stopTimer();
    }

    private void setupKeyControls() {
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        ballCalculations.pullBack(0, -5);
                        repaint();
                        break;
                    case KeyEvent.VK_DOWN:
                        ballCalculations.pullBack(0, 5);
                        repaint();
                        break;
                    case KeyEvent.VK_SPACE:
                        ballCalculations.launchBall();
                        repaint();
                        break;
                    case KeyEvent.VK_S:
                        if (e.isControlDown()) { // Ctrl+S to save
                            saveGame();
                        }
                        break;
                    case KeyEvent.VK_L:
                        if (e.isControlDown()) { // Ctrl+L to load
                            loadGame();
                        }
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Clears the bg
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        } else {
            g.setColor(Color.BLUE);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
        ballCalculations.setScreenSize(getWidth(), getHeight()); // Sets screen size
        drawSlingshot(g); // Draws the slingshot
        drawBall(g); // Draws the ball
        drawTrajectory(g); // Draws the trajectory
        drawTargets(g); // Draws the targets
        drawBombs(g); // Draws the bombs
    }

    private void drawSlingshot(Graphics g) {
        if (slingshotImage != null) {
            int slingshotX = 150; // X position of the slingshot
            int slingshotY = getHeight() - platformHeight - 350; // Y position of the slingshot
            g.drawImage(slingshotImage, slingshotX, slingshotY, 200, 250, this);

            drawSlingshotBand(g, slingshotX, slingshotY);
        }
    }

    private void drawSlingshotBand(Graphics g, int slingshotX, int slingshotY) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.decode("#8A3324"));
        g2d.setStroke(new BasicStroke(18)); // thickness of the band
        double targetX = ballCalculations.isLaunched()
                ? ballCalculations.getLaunchX()
                : ballCalculations.getX();
        double targetY = ballCalculations.isLaunched()
                ? ballCalculations.getLaunchY()
                : ballCalculations.getY();

        int leftBandX = slingshotX + 50; // Left band attachment point
        int rightBandX = slingshotX + 120; // Right band attachment point
        int bandY = slingshotY + 80; // Band attachment height

        g2d.drawLine(leftBandX, bandY, (int) targetX, (int) targetY);
        g2d.drawLine(rightBandX, bandY, (int) targetX, (int) targetY);
    }

    private void drawBall(Graphics g) {
        g.setColor(Color.decode("#FF5C00")); // Sets the ball color to orange
        double x = ballCalculations.getX();
        double y = ballCalculations.getY();
        double radius = ballCalculations.getRadius();

        g.fillOval((int) (x - radius), (int) (y - radius),
                (int) (radius * 2), (int) (radius * 2));

    }

    private void drawTargets(Graphics g) {
        for (Targets target : targets) {
            int x = (int) target.getX();
            int y = (int) target.getY();
            int width = (int) target.getWidth();
            int height = (int) target.getHeight();

            if (cupImage != null) {
                g.drawImage(cupImage, x, y, width, height, this);
            } else {
                // Fallback drawing if targets image isn't loaded
                g.setColor(Color.RED);
                g.fillRect(x, y, width, height);
            }
        }
    }

    private void drawBombs(Graphics g) {
        for (Targets bomb : bombs) {
            int x = (int) bomb.getX();
            int y = (int) bomb.getY();
            int width = (int) bomb.getWidth();
            int height = (int) bomb.getHeight();

            if (bombImage != null) {
                g.drawImage(bombImage, x, y, width, height, this);
            } else {
                // Fallback drawing if bomb image isn't loaded
                g.setColor(Color.BLACK);
                g.fillOval(x, y, width, height);
                g.setColor(Color.RED);
                g.fillOval(x + width / 4, y + height / 4, width / 2, height / 2);
            }
        }
    }

    private void drawTrajectory(Graphics g) {
        if (ballCalculations.showTrajectory) {
            g.setColor(Color.WHITE);
            for (Point dot : ballCalculations.trajectoryPoints) {
                g.fillOval(dot.x, dot.y, 8, 8);
            }
        }
    }
}
