import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class OnlineReservationSystem extends Application {

    // Users ane reservations store karva mate Maps
    private static final Map<String, String> users = new HashMap<>();
    private static final Map<String, String> reservations = new HashMap<>();

    static {
        // Sample user
        users.put("user", "pass");
    }

    private Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        // Login screen dekhao
        showLoginScreen();
    }

    private void showLoginScreen() {
        // Login pane setup karo
        GridPane loginPane = new GridPane();
        loginPane.setPadding(new Insets(10));
        loginPane.setHgap(10);
        loginPane.setVgap(10);

        Label loginLabel = new Label("Login ID:");
        TextField loginField = new TextField();
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Login");

        // Login button par action set karo
        loginButton.setOnAction(e -> {
            if (login(loginField.getText(), passwordField.getText())) {
                showMainMenu();
            } else {
                showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid login credentials!");
            }
        });

        // Components ne login pane ma add karo
        loginPane.add(loginLabel, 0, 0);
        loginPane.add(loginField, 1, 0);
        loginPane.add(passwordLabel, 0, 1);
        loginPane.add(passwordField, 1, 1);
        loginPane.add(loginButton, 1, 2);

        // Login scene set karo
        Scene loginScene = new Scene(loginPane, 300, 200);
        primaryStage.setScene(loginScene);
        primaryStage.setTitle("Ticket Reservation System - Login");
        primaryStage.show();
    }

    private void showMainMenu() {
        // Main menu pane setup karo
        FlowPane menuPane = new FlowPane(10, 10);
        menuPane.setPadding(new Insets(10));

        Button reserveButton = new Button("Reserve Ticket");
        Button cancelButton = new Button("Cancel Ticket");
        Button exitButton = new Button("Exit");

        // Buttons par actions set karo
        reserveButton.setOnAction(e -> showReserveTicketScreen());
        cancelButton.setOnAction(e -> showCancelTicketScreen());
        exitButton.setOnAction(e -> primaryStage.close());

        // Buttons ne menu pane ma add karo
        menuPane.getChildren().addAll(reserveButton, cancelButton, exitButton);

        // Main menu scene set karo
        Scene menuScene = new Scene(menuPane, 300, 200);
        primaryStage.setScene(menuScene);
        primaryStage.setTitle("Ticket Reservation System - Main Menu");
        primaryStage.show();
    }

    private void showReserveTicketScreen() {
        // Reserve ticket pane setup karo
        GridPane reservePane = new GridPane();
        reservePane.setPadding(new Insets(10));
        reservePane.setHgap(10);
        reservePane.setVgap(10);

        Label pnrLabel = new Label("PNR number:");
        TextField pnrField = new TextField();
        Label trainLabel = new Label("Train number:");
        TextField trainField = new TextField();
        Label classLabel = new Label("Class type:");
        TextField classField = new TextField();
        Label dateLabel = new Label("Date of journey:");
        TextField dateField = new TextField();
        Label fromLabel = new Label("From place:");
        TextField fromField = new TextField();
        Label toLabel = new Label("Destination:");
        TextField toField = new TextField();
        Button submitButton = new Button("Reserve");

        // Reserve button par action set karo
        submitButton.setOnAction(e -> {
            String pnr = pnrField.getText();
            String trainNumber = trainField.getText();
            String classType = classField.getText();
            String dateOfJourney = dateField.getText();
            String from = fromField.getText();
            String to = toField.getText();

            // Check karo ke badha fields bharela chhe
            if (pnr.isEmpty() || trainNumber.isEmpty() || classType.isEmpty() || dateOfJourney.isEmpty() || from.isEmpty() || to.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Reservation Failed", "All fields must be filled out!");
            } else {
                // Reservation ne add karo
                reservations.put(pnr, String.format("Train: %s, Class: %s, Date: %s, From: %s, To: %s",
                        trainNumber, classType, dateOfJourney, from, to));

                showAlert(Alert.AlertType.INFORMATION, "Reservation Successful",
                        "Reservation successful!\nPNR: " + pnr);

                // Main menu dekhao
                showMainMenu();
            }
        });

        // Components ne reserve pane ma add karo
        reservePane.add(pnrLabel, 0, 0);
        reservePane.add(pnrField, 1, 0);
        reservePane.add(trainLabel, 0, 1);
        reservePane.add(trainField, 1, 1);
        reservePane.add(classLabel, 0, 2);
        reservePane.add(classField, 1, 2);
        reservePane.add(dateLabel, 0, 3);
        reservePane.add(dateField, 1, 3);
        reservePane.add(fromLabel, 0, 4);
        reservePane.add(fromField, 1, 4);
        reservePane.add(toLabel, 0, 5);
        reservePane.add(toField, 1, 5);
        reservePane.add(submitButton, 1, 6);

        // Reserve scene set karo
        Scene reserveScene = new Scene(reservePane, 400, 300);
        primaryStage.setScene(reserveScene);
        primaryStage.setTitle("Reserve Ticket");
        primaryStage.show();
    }

    private void showCancelTicketScreen() {
        // Cancel ticket pane setup karo
        VBox cancelPane = new VBox(10);
        cancelPane.setPadding(new Insets(10));

        Label pnrLabel = new Label("Enter PNR number:");
        TextField pnrField = new TextField();
        Button cancelButton = new Button("Cancel Ticket");

        // Cancel button par action set karo
        cancelButton.setOnAction(e -> {
            String pnr = pnrField.getText();
            if (reservations.containsKey(pnr)) {
                String details = reservations.get(pnr);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Reservation Details: " + details + "\nConfirm cancellation?", ButtonType.YES, ButtonType.NO);
                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.YES) {
                        reservations.remove(pnr);
                        showAlert(Alert.AlertType.INFORMATION, "Cancellation Successful", "Cancellation successful!");
                        showMainMenu();
                    }
                });
            } else {
                showAlert(Alert.AlertType.ERROR, "PNR Not Found", "PNR number not found!");
                showMainMenu();
            }
        });

        // Components ne cancel pane ma add karo
        cancelPane.getChildren().addAll(pnrLabel, pnrField, cancelButton);

        // Cancel scene set karo
        Scene cancelScene = new Scene(cancelPane, 300, 200);
        primaryStage.setScene(cancelScene);
        primaryStage.setTitle("Cancel Ticket");
        primaryStage.show();
    }

    private boolean login(String loginId, String password) {
        return users.containsKey(loginId) && users.get(loginId).equals(password);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
