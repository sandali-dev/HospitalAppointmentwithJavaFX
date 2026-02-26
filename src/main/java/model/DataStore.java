package model;

import java.util.ArrayList;

// DataStore = the memory of the whole system
// Stores all users and appointments in simple ArrayLists
public class DataStore {

    // All users in the system (Admin + Doctors + Patients)
    public static ArrayList<User> users = new ArrayList<>();

    // All appointments in the system
    public static ArrayList<Appointment> appointments = new ArrayList<>();

    // Load starting data when app opens
    public static void addHardcodedData() {
        users.add(new Admin());
        users.add(new Doctor("silva",      "silva1",      "Dr. Namal Silva",          "0771234567", "Cardiologist"));
        users.add(new Doctor("sanjeewani", "sanjeewani2", "Dr. Sanjeewani Pathirana",  "0772222222", "Dermatologist"));
        users.add(new Doctor("perera",     "perera3",     "Dr. Kasun Perera",          "0773333333", "Dentist"));
    }

    // Find a user by username + password + role
    // Returns the User if found, or null if not found
    public static User findUser(String username, String password, String role) {
        for (User u : users) {
            if (u.getUsername().equalsIgnoreCase(username)
                    && u.getPassword().equals(password)
                    && u.getRole().equalsIgnoreCase(role)) {
                return u;
            }
        }
        return null;
    }

    // Check if a username is already taken
    public static boolean usernameExists(String username) {
        for (User u : users) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }

    // Get only the doctors from the users list
    public static ArrayList<Doctor> getAllDoctors() {
        ArrayList<Doctor> doctors = new ArrayList<>();
        for (User u : users) {
            if (u instanceof Doctor) {
                doctors.add((Doctor) u);
            }
        }
        return doctors;
    }
}
