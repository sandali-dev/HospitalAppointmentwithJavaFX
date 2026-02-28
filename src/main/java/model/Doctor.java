package model;

import java.util.ArrayList;

public class Doctor extends User {


    private String specialty;
    private ArrayList<Appointment> queue;

    // ---- CONSTRUCTOR ----
    public Doctor(String username, String password, String name, String contact, String specialty) {
        super(username, password, name, contact, "Doctor");

        this.specialty = specialty;
        this.queue     = new ArrayList<>();  // empty queue at start
    }

    // ---- GETTER ----
    public String getSpecialty() {
        return specialty;
    }

    public ArrayList<Appointment> getQueue() {
        return queue;
    }

    // ---- Add a new appointment to this doctor's queue ----
    public void addToQueue(Appointment a) {
        queue.add(a);
    }

    // ---- Find the next PENDING patient and call them ----
    public Appointment callNextPatient() {
        for (int i = 0; i < queue.size(); i++) {
            Appointment a = queue.get(i);

            // Find the first one that is still PENDING
            if (a.getStatus() == Status.PENDING) {
                a.setStatus(Status.IN_PROGRESS);
                return a;                          // return it to the controller
            }
        }
        return null;  // no pending patients
    }


    @Override
    public void displayDashboard() {
        System.out.println("Doctor Dashboard - Dr. " + getName());
    }


    @Override
    public String toString() {
        return getName() + " — " + specialty;
    }
}