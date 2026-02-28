package model;


public abstract class User {

    private String username;
    private String password;
    private String name;
    private String contact;
    private String role;

    public User(String username, String password, String name, String contact, String role) {
        this.username = username;
        this.password = password;
        this.name     = name;
        this.contact  = contact;
        this.role     = role;
    }

    // Getters


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getContact() {
        return contact;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    // Every child class must write their own version of this
    public abstract void displayDashboard();

    @Override
    public String toString() {
        return "User[name=" + name + ", role=" + role + "]";
    }
}
