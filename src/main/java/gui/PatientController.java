package gui;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import model.*;
import java.time.LocalDate;

public class PatientController {

    @FXML private Label  welcomeLabel;
    @FXML private Button logoutBtn;
    @FXML private Button btnDoctors;
    @FXML private Button btnBook;
    @FXML private Button btnView;

    @FXML private VBox panelDoctors;
    @FXML private VBox panelBook;
    @FXML private VBox panelView;

    @FXML private TableView<Doctor>           doctorsTable;
    @FXML private TableColumn<Doctor, String> colDocName;
    @FXML private TableColumn<Doctor, String> colDocSpecialty;

    @FXML private ComboBox<Doctor> doctorCombo;
    @FXML private DatePicker       datePicker;

    @FXML private TableView<Appointment>            appointmentsTable;
    @FXML private TableColumn<Appointment, String>  colDoctor;
    @FXML private TableColumn<Appointment, String>  colSpecialty;
    @FXML private TableColumn<Appointment, String>  colDate;
    @FXML private TableColumn<Appointment, Integer> colToken;
    @FXML private TableColumn<Appointment, String>  colStatus;

    private Patient      currentPatient;
    private HospitalMain mainApp;

    public void setPatient(Patient p) {
        currentPatient = p;
        welcomeLabel.setText("Welcome, " + p.getName());
    }

    public void setMainApp(HospitalMain app) { mainApp = app; }

    @FXML
    private void initialize() {
        logoutBtn.setOnAction(e  -> mainApp.showLoginScreen());
        btnDoctors.setOnAction(e -> showPanel("doctors"));
        btnBook.setOnAction(e    -> showPanel("book"));
        btnView.setOnAction(e    -> showPanel("view"));

        addHover(btnDoctors);
        addHover(btnBook);
        addHover(btnView);

        colDocName.setCellValueFactory(d      -> new SimpleStringProperty(d.getValue().getName()));
        colDocSpecialty.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getSpecialty()));

        colDoctor.setCellValueFactory(a    -> new SimpleStringProperty(a.getValue().getDoctor().getName()));
        colSpecialty.setCellValueFactory(a -> new SimpleStringProperty(a.getValue().getDoctor().getSpecialty()));
        colDate.setCellValueFactory(a      -> new SimpleStringProperty(a.getValue().getDate()));
        colToken.setCellValueFactory(a     -> new SimpleIntegerProperty(a.getValue().getQueueNumber()).asObject());
        colStatus.setCellValueFactory(a    -> new SimpleStringProperty(a.getValue().getStatus().toString()));

        doctorCombo.setCellFactory(lv -> new ListCell<>() {
            protected void updateItem(Doctor d, boolean empty) {
                super.updateItem(d, empty);
                setText(empty || d == null ? "" : d.getName() + " — " + d.getSpecialty());
            }
        });
        doctorCombo.setButtonCell(new ListCell<>() {
            protected void updateItem(Doctor d, boolean empty) {
                super.updateItem(d, empty);
                setText(empty || d == null ? "" : d.getName() + " — " + d.getSpecialty());
            }
        });
    }

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
        panelBook.setVisible(false);
        panelView.setVisible(false);

        switch (which) {
            case "doctors" -> {
                panelDoctors.setVisible(true);
                doctorsTable.setItems(FXCollections.observableArrayList(DataStore.getAllDoctors()));
            }
            case "book" -> {
                panelBook.setVisible(true);
                doctorCombo.setItems(FXCollections.observableArrayList(DataStore.getAllDoctors()));
            }
            case "view" -> {
                panelView.setVisible(true);
                appointmentsTable.setItems(FXCollections.observableArrayList(currentPatient.getAppointments()));
            }
        }
    }

    @FXML
    private void handleBooking() {
        try {
            Doctor    doctor = doctorCombo.getValue();
            LocalDate date   = datePicker.getValue();

            // Validate — throw exception if fields missing
            if (doctor == null || date == null) {
                throw new IllegalArgumentException("Please select a doctor and date.");
            }
            if (date.isBefore(LocalDate.now())) {
                throw new IllegalArgumentException("Please pick today or a future date.");
            }

            // Create appointment and save
            Appointment appt = new Appointment(currentPatient.getName(), doctor, date.toString());
            currentPatient.addAppointment(appt);
            DataStore.appointments.add(appt);

            showAlert(Alert.AlertType.INFORMATION,
                    "✅ Booking Confirmed!\n\n" +
                            "Doctor   : " + doctor.getName() + "\n" +
                            "Specialty: " + doctor.getSpecialty() + "\n" +
                            "Date     : " + date + "\n\n" +
                            "🎫 Your Token: #" + appt.getQueueNumber() + "\n\n" +
                            "Please show this token when you arrive.");

            doctorCombo.setValue(null);
            datePicker.setValue(null);
            showPanel("view");

        } catch (IllegalArgumentException e) {
            // Validation errors — show to user
            showAlert(Alert.AlertType.WARNING, e.getMessage());

        } catch (Exception e) {
            // Unexpected errors
            showAlert(Alert.AlertType.ERROR, "Booking failed: " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType type, String msg) {
        new Alert(type, msg, ButtonType.OK).showAndWait();
    }
}