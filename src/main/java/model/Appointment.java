package model;


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
        this.queueNumber = doctor.getQueue().size() + 1;

        // Add to this doctor's queue AFTER setting queue number
        doctor.addToQueue(this);
    }

    // Getters
    public String getPatientName() {
        return patientName;
    }
    public Doctor getDoctor()      {
        return doctor;
    }
    public String getDate()        {
        return date;
    }
    public Status getStatus()      {
        return status;
    }
    public int    getQueueNumber() {
        return queueNumber;
    }



    // Only status can change after booking
    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Appointment[token=#" + queueNumber
                + ", patient=" + patientName
                + ", doctor=Dr." + doctor.getName()
                + ", date=" + date
                + ", status=" + status + "]";
    }
}