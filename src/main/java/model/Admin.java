package model;

public class Admin extends User {

    public Admin() {
        super("admin", "admin123", "System Admin", "N/A", "Admin");
    }

    @Override
    public void displayDashboard() {
        System.out.println("Admin Dashboard");
    }

    // ---- toString ----
    @Override
    public String toString() {
        return "Admin[name=" + getName() + "]";
    }
}
