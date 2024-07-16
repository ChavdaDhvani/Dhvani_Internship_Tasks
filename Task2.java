import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Random;

public class NumberGuessingGame extends Application {
    private int randomNumber; 
    private int attemptsLeft; 
    private int score; 
    private int round; 
    private final int maxAttempts = 10; 

    private Label instructionLabel; 
    private TextField guessInput; 
    private Button guessButton; 
    private Label feedbackLabel; 
    private Label scoreLabel;

    @Override
    public void start(Stage primaryStage) {
        // Initial setup: Random number generate karo, attempts initialize karo, score and round initialize karo
        randomNumber = generateRandomNumber();
        attemptsLeft = maxAttempts;
        score = 0;
        round = 1;

        // UI elements setup
        instructionLabel = new Label("Guess a number between 1 and 100:");
        guessInput = new TextField();
        guessButton = new Button("Guess");
        feedbackLabel = new Label();
        scoreLabel = new Label("Score: " + score + " | Round: " + round);

        // Guess button par click handler set karo
        guessButton.setOnAction(e -> handleGuess());

        // Layout setup
        VBox root = new VBox(10, instructionLabel, guessInput, guessButton, feedbackLabel, scoreLabel);
        root.setPadding(new Insets(10));

        // Scene and stage setup
        Scene scene = new Scene(root, 300, 200);
        primaryStage.setTitle("Guess The Number Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleGuess() {
        String input = guessInput.getText();
        try {
            int guess = Integer.parseInt(input);

            // Input validation
            if (guess < 1 || guess > 100) {
                feedbackLabel.setText("Please enter a number between 1 and 100.");
                return;
            }

            attemptsLeft--; // Attempt decrement karo

            // Check if guess is correct
            if (guess == randomNumber) {
                feedbackLabel.setText("Correct! You've guessed the number.");
                score += calculatePoints(); 
                nextRound(); 
            } else if (guess < randomNumber) {
                feedbackLabel.setText("Higher! Attempts left: " + attemptsLeft);
            } else {
                feedbackLabel.setText("Lower! Attempts left: " + attemptsLeft);
            }

            // Check if attempts are over
            if (attemptsLeft == 0) {
                feedbackLabel.setText("Game Over! The number was " + randomNumber);
                nextRound(); 
            }

        } catch (NumberFormatException e) {
            feedbackLabel.setText("Please enter a valid number."); 
        }
    }

    // Random number generate karvanu function
    private int generateRandomNumber() {
        Random random = new Random();
        return random.nextInt(100) + 1;
    }

    // Points ganatri karvanu function
    private int calculatePoints() {
        return maxAttempts - attemptsLeft;
    }

    // Navu round sharu karva ni method
    private void nextRound() {
        round++;
        attemptsLeft = maxAttempts;
        randomNumber = generateRandomNumber();
        scoreLabel.setText("Score: " + score + " | Round: " + round);
        guessInput.clear(); 
    }

    public static void main(String[] args) {
        launch(args);
    }
}
