package model;

import java.util.ArrayList;


public class Patient extends User {

    private ArrayList<Appointment> appointments;

    public Patient(String username, String password, String name, String contact) {
        super(username, password, name, contact, "Patient");
        this.appointments = new ArrayList<>();
    }


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

    @Override
    public String toString() {
        return "Patient[name=" + getName() + ", appointments=" + appointments.size() + "]";
    }
}
