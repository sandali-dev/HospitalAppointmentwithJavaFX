package model;

// Holds all information about ONE appointment
public class Appointment {

    private String patientName; // who booked
    private Doctor doctor;      // which doctor
    private String date;        // appointment date
    private Status status;      // PENDING / IN_PROGRESS / COMPLETED / CANCELLED
    private int    queueNumber; // position in THIS doctor's queue

    public Appointment(String patientName, Doctor doctor, String date) {
        this.patientName = patientName;
        this.doctor      = doctor;
        this.date        = date;
        this.status      = Status.PENDING;

        // Queue number = how many patients this doctor already has + 1
        // So each doctor counts independently: #1, #2, #3...
        this.queueNumber = doctor.getQueue().size() + 1;

        // Add to this doctor's queue AFTER setting queue number
        doctor.addToQueue(this);
    }

    // Getters
    public String getPatientName() { return patientName; }
    public Doctor getDoctor()      { return doctor; }
    public String getDate()        { return date; }
    public Status getStatus()      { return status; }
    public int    getQueueNumber() { return queueNumber; }

    // Only status can change after booking
    public void setStatus(Status status) { this.status = status; }
}