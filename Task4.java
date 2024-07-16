import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Timer;
import java.util.TimerTask;

public class OnlineExamination extends Application {
    private Stage stage;
    private int count = 0;
    private int current = 0;
    private int x = 1;
    private int y = 1;
    private int now = 0;
    private int[] m = new int[10];
    private Timer timer = new Timer();
    private Label timerLabel;
    private RadioButton[] options = new RadioButton[4];
    private ToggleGroup group = new ToggleGroup();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        stage.setTitle("Online Examination");

        // Login Scene
        GridPane loginGrid = new GridPane();
        loginGrid.setPadding(new Insets(10));
        loginGrid.setVgap(10);
        loginGrid.setHgap(10);

        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Submit");

        loginGrid.add(usernameLabel, 0, 0);
        loginGrid.add(usernameField, 1, 0);
        loginGrid.add(passwordLabel, 0, 1);
        loginGrid.add(passwordField, 1, 1);
        loginGrid.add(loginButton, 1, 2);

        Scene loginScene = new Scene(loginGrid, 300, 200);

        // Exam Scene
        VBox examBox = new VBox(10);
        examBox.setPadding(new Insets(10));

        Label questionLabel = new Label();
        timerLabel = new Label("Time left: 600");
        Button nextButton = new Button("Save and Next");
        Button reviewButton = new Button("Save for later");

        examBox.getChildren().addAll(questionLabel, timerLabel);
        for (int i = 0; i < 4; i++) {
            options[i] = new RadioButton();
            options[i].setToggleGroup(group);
            examBox.getChildren().add(options[i]);
        }
        examBox.getChildren().addAll(nextButton, reviewButton);

        Scene examScene = new Scene(examBox, 600, 400);

        // Login Button Action
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (!password.isEmpty()) {
                startExam(examScene, questionLabel);
            } else {
                passwordField.setText("Enter Password");
            }
        });

        // Next Button Action
        nextButton.setOnAction(e -> {
            if (checkAnswer()) {
                count++;
            }
            current++;
            setQuestion(questionLabel);
            if (current == 9) {
                nextButton.setDisable(true);
                reviewButton.setText("Result");
            }
        });

        // Review Button Action
        reviewButton.setOnAction(e -> {
            if (reviewButton.getText().equals("Result")) {
                if (checkAnswer()) {
                    count++;
                }
                current++;
                showAlert("Score", "Score = " + count);
                stage.close();
            } else {
                Button reviewButtonNew = new Button("Review" + x);
                examBox.getChildren().add(reviewButtonNew);
                int index = x - 1;
                m[index] = current;
                x++;
                current++;
                setQuestion(questionLabel);
                reviewButtonNew.setOnAction(event -> {
                    if (checkAnswer()) {
                        count++;
                    }
                    now = current;
                    current = m[index];
                    setQuestion(questionLabel);
                    reviewButtonNew.setDisable(true);
                    current = now;
                });
                stage.setScene(examScene);
            }
        });

        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    private void startExam(Scene examScene, Label questionLabel) {
        stage.setScene(examScene);
        setQuestion(questionLabel);
        timer.scheduleAtFixedRate(new TimerTask() {
            int i = 600;

            @Override
            public void run() {
                javafx.application.Platform.runLater(() -> {
                    timerLabel.setText("Time left: " + i);
                    i--;
                    if (i < 0) {
                        timer.cancel();
                        timerLabel.setText("Time Out");
                        showAlert("Time's up!", "Your exam has ended.");
                        stage.close();
                    }
                });
            }
        }, 0, 1000);
    }

    private void setQuestion(Label questionLabel) {
        group.selectToggle(null);
        switch (current) {
            case 0:
                questionLabel.setText("Que1: Who is known as the father of Java programming language?");
                options[0].setText("Charles Babbage");
                options[1].setText("James Gosling");
                options[2].setText("M.P. Java");
                options[3].setText("Blais Pascal");
                break;
            case 1:
                questionLabel.setText("Que2: Number of primitive data types in Java are?");
                options[0].setText("6");
                options[1].setText("7");
                options[2].setText("8");
                options[3].setText("9");
                break;
            case 2:
                questionLabel.setText("Que3: Where is the system class defined?");
                options[0].setText("java.lang.package");
                options[1].setText("java.util.package");
                options[2].setText("java.io.package");
                options[3].setText("None");
                break;
            case 3:
                questionLabel.setText("Que4: Exception created by try block is caught in which block?");
                options[0].setText("catch");
                options[1].setText("throw");
                options[2].setText("final");
                options[3].setText("thrown");
                break;
            case 4:
                questionLabel.setText("Que5: Which of the following is not an OOP concept in Java?");
                options[0].setText("Polymorphism");
                options[1].setText("Inheritance");
                options[2].setText("Compilation");
                options[3].setText("Encapsulation");
                break;
            case 5:
                questionLabel.setText("Que6: Identify the infinite loop:");
                options[0].setText("for(;;)");
                options[1].setText("for(int i=0; j<1; i--)");
                options[2].setText("for(int=0; i++)");
                options[3].setText("if(All of the above)");
                break;
            case 6:
                questionLabel.setText("Que7: When is the finalize() method called?");
                options[0].setText("Before garbage collection");
                options[1].setText("Before an object goes out of scope");
                options[2].setText("Before a variable goes out of scope");
                options[3].setText("None");
                break;
            case 7:
                questionLabel.setText("Que8: What is the implicit return type of a constructor?");
                options[0].setText("No return type");
                options[1].setText("A class object in which it is defined");
                options[2].setText("void");
                options[3].setText("None");
                break;
            case 8:
                questionLabel.setText("Que9: The class at the top of exception class hierarchy is?");
                options[0].setText("ArithmeticException");
                options[1].setText("Throwable");
                options[2].setText("Object");
                options[3].setText("Console");
                break;
            case 9:
                questionLabel.setText("Que10: Which provides the runtime environment for Java byte code to be executed?");
                options[0].setText("JDK");
                options[1].setText("JVM");
                options[2].setText("JRE");
                options[3].setText("JAVAC");
                break;
        }
    }

    private boolean checkAnswer() {
        switch (current) {
            case 0:
                return options[1].isSelected();
            case 1:
                return options[2].isSelected();
            case 2:
                return options[0].isSelected();
            case 3:
                return options[0].isSelected();
            case 4:
                return options[2].isSelected();
            case 5:
                return options[0].isSelected();
            case 6:
                return options[0].isSelected();
            case 7:
                return options[1].isSelected();
            case 8:
                return options[1].isSelected();
            case 9:
                return options[1].isSelected();
        }
        return false;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

