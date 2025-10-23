import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.*;
import javax.swing.Timer;

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

    // fields for score and timer
    private JLabel timerLabel;
    private JLabel scoreLabel;
    private int score = 0;
    private String lastAction = "";
    private long lastActionTime = 0;
    private int timeRemaining;
    private Timer gameTimer;
    private String difficulty;

    /**
     * A constructor to initialize the panel and load the images and background.
     */
    public AnimationsAndObjects(BallCalculations ballCalculations, Targets[] targets, Targets[] bombs,
            String difficulty) {
        this.ballCalculations = ballCalculations;
        this.targets = targets;
        this.bombs = bombs;
        this.difficulty = difficulty;
        this.setPreferredSize(new Dimension(800, 600));
        this.setLayout(new BorderLayout()); // changed layout to BorderLayout
        backgroundImage = new ImageIcon("Pub_Interior_Image.jpeg").getImage();
        slingshotImage = new ImageIcon("sling.png").getImage();
        cupImage = new ImageIcon("beer.png").getImage();
        bombImage = new ImageIcon("bomb.png").getImage();
        setupLabels();
        setupKeyControls();
        initializeTimer();
        startTimer();
    }

    private void initializeTimer() {
        // Set initial time based on difficulty
        switch (difficulty.toLowerCase()) {
            case "easy":
                timeRemaining = 90; // 90 seconds
                break;
            case "medium":
                timeRemaining = 60; // 60 seconds
                break;
            case "hard":
                timeRemaining = 30; // 30 seconds
                break;
            default:
                timeRemaining = 60; // Default to medium
        }
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
        updateTimerDisplay();
    }

    private void startTimer() {
        gameTimer = new Timer(1000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                timeRemaining--;
                updateTimerDisplay();

                // Check if time is up
                if (timeRemaining <= 0) {
                    gameTimer.stop();
                    timeUp();
                }
            }
        });
        gameTimer.start();
    }

    private void updateTimerDisplay() {
        String timeText = "Time: " + timeRemaining + "s";

        // Change color when time is running low
        if (timeRemaining <= 10) {
            timerLabel.setForeground(Color.RED);
        } else if (timeRemaining <= 30) {
            timerLabel.setForeground(Color.YELLOW);
        } else {
            timerLabel.setForeground(Color.WHITE);
        }

        timerLabel.setText(timeText);
    }

    // Method to call when a target is hit
    public void targetHit() {
        score += 10;
        lastAction = "Score! You get 10 points";
        lastActionTime = System.currentTimeMillis();
        updateScoreDisplay();
    }

    // Method to call when a bomb is hit
    public void bombHit() {
        score -= 5;
        lastAction = "Oh no! You lose 5 points";
        lastActionTime = System.currentTimeMillis();
        updateScoreDisplay();
    }

    // Method to update the score display
    private void updateScoreDisplay() {
        String displayText = "Score: " + score;

        // Show temporary message for 2 seconds after hit
        if (!lastAction.isEmpty() && (System.currentTimeMillis() - lastActionTime) < 2000) {
            displayText = lastAction + " | Score: " + score;
        }

        scoreLabel.setText(displayText);
    }

    private void timeUp() {
        // Game over logic
        timerLabel.setText("TIME UP!");
        timerLabel.setForeground(Color.RED);
        scoreLabel.setText("Final Score: " + score);

        // Show game over dialog
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(this,
                    "Time's up! Final Score: " + score,
                    "Game Over",
                    JOptionPane.INFORMATION_MESSAGE);
        });
    }

    // Getter for score
    public int getScore() {
        return score;
    }

    // Getter for time remaining
    public int getTimeRemaining() {
        return timeRemaining;
    }

    // Method to stop timer
    public void stopTimer() {
        if (gameTimer != null) {
            gameTimer.stop();
        }
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
