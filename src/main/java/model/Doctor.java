package model;

import java.util.ArrayList;

// Doctor is a User — created by Admin only
public class Doctor extends User {

    private String specialty;
    // Each doctor has their own list of appointments (queue)
    private ArrayList<Appointment> queue = new ArrayList<>();

    public Doctor(String username, String password, String name, String contact, String specialty) {
        super(username, password, name, contact, "Doctor");
        this.specialty = specialty;
    }

    public String getSpecialty() {
        return specialty;
    }

    // Add appointment to this doctor's queue
    public void addToQueue(Appointment a) {
        queue.add(a);
    }

    // Get the full queue list
    public ArrayList<Appointment> getQueue() {
        return queue;
    }

    // Find and return the next PENDING appointment
    public Appointment callNextPatient() {
        for (Appointment a : queue) {
            if (a.getStatus() == Status.PENDING) {
                a.setStatus(Status.IN_PROGRESS);
                return a;
            }
        }
        return null; // no pending appointments
    }

    @Override
    public void displayDashboard() {
        System.out.println("Doctor Dashboard - Dr. " + getName());
    }
}
