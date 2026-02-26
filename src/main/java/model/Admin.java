package model;

// Admin is a User — one fixed account, created by the system
public class Admin extends User {

    public Admin() {
        super("admin", "admin123", "System Admin", "N/A", "Admin");
    }

    @Override
    public void displayDashboard() {
        System.out.println("Admin Dashboard");
    }
}
