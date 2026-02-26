package gui;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.*;

public class DoctorController {

    @FXML private Label  welcomeLabel;
    @FXML private Button logoutBtn;

    @FXML private TableView<Appointment>            queueTable;
    @FXML private TableColumn<Appointment, Integer> colQueue;
    @FXML private TableColumn<Appointment, String>  colPatient;
    @FXML private TableColumn<Appointment, String>  colDate;
    @FXML private TableColumn<Appointment, String>  colStatus;

    private Doctor       currentDoctor;
    private HospitalMain mainApp;

    public void setDoctor(Doctor d) {
        currentDoctor = d;
        welcomeLabel.setText("Welcome, Dr. " + d.getName());
        loadQueue();
    }

    public void setMainApp(HospitalMain app) {
        mainApp = app;
    }

    @FXML
    private void initialize() {
        logoutBtn.setOnAction(e -> mainApp.showLoginScreen());

        colQueue.setCellValueFactory(a   -> new SimpleIntegerProperty(a.getValue().getQueueNumber()).asObject());
        colPatient.setCellValueFactory(a -> new SimpleStringProperty(a.getValue().getPatientName()));
        colDate.setCellValueFactory(a    -> new SimpleStringProperty(a.getValue().getDate()));
        colStatus.setCellValueFactory(a  -> new SimpleStringProperty(a.getValue().getStatus().toString()));
    }

    // Fill table with this doctor's queue
    private void loadQueue() {
        queueTable.setItems(FXCollections.observableArrayList(currentDoctor.getQueue()));
    }

    // Finds next PENDING patient → marks IN_PROGRESS
    @FXML
    private void handleCallNext() {
        Appointment next = currentDoctor.callNextPatient();

        if (next == null) {
            showAlert(Alert.AlertType.INFORMATION, "No pending patients in the queue.");
            return;
        }
        loadQueue();
        showAlert(Alert.AlertType.INFORMATION,
                "Now seeing: " + next.getPatientName() + "\nQueue No: #" + next.getQueueNumber());
    }

    // Marks selected IN_PROGRESS appointment as COMPLETED
    @FXML
    private void handleComplete() {
        Appointment selected = queueTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Please select a patient from the table.");
            return;
        }
        if (selected.getStatus() != Status.IN_PROGRESS) {
            showAlert(Alert.AlertType.WARNING, "Only IN PROGRESS appointments can be completed.");
            return;
        }

        selected.setStatus(Status.COMPLETED);
        loadQueue();
        showAlert(Alert.AlertType.INFORMATION, selected.getPatientName() + "'s appointment completed!");
    }

    private void showAlert(Alert.AlertType type, String msg) {
        new Alert(type, msg, ButtonType.OK).showAndWait();
    }
}