package model;

import java.util.ArrayList;

// Patient is a User — can register themselves
public class Patient extends User {

    // Each patient keeps a list of their own appointments
    private ArrayList<Appointment> appointments = new ArrayList<>();

    public Patient(String username, String password, String name, String contact) {
        super(username, password, name, contact, "Patient");
    }

    // Add a new appointment to this patient's list
    public void addAppointment(Appointment a) {
        appointments.add(a);
    }

    // Get all appointments for this patient
    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    @Override
    public void displayDashboard() {
        System.out.println("Patient Dashboard - " + getName());
    }
}
