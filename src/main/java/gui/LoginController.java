package gui;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.*;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML private ComboBox<String> roleCombo;
    @FXML private TextField        usernameField;
    @FXML private PasswordField    passwordField;
    @FXML private Button           loginBtn;
    @FXML private Button           registerBtn;

    private HospitalMain mainApp;

    public void setMainApp(HospitalMain mainApp) { this.mainApp = mainApp; }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        roleCombo.setItems(FXCollections.observableArrayList("Patient", "Doctor", "Admin"));
        roleCombo.setValue("Patient");
        loginBtn.setOnAction(e    -> handleLogin());
        registerBtn.setOnAction(e -> mainApp.showRegisterScreen());
    }

    private void handleLogin() {
        try {
            String role     = roleCombo.getValue();
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();

            // Validate — throw exception if fields empty
            if (username.isEmpty() || password.isEmpty()) {
                throw new IllegalArgumentException("Please enter username and password.");
            }

            User user = DataStore.findUser(username, password, role);

            // Throw exception if user not found
            if (user == null) {
                throw new IllegalArgumentException("Wrong username, password or role.");
            }

            user.displayDashboard(); // polymorphism

            // Navigate to correct dashboard
            if      (role.equals("Patient")) mainApp.showPatientDashboard((Patient) user);
            else if (role.equals("Doctor"))  mainApp.showDoctorDashboard((Doctor) user);
            else if (role.equals("Admin"))   mainApp.showAdminDashboard();

        } catch (IllegalArgumentException e) {
            // Expected errors — show to user
            showAlert(Alert.AlertType.WARNING, e.getMessage());

        } catch (Exception e) {
            // Unexpected errors
            showAlert(Alert.AlertType.ERROR, "Unexpected error: " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}