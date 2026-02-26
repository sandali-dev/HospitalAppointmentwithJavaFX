package gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.DataStore;
import model.Patient;

// Controls everything on the register screen
public class RegisterController {

    // Connected to register.fxml using fx:id
    @FXML private TextField     regUsername;
    @FXML private TextField     regName;
    @FXML private TextField     regContact;
    @FXML private PasswordField regPassword;
    @FXML private Button        registerBtn;
    @FXML private Button        backToLoginBtn;

    // Reference to switch screens
    private HospitalMain mainApp;

    public void setMainApp(HospitalMain mainApp) {
        this.mainApp = mainApp;
    }

    // Runs automatically when screen loads
    @FXML
    private void initialize() {
        registerBtn.setOnAction(e -> handleRegister());
        backToLoginBtn.setOnAction(e -> mainApp.showLoginScreen());
    }

    // Runs when CREATE ACCOUNT button is clicked
    private void handleRegister() {
        String username = regUsername.getText().trim();
        String name     = regName.getText().trim();
        String contact  = regContact.getText().trim();
        String password = regPassword.getText().trim();

        // Check required fields
        if (username.isEmpty() || name.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Incomplete", "Username, name and password are required.");
            return;
        }

        // Check if username already exists
        if (DataStore.usernameExists(username)) {
            showAlert(Alert.AlertType.ERROR, "Taken", "That username is already taken. Try another.");
            return;
        }

        // Create new patient and save to DataStore
        Patient newPatient = new Patient(username, password, name, contact);
        DataStore.users.add(newPatient);

        showAlert(Alert.AlertType.INFORMATION, "Success", "Account created! You can now login.");

        // Go back to login
        mainApp.showLoginScreen();
    }

    // Helper to show popup messages
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
