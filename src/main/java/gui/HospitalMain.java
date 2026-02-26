package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import model.*;

public class HospitalMain extends Application {

    private Stage primaryStage;

    // One scene per screen
    private Scene loginScene;
    private Scene registerScene;
    private Scene patientScene;
    private Scene doctorScene;
    private Scene adminScene;

    // Controllers we pass data to after login
    private PatientController patientController;
    private DoctorController  doctorController;
    private AdminController   adminController;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        // Load starting users into memory
        DataStore.addHardcodedData();

        // Load all 5 screens — catch any FXML loading errors
        try {
            FXMLLoader loginLoader = loadFXML("/login.fxml");
            loginScene = new Scene(loginLoader.getRoot(), 980, 680);
            ((LoginController) loginLoader.getController()).setMainApp(this);

            FXMLLoader regLoader = loadFXML("/register.fxml");
            registerScene = new Scene(regLoader.getRoot(), 980, 680);
            ((RegisterController) regLoader.getController()).setMainApp(this);

            FXMLLoader patientLoader = loadFXML("/Patientdashboard.fxml");
            patientScene = new Scene(patientLoader.getRoot(), 980, 680);
            patientController = patientLoader.getController();
            patientController.setMainApp(this);

            FXMLLoader doctorLoader = loadFXML("/Doctor.fxml");
            doctorScene = new Scene(doctorLoader.getRoot(), 980, 680);
            doctorController = doctorLoader.getController();
            doctorController.setMainApp(this);

            FXMLLoader adminLoader = loadFXML("/Admin.fxml");
            adminScene = new Scene(adminLoader.getRoot(), 980, 680);
            adminController = adminLoader.getController();
            adminController.setMainApp(this);

        } catch (Exception e) {
            // If any FXML file fails to load — show error and stop
            new Alert(Alert.AlertType.ERROR,
                    "Failed to load application screens.\nError: " + e.getMessage(),
                    ButtonType.OK).showAndWait();
            e.printStackTrace();
            return;
        }

        // Show login screen first
        primaryStage.setTitle("EliteCare Clinic");
        primaryStage.setScene(loginScene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    // Helper — loads one FXML file and returns its loader
    private FXMLLoader loadFXML(String path) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        loader.load();
        return loader;
    }

    // ── Screen switching methods ──

    public void showLoginScreen() {
        primaryStage.setScene(loginScene);
    }

    public void showRegisterScreen() {
        primaryStage.setScene(registerScene);
    }

    public void showAdminDashboard() {
        primaryStage.setScene(adminScene);
    }

    public void showPatientDashboard(Patient p) {
        patientController.setPatient(p);
        primaryStage.setScene(patientScene);
    }

    public void showDoctorDashboard(Doctor d) {
        doctorController.setDoctor(d);
        primaryStage.setScene(doctorScene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}