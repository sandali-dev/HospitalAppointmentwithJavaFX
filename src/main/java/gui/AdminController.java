package gui;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import model.*;

public class AdminController {

    @FXML private Button logoutBtn;
    @FXML private Button btnDoctors;
    @FXML private Button btnAppointments;
    @FXML private VBox   panelDoctors;
    @FXML private VBox   panelAppointments;

    @FXML private TableView<Doctor>            doctorsTable;
    @FXML private TableColumn<Doctor, String>  colDocUsername;
    @FXML private TableColumn<Doctor, String>  colDocName;
    @FXML private TableColumn<Doctor, String>  colDocSpecialty;
    @FXML private TableColumn<Doctor, String>  colDocContact;
    @FXML private TableColumn<Doctor, Integer> colDocQueue;

    @FXML private TableView<Appointment>            appointmentsTable;
    @FXML private TableColumn<Appointment, Integer> colApptToken;
    @FXML private TableColumn<Appointment, String>  colApptPatient;
    @FXML private TableColumn<Appointment, String>  colApptDoctor;
    @FXML private TableColumn<Appointment, String>  colApptDate;
    @FXML private TableColumn<Appointment, String>  colApptStatus;

    private HospitalMain mainApp;

    public void setMainApp(HospitalMain app) { mainApp = app; }

    @FXML
    private void initialize() {
        logoutBtn.setOnAction(e       -> mainApp.showLoginScreen());
        btnDoctors.setOnAction(e      -> showPanel("doctors"));
        btnAppointments.setOnAction(e -> showPanel("appointments"));

        // Add hover effect to sidebar buttons
        addHover(btnDoctors);
        addHover(btnAppointments);

        // Doctors columns
        colDocUsername.setCellValueFactory(d  -> new SimpleStringProperty(d.getValue().getUsername()));
        colDocName.setCellValueFactory(d      -> new SimpleStringProperty(d.getValue().getName()));
        colDocSpecialty.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getSpecialty()));
        colDocContact.setCellValueFactory(d   -> new SimpleStringProperty(d.getValue().getContact()));
        colDocQueue.setCellValueFactory(d     -> new SimpleIntegerProperty(d.getValue().getQueue().size()).asObject());

        // Appointments columns
        colApptToken.setCellValueFactory(a   -> new SimpleIntegerProperty(a.getValue().getQueueNumber()).asObject());
        colApptPatient.setCellValueFactory(a -> new SimpleStringProperty(a.getValue().getPatientName()));
        colApptDoctor.setCellValueFactory(a  -> new SimpleStringProperty(a.getValue().getDoctor().getName()));
        colApptDate.setCellValueFactory(a    -> new SimpleStringProperty(a.getValue().getDate()));
        colApptStatus.setCellValueFactory(a  -> new SimpleStringProperty(a.getValue().getStatus().toString()));

        loadDoctors();
    }

    // ── Hover effect — background turns cyan when mouse is over button ──
    private void addHover(Button btn) {
        String normal = "-fx-background-color: transparent; -fx-text-fill: #0288d1; " +
                "-fx-font-size: 12px; -fx-alignment: CENTER_LEFT; " +
                "-fx-background-radius: 10; -fx-padding: 11 14; -fx-cursor: hand;";

        String hover  = "-fx-background-color: #e0f7fa; -fx-text-fill: #0097a7; " +
                "-fx-font-size: 12px; -fx-font-weight: bold; -fx-alignment: CENTER_LEFT; " +
                "-fx-background-radius: 10; -fx-padding: 11 14; -fx-cursor: hand;";

        btn.setOnMouseEntered(e -> btn.setStyle(hover));
        btn.setOnMouseExited(e  -> btn.setStyle(normal));
    }

    private void showPanel(String which) {
        panelDoctors.setVisible(false);
        panelAppointments.setVisible(false);

        if (which.equals("doctors")) {
            panelDoctors.setVisible(true);
            loadDoctors();
        } else {
            panelAppointments.setVisible(true);
            loadAppointments();
        }
    }

    private void loadDoctors() {
        doctorsTable.setItems(FXCollections.observableArrayList(DataStore.getAllDoctors()));
    }

    private void loadAppointments() {
        appointmentsTable.setItems(FXCollections.observableArrayList(DataStore.appointments));
    }

    @FXML
    private void handleAddDoctor() {
        TextField     usernameF  = new TextField();     usernameF.setPromptText("Username");
        PasswordField passwordF  = new PasswordField(); passwordF.setPromptText("Password");
        TextField     nameF      = new TextField();     nameF.setPromptText("Full Name");
        TextField     specialtyF = new TextField();     specialtyF.setPromptText("Specialty");
        TextField     contactF   = new TextField();     contactF.setPromptText("Contact");

        VBox form = new VBox(8, usernameF, passwordF, nameF, specialtyF, contactF);
        form.setStyle("-fx-padding: 10;");

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Add New Doctor");
        dialog.getDialogPane().setContent(form);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.showAndWait().ifPresent(btn -> {
            if (btn != ButtonType.OK) return;

            String username  = usernameF.getText().trim();
            String password  = passwordF.getText().trim();
            String name      = nameF.getText().trim();
            String specialty = specialtyF.getText().trim();
            String contact   = contactF.getText().trim();

            if (username.isEmpty() || password.isEmpty() || name.isEmpty() || specialty.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "All fields except contact are required."); return;
            }
            if (DataStore.usernameExists(username)) {
                showAlert(Alert.AlertType.ERROR, "Username already taken."); return;
            }

            DataStore.users.add(new Doctor(username, password, name, contact, specialty));
            loadDoctors();
            showAlert(Alert.AlertType.INFORMATION, "Dr. " + name + " added successfully!");
        });
    }

    @FXML
    private void handleRemoveDoctor() {
        Doctor selected = doctorsTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Please select a doctor to remove."); return;
        }

        DataStore.users.remove(selected);
        loadDoctors();
        showAlert(Alert.AlertType.INFORMATION, selected.getName() + " has been removed.");
    }

    private void showAlert(Alert.AlertType type, String msg) {
        new Alert(type, msg, ButtonType.OK).showAndWait();
    }
}